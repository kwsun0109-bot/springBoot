package com.example.jpastudy2.dto;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class BookRequestDto {
    private String title;
    private String author;
    private int price;
    private int stock;
    private Long categoryId;
}
