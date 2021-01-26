package com.example.security.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Item {
    @Id
    @Column(name="itemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="itemName")
    private String name;

    @Column(name="price")
    private int price;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "user")
    private List<Order> orders=new ArrayList<>();



    @Builder
    public Item(String name, int price,String description){
        this.name=name;
        this.price=price;
        this.description=description;
    }
    public void update(String name, int price, String description) {
        this.name=name;
        this.price=price;
        this.description=description;
    }

}
