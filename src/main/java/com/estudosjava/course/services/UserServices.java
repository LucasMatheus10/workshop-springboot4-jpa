package com.estudosjava.course.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.estudosjava.course.dto.UserDTO;
import com.estudosjava.course.dto.UserInsertDTO;
import com.estudosjava.course.dto.UserOrdersDTO;
import com.estudosjava.course.entities.User;
import com.estudosjava.course.repositories.UserRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServices {

    @Autowired
    private UserRepository repository;

    public List<UserDTO> findAll() {
        List<User> list = repository.findAll();
        return list.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserOrdersDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new UserOrdersDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
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
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User user = repository.getReferenceById(id);
            updateData(user, dto);
            user = repository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void loadData(User entity, UserInsertDTO obj) {
        entity.setName(obj.name());
        entity.setEmail(obj.email());
        entity.setPhone(obj.phone());
        entity.setPassword(obj.password());
    }

    private void updateData(User user, UserDTO dto) {
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
    }
}
