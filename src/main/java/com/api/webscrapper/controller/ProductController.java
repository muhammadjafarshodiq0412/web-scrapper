package com.api.webscrapper.controller;

import com.api.webscrapper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrapper")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public @ResponseBody String getDataTokopedia() {
        return productService.getDataTokopedia();
    }
}
