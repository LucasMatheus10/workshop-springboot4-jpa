package com.estudosjava.course.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.estudosjava.course.dto.CategoryDTO;
import com.estudosjava.course.dto.CategoryInsertDTO;
import com.estudosjava.course.dto.CategoryProductsDTO;
import com.estudosjava.course.entities.Category;
import com.estudosjava.course.repositories.CategoryRepository;
import com.estudosjava.course.repositories.ProductRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Category category;
    private CategoryInsertDTO insertDTO;
    private CategoryProductsDTO productsDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        category = new Category(existingId, "Electronics");
        insertDTO = new CategoryInsertDTO("Electronics");
        productsDTO = new CategoryProductsDTO(existingId, "New Name", List.of());
    }

    @Test
    public void findAllShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(category));
        List<CategoryDTO> result = service.findAll();
        Assertions.assertNotNull(result);
        verify(repository).findAll();
    }

    @Test
    public void findByIdShouldReturnCategoryProductsDTOWhenIdExists() {
        when(repository.findById(existingId)).thenReturn(Optional.of(category));
        CategoryProductsDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Electronics", result.name());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnCategoryDTO() {
        when(repository.save(any())).thenReturn(category);
        CategoryDTO result = service.insert(insertDTO);
        Assertions.assertNotNull(result);
        verify(repository).save(any());
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        verify(repository).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }

    @Test
    public void updateShouldReturnCategoryDTOWhenIdExists() {
        when(repository.getReferenceById(existingId)).thenReturn(category);
        when(repository.save(any())).thenReturn(category);

        CategoryDTO result = service.update(existingId, productsDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("New Name", result.name());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, productsDTO);
        });
    }
}