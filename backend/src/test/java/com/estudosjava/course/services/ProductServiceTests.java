package com.estudosjava.course.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.estudosjava.course.dto.ProductDTO;
import com.estudosjava.course.dto.ProductInsertDTO;
import com.estudosjava.course.dto.ProductUpdateDTO;
import com.estudosjava.course.entities.Category;
import com.estudosjava.course.entities.Product;
import com.estudosjava.course.repositories.CategoryRepository;
import com.estudosjava.course.repositories.ProductRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Product product;
    private Category category;
    private ProductInsertDTO insertDTO;
    private ProductUpdateDTO updateDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;

        category = new Category(1L, "Electronics");
        CategoryDTO catDto = new CategoryDTO(category);

        product = new Product(existingId, "Phone", "Smartphone 5G", 2500.0, "");
        product.getCategories().add(category);
        
        insertDTO = new ProductInsertDTO("Phone", "Smartphone 5G", 2500.0, "", Set.of(catDto));
        updateDTO = new ProductUpdateDTO("Updated Phone", "Updated Description", 2600.0, "", Set.of(catDto));
    }

    @Test
    public void findAllShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(product));
        List<ProductDTO> result = service.findAll();
        Assertions.assertNotNull(result);
        verify(repository).findAll();
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        ProductDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Phone", result.name());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnProductDTO() {
        when(categoryRepository.getReferenceById(any())).thenReturn(category);
        when(repository.save(any())).thenReturn(product);

        ProductDTO result = service.insert(insertDTO);

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
    public void updateShouldReturnProductDTOWhenIdExists() {
        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(categoryRepository.getReferenceById(any())).thenReturn(category);
        when(repository.save(any())).thenReturn(product);

        ProductDTO result = service.update(existingId, updateDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Updated Phone", result.name());
        verify(repository).save(any());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, updateDTO);
        });
    }
}