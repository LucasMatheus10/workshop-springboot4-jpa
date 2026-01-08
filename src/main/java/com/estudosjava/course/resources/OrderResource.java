package com.estudosjava.course.resources;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.estudosjava.course.dto.OrderDTO;
import com.estudosjava.course.dto.OrderInsertDTO;
import com.estudosjava.course.dto.OrderStatusDTO;
import com.estudosjava.course.dto.OrderSummaryDTO;
import com.estudosjava.course.dto.PaymentDTO;
import com.estudosjava.course.services.OrderServices;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
    
    @Autowired
    private OrderServices service;

    @GetMapping
    public ResponseEntity<List<OrderSummaryDTO>> findAll() {
        List<OrderSummaryDTO> list =  service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderInsertDTO dto) {
        OrderDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @Valid @RequestBody OrderStatusDTO dto) {
        OrderDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<OrderDTO> cancel(@PathVariable Long id) {
        OrderDTO dto = service.cancel(id);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}/payment")
    public ResponseEntity<OrderDTO> setPayment(@PathVariable Long id, @Valid @RequestBody PaymentDTO dto) {
        OrderDTO newDto = service.setPayment(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

}
