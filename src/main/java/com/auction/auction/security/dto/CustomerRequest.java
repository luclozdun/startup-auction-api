package com.auction.auction.security.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomerRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastname;
    private String dni;
    private String number;
    @JsonFormat(pattern = "yy/MM/dd")
    private Date brithday;
}
