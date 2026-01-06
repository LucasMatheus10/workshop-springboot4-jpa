package com.estudosjava.course.services;

import java.util.List;
import com.estudosjava.course.dto.ProductDTO;
import com.estudosjava.course.dto.ProductInsertDTO;
import com.estudosjava.course.dto.ProductUpdateDTO;
import com.estudosjava.course.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.estudosjava.course.repositories.CategoryRepository;
import com.estudosjava.course.repositories.ProductRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductDTO> findAll() {
        List<Product> list = repository.findAll();
        return list.stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO insert(ProductInsertDTO dto) {
        Product product = new Product();
        loadData(product, dto);
        product = repository.save(product);
        return new ProductDTO(product);
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
    public ProductDTO update(Long id, ProductUpdateDTO dto) {
        try {
            Product product = repository.getReferenceById(id);
            updateData(product, dto);
            product = repository.save(product);
            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void loadData(Product product, ProductInsertDTO dto) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setImgUrl(dto.imgUrl());
        product.getCategories().clear();
        if (dto.categories() != null) {
            dto.categories().forEach(catDto -> {
                product.getCategories().add(categoryRepository.getReferenceById(catDto.id()));
            });
        }
    }

    public void updateData(Product product, ProductUpdateDTO dto) {
        if (dto.name() != null) {
            product.setName(dto.name());
        }
        if (dto.description() != null) {
            product.setDescription(dto.description());
        }
        if (dto.price() != null) {
            product.setPrice(dto.price());
        }
        if (dto.imgUrl() != null) {
            product.setImgUrl(dto.imgUrl());
        }
        if (dto.categories() != null) {
        product.getCategories().clear();
        dto.categories().forEach(catDto -> {
            product.getCategories().add(categoryRepository.getReferenceById(catDto.id()));
        });
    }
    }
}
