package com.example.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {//회원가입 폼으로 받을 유저정보 매핑할 객체임
    private String email;
    private String password;
    private String auth;
}