package com.example.security.entity;

import com.example.security.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ORDER_DETAIL")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="code") //매핑을 뭘로할거냐임. 즉 외래키
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "itemId")
    private Item item;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name="count")
    private int count; //주문 인원수

    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public static Order createOrder(User user,Item item, int count){
        Order order=new Order();
        order.setUser(user);
        order.setItem(item);
        order.setStatus(OrderStatus.ORDER);
        order.setCount(count);
        order.setOrderDate(LocalDateTime.now());
        item.removeStock(count);
        return order;
    }

    public void cancel(){
        this.setStatus(OrderStatus.CANCEL);
        getItem().addStock(count);
    }


}
