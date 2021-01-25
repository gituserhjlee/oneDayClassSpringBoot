package com.example.security.controller;

import com.example.security.entity.Item;
import com.example.security.entity.user.User;
import com.example.security.service.ItemService;
import com.example.security.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/user/buy")
    public String createForm(Model model){
        List<Item> items=itemService.findItems();
        model.addAttribute("items",items);

        return "orderForm";

    }
    @PostMapping("/user/buy")
    public String order(@RequestParam("itemId") Long itemId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getCode();
        orderService.order(userId, itemId);
        return "redirect:/";
    }


}
