package com.example.security.controller;

import com.example.security.dto.UserDto;
import com.example.security.entity.Order;
import com.example.security.entity.user.User;
import com.example.security.service.OrderService;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Transactional
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/user")
    @ResponseBody
    public String signup(@RequestBody UserDto userDto) {
        String result = userService.save(userDto);

        return result;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/login";
    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/mypage")
    public String showMyReservation(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderService.findByUser(user);
        model.addAttribute("orders", orders);


        return "/mypage";
    }


}