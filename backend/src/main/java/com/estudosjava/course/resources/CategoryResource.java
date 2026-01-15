package com.estudosjava.course.resources;


import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.estudosjava.course.dto.CategoryDTO;
import com.estudosjava.course.dto.CategoryInsertDTO;
import com.estudosjava.course.dto.CategoryProductsDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.estudosjava.course.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Categorias", description = "Gerenciador de categorias")
public class CategoryResource {

    @Autowired
    private CategoryService service;
    
    @GetMapping
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias cadastradas")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list =  service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar categoria", description = "Busca a categoria desejada pelo id da categoria")
    public ResponseEntity<CategoryProductsDTO> findById(@PathVariable Long id) {
        CategoryProductsDTO obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Operation(summary = "Inserir categoria", description = "Insere uma nova categoria adicionando todos os seus atributos")
    public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryInsertDTO dto) {
        CategoryDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }
    
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Remover categoria", description = "Remove a categoria desejada pelo id da categoria")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza a categoria desejada passando o id e os atributos que se deseja atualizar")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryProductsDTO dto) {
        CategoryDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

}
