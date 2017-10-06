package com.laxtech.core.product.service;

import com.laxtech.core.product.model.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by magnus on 04/03/15.
 */
@RestController
public class ProductService {

    /**
     * Sample usage: curl $HOST:$PORT/product/1
     *
     * @param productId
     * @return
     */
    @RequestMapping("/product/{productId}")
    public Product getProduct(@PathVariable int productId) {

        return new Product(productId, "name", 123);
    }
}