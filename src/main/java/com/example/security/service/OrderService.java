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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepositoy orderRepositoy;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public String order(Long userId, Long itemId, int stock) {
        //엔터티 조회
        User user = userRepository.findById(userId).get();
        Item item = itemRepository.findById(itemId).get();
        if(item.getStock()>=stock){
            //주문 생성
            Order order = Order.createOrder(user, item, stock);
            //주문 저장
            orderRepositoy.save(order);
            return "성공";
        }
        return "실패";

    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepositoy.findById(orderId).get();
        order.cancel();
    }

    public List<Order> findOrders(Long userId) {
        List<Order> allList=orderRepositoy.findAll();
        List<Order> returnList=new ArrayList<>();
        for(Order o:allList){
            if(o.getItem().getUser().getCode()==userId){
                returnList.add(o);
            }
        }
        return returnList;
    }

    public List<Order> findByUser(User user) {
        return orderRepositoy.findByUser(user);
    }

    public Optional<Order> findById(Long id){
        return orderRepositoy.findById(id);
    }
}
