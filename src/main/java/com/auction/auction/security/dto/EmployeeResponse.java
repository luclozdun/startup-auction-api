package com.auction.auction.security.dto;

import lombok.Data;

@Data
public class EmployeeResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastname;
    private String code;
    private String dni;
    private String number;
}
