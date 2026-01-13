package com.estudosjava.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudosjava.course.dto.CategoryDTO;
import com.estudosjava.course.dto.CategoryInsertDTO;
import com.estudosjava.course.dto.CategoryProductsDTO;
import com.estudosjava.course.entities.Category;

import com.estudosjava.course.repositories.CategoryRepository;
import com.estudosjava.course.repositories.ProductRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProductRepository productRepository;

    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryProductsDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new CategoryProductsDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryInsertDTO dto) {
        Category category = new Category();
        loadData(category, dto);
        category = repository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Registro possui dependências");
        }
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryProductsDTO dto) {
        try {
            Category category = repository.getReferenceById(id);
            updateData(category, dto);
            category = repository.save(category);
            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void loadData(Category category, CategoryInsertDTO dto) {
        category.setName(dto.name());
    }

    public void updateData(Category category, CategoryProductsDTO dto) {
        if (dto.name() != null) {
            category.setName(dto.name());
        }
        if (dto.products() != null) {
            category.getProducts().clear();
            dto.products().forEach(productDto -> {
                category.getProducts().add(productRepository.getReferenceById(productDto.id()));
            });
        }
    }
}
