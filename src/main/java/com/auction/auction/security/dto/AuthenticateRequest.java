package com.auction.auction.security.dto;

import lombok.Data;

@Data
public class AuthenticateRequest {
    private String username;
    private String password;
}
