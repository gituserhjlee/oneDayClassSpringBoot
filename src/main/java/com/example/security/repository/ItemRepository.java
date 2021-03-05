package com.example.security.repository;

import com.example.security.entity.Item;
import com.example.security.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long id);
    List<Item> findByUser(User user);
}
