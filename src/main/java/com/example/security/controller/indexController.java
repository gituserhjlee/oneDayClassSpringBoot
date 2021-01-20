package com.example.security.controller;

import com.example.security.entity.Item;
import com.example.security.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class indexController {
    private final ItemService itemService;

    @GetMapping("/")
    public String list(Model model){
        List<Item> items=itemService.findItems();
        model.addAttribute("items",items);
        return "main";
    }
}
