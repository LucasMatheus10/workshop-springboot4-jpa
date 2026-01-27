package com.estudosjava.course.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import com.estudosjava.course.dto.UserDTO;
import com.estudosjava.course.dto.UserInsertDTO;
import com.estudosjava.course.dto.UserOrdersDTO;
import com.estudosjava.course.dto.UserUpdateDTO;
import com.estudosjava.course.entities.User;
import com.estudosjava.course.repositories.UserRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import com.estudosjava.course.dto.PasswordResetDTO;
@Service
public class UserServices {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    UserServices(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        List<User> list = repository.findAll();
        return list.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserOrdersDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new UserOrdersDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDetails findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        // Verifica se o email já está cadastrado
        if (repository.findByEmail(dto.email()) != null) {
            throw new DatabaseException("Este e-mail já está cadastrado no sistema.");
        }
        User user = new User();
        loadData(user, dto);
        user = repository.save(user);
        return new UserDTO(user);
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
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User user = repository.getReferenceById(id);
            updateData(user, dto);
            user = repository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public void updatePassword(Long id, PasswordResetDTO dto) {
        try {
            User user = repository.getReferenceById(id);

            if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
                throw new DatabaseException("A nova senha não pode ser igual à senha atual.");
            }

            user.setPassword(passwordEncoder.encode(dto.newPassword()));
            repository.save(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void loadData(User entity, UserInsertDTO obj) {
        entity.setName(obj.name());
        entity.setEmail(obj.email());
        entity.setPhone(obj.phone());
        entity.setPassword(passwordEncoder.encode(obj.password()));
    }

    private void updateData(User user, UserUpdateDTO dto) {
        if (dto.name() != null) {
            user.setName(dto.name());
        }
        if (dto.email() != null) {
            user.setEmail(dto.email());
        }
        if (dto.phone() != null) {
            user.setPhone(dto.phone());
        }
    }
}
