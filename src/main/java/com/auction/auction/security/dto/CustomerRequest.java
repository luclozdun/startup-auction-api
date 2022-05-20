package com.auction.auction.security.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

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
    @JsonFormat(pattern = "yyyy/MM/dd", shape = Shape.STRING)
    private Date brithday;
}
