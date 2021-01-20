package com.example.security.service;

import com.example.security.entity.Item;
import com.example.security.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Long save(Item item) {
        return itemRepository.save(Item.builder()
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .build()).getId();


    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

}
