package com.example.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private Long ItemId;
    private String ItemName;
    private int stock;
}
