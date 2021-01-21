package com.example.security.service;

import com.example.security.entity.Item;
import com.example.security.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
   public Long update(Long id, Item item){
        Item it=itemRepository.findById(id).orElseThrow(()->new IllegalArgumentException("없는 게시물"));
        it.update(item.getName(), item.getPrice(),item.getDescription());
        return id;
   }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Optional<Item> findById(Long id){
        return itemRepository.findById(id);
    }

    public void deleteItem(Long itemId){
        itemRepository.deleteById(itemId);

    }
}
