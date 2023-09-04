package com.gbossoufolly.assivitoshopbackend.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String jwt;

    private boolean success;

    private String failureReason;
}
