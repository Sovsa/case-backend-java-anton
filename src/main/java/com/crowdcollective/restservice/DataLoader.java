package com.crowdcollective.restservice;

import com.crowdcollective.restservice.datamodel.Product;
import com.crowdcollective.restservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<Product> products = productRepository.findAllById(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("Creating base products");

        List<Integer> productIds = products.stream().map(Product::getProductid).collect(Collectors.toList());

        if (!productIds.contains(1)) {
            Product penProduct = new Product();
            penProduct.setProductid(1);
            penProduct.setDescription("Pencil");
            penProduct.setName("Pen");
            penProduct.setPricePerArticle(new BigDecimal(10.0000));
            productRepository.save(penProduct);
        }

        if (!productIds.contains(2)) {
            Product paperProduct = new Product();
            paperProduct.setProductid(2);
            paperProduct.setDescription("Paper A4");
            paperProduct.setName("Paper");
            paperProduct.setPricePerArticle(new BigDecimal(0.2500));
            productRepository.save(paperProduct);
        }

        if (!productIds.contains(3)) {
            Product notePadProduct = new Product();
            notePadProduct.setProductid(3);
            notePadProduct.setDescription("Notepad with 50 pages, A5");
            notePadProduct.setName("Notepad");
            notePadProduct.setPricePerArticle(new BigDecimal(50.0000));
            productRepository.save(notePadProduct);
        }

        if (!productIds.contains(4)) {
            Product eraserProduct = new Product();
            eraserProduct.setProductid(4);
            eraserProduct.setDescription("Rubber eraser");
            eraserProduct.setName("Eraser");
            eraserProduct.setPricePerArticle(new BigDecimal(5.0000));
            productRepository.save(eraserProduct);
        }

        if (!productIds.contains(5)) {
            Product bicycleProduct = new Product();
            bicycleProduct.setProductid(5);
            bicycleProduct.setDescription("Red bicycle, three gears");
            bicycleProduct.setName("Bicycle");
            bicycleProduct.setPricePerArticle(new BigDecimal(500.0000));
            productRepository.save(bicycleProduct);
        }
    }
}
