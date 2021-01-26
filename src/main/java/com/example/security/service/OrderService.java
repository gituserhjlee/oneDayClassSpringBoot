package com.example.security.service;

import com.example.security.entity.Item;
import com.example.security.entity.Order;
import com.example.security.entity.user.User;
import com.example.security.repository.ItemRepository;
import com.example.security.repository.OrderRepositoy;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepositoy orderRepositoy;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public Long order(Long userId, Long itemId){
        //엔터티 조회
        User user=userRepository.findById(userId).get();
        Item item=itemRepository.findById(itemId).get();

        //주문 생성
        Order order= Order.createOrder(user, item);
        //주문 저장
        orderRepositoy.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId){
        Order order=orderRepositoy.findById(orderId).get();
        order.cancel();
    }

    public List<Order> findOrders(){
        return orderRepositoy.findAll();
    }
}
