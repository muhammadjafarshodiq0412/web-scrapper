package com.api.webscrapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private String productName;
    private String description;
    private String image;
    private String price;
    private String rating;
    private String store;
}
