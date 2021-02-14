package com.example.security.repository;

import com.example.security.entity.Order;
import com.example.security.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoy extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    List<Order> findByUser(User user);



}
