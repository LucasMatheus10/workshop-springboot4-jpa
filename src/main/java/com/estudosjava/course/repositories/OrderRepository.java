package com.estudosjava.course.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.estudosjava.course.entities.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
