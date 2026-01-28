package com.estudosjava.course.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.estudosjava.course.dto.UserDTO;
import com.estudosjava.course.dto.UserInsertDTO;
import com.estudosjava.course.dto.UserOrdersDTO;
import com.estudosjava.course.dto.UserUpdateDTO;
import com.estudosjava.course.entities.User;
import com.estudosjava.course.entities.enums.UserRole;
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

    @Mock
    private PasswordEncoder passwordEncoder;

    private Long existingId;
    private Long nonExistingId;
    private User user;
    private UserUpdateDTO updateDTO;
    private UserInsertDTO insertDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;

        user = new User(existingId, "Maria Brown", "maria@gmail.com", "988888888", "123456", UserRole.USER, new ArrayList<>());
        
        insertDTO = new UserInsertDTO("Maria Brown", "maria@gmail.com", "988888888", "123456", UserRole.USER);
        updateDTO = new UserUpdateDTO("Maria Green", "maria_green@gmail.com", "977777777", UserRole.USER);
    }

    @Test
    public void findAllShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(user));
        List<UserDTO> result = service.findAll();
        Assertions.assertNotNull(result);
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
    public void insertShouldReturnUserDTOWhenDataIsValid() {
        // Simula que o e-mail não existe (validação de unicidade)
        when(repository.findByEmail(anyString())).thenReturn(null);
        when(passwordEncoder.encode(any())).thenReturn("hashed_password");
        when(repository.save(any())).thenReturn(user);

        UserDTO result = service.insert(insertDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(UserRole.USER, result.role());
        verify(repository).findByEmail(insertDTO.email());
        verify(repository).save(any());
    }

    @Test
    public void insertShouldThrowDatabaseExceptionWhenEmailAlreadyExists() {
        // Simula que o e-mail já está cadastrado
        when(repository.findByEmail(insertDTO.email())).thenReturn(user);

        Assertions.assertThrows(DatabaseException.class, () -> {
            service.insert(insertDTO);
        });

        verify(repository, Mockito.never()).save(any());
    }

    @Test
    public void updateShouldReturnUserDTOWhenIdExists() {
        when(repository.getReferenceById(existingId)).thenReturn(user);
        when(repository.save(any())).thenReturn(user);

        UserDTO result = service.update(existingId, updateDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Maria Green", result.name());
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