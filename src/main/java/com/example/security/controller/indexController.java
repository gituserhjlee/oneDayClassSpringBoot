package com.example.security.controller;

import com.example.security.entity.Item;
import com.example.security.entity.user.User;
import com.example.security.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userAuth = user.getAuth();
        model.addAttribute("userAuth",userAuth);
        model.addAttribute("items",items);
        return "main";
    }
}
