package com.example.security.controller;

import com.example.security.entity.Item;
import com.example.security.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/admin")
    public String list(Model model){
        List<Item> items=itemService.findItems();
        model.addAttribute("items", items);
        return "admin";
    }
    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new Item());
        return "createItemForm";
    }

    @PostMapping("/items/new")
    public String create(Item i){
        Item item=new Item();
        item.setName(i.getName());
        item.setPrice(i.getPrice());
        item.setDescription(i.getDescription());

        itemService.save(item);
        return "redirect:/admin";
    }
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findById(itemId).get();
        Item item1 = new Item();
        item1.setId(item.getId());
        item1.setName(item.getName());
        item1.setPrice(item.getPrice());
        item1.setDescription(item.getDescription());
        model.addAttribute("form", item1);
        return "updateItemform";

    }

    @PostMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, @ModelAttribute("form") Item item) {

         itemService.update(itemId, item);
         return "redirect:/";

    }

}
