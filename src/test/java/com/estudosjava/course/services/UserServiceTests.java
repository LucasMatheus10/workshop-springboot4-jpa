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

import com.estudosjava.course.dto.UserDTO;
import com.estudosjava.course.dto.UserInsertDTO;
import com.estudosjava.course.dto.UserOrdersDTO;
import com.estudosjava.course.dto.UserUpdateDTO;
import com.estudosjava.course.entities.User;
import com.estudosjava.course.repositories.UserRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserServices service;

    @Mock
    private UserRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private User user;
    private UserDTO userDTO;
    private UserUpdateDTO updateDTO;
    private UserInsertDTO insertDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;

        user = new User(existingId, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        userDTO = new UserDTO(user);
        insertDTO = new UserInsertDTO("Maria Brown", "maria@gmail.com", "988888888", "123456");
        updateDTO = new UserUpdateDTO("Maria Green", "maria_green@gmail.com", "977777777");
    }

    @Test
    public void findAllShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<UserDTO> result = service.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    public void findByIdShouldReturnUserDTOWhenIdExists() {
        when(repository.findById(existingId)).thenReturn(Optional.of(user));

        UserOrdersDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Maria Brown", result.name());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnUserDTO() {
        when(repository.save(any())).thenReturn(user);

        UserDTO result = service.insert(insertDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("maria@gmail.com", result.email());
        verify(repository).save(any());
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        verify(repository).deleteById(existingId);
        verify(repository).flush();
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
    public void updateShouldReturnUserDTOWhenIdExists() {
        when(repository.getReferenceById(existingId)).thenReturn(user);
        when(repository.save(any())).thenReturn(user);

        UserDTO result = service.update(existingId, updateDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Maria Green", result.name());
        verify(repository).getReferenceById(existingId);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, updateDTO);
        });
    }
}