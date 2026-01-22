package com.estudosjava.course.resources;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.estudosjava.course.dto.UserDTO;
import com.estudosjava.course.dto.UserInsertDTO;
import com.estudosjava.course.dto.UserOrdersDTO;
import com.estudosjava.course.dto.UserUpdateDTO;
import com.estudosjava.course.services.UserServices;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Usuários", description = "Gerenciador de usuários")
public class UserResource {
    
    @Autowired
    private UserServices service;

    @GetMapping
    @Operation(summary = "Buscar usuários", description = "Busca todos os usuários cadastrados no sistema")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list =  service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar usuário", description = "Busca o usuário desejado passando o id do usuário")
    public ResponseEntity<UserOrdersDTO> findById(@PathVariable Long id) {
        UserOrdersDTO obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/search")
    @Operation(summary = "Buscar usuário por email", description = "Busca o usuário desejado passando o email do usuário")
    public ResponseEntity<UserDTO> findByEmail(@RequestParam String email) {
        User user = (User) repository.findByEmail(email); 
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @PostMapping
    @Operation(summary = "Inserir usuário", description = "Cadastra um novo usuário no sistema")
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }
    
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Remover usuário", description = "Remove o usuário desejado passando o id do usuário e os atributos que se deseja atualizar")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza o usuário desejado passando o id do usuário e os atributos que se deseja atualizar")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        UserDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }
    
}
