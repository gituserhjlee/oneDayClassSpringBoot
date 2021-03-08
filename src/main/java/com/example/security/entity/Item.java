package com.example.security.entity;

import com.example.security.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Item {
    @Id
    @Column(name="itemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="code") //매핑을 뭘로할거냐임. 즉 외래키
    private User user;

    @Column(name="itemName")
    private String name;

    @Column(name="price")
    private int price;

    @Column(name="stock")
    private int stock;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "user")
    private List<Order> orders=new ArrayList<>();

    public void setUser(User user){
        this.user=user;
        user.getItems().add(this);
    }

    @Builder
    public Item(String name, int price,String description ,int stock, User user){
        this.name=name;
        this.price=price;
        this.description=description;
        this.stock=stock;
        this.user=user;
    }
    public void update(String name, int price, String description ,int stock) {
        this.name=name;
        this.price=price;
        this.description=description;
        this.stock=stock;
    }
    public void addStock(int quantity){
        this.stock+=quantity;
    }
    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        this.stock = restStock;
    }

}
