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
public class OrderController {
    private final ItemService itemService;

    @GetMapping("/user/buy")
    public String createForm(Model model){
        List<Item> items=itemService.findItems();
        model.addAttribute("items",items);

        return "orderForm";

    }


}
