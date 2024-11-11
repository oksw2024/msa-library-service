package com.example.libraryapi.model;

import lombok.Data;

@Data
public class Library {
    private String libCode;
    private String libName;
    private String address;
    private String tel;
    private String fax;
    private String latitude;
    private String longitude;
    private String homepage;
    private String closed;
    private String operatingTime;
}
