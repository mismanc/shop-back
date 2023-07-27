package com.simple.webshop.bootstrap;

import com.simple.webshop.domain.Brand;
import com.simple.webshop.domain.Product;
import com.simple.webshop.repository.BrandRepository;
import com.simple.webshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class ProductLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    @Override
    public void run(String... args) {
        loadProducts();
    }

    private void loadProducts() {
        if (brandRepository.count() == 0) {
            Brand torkuBrand = Brand.builder().name("Torku").build();
            Brand maviBrand = Brand.builder().name("Mavi").build();
            Brand appleBrand = Brand.builder().name("Apple").build();
            Brand lgBrand = Brand.builder().name("LG").build();

            brandRepository.saveAll(Arrays.asList(torkuBrand, maviBrand, appleBrand, lgBrand));
            Product p1 = Product.builder().name("Yoğurt").price(30.00).brand(torkuBrand).build();
            Product p2 = Product.builder().name("Moon Çikolata").price(35.22).brand(torkuBrand).build();
            Product p3 = Product.builder().name("Çubuk Kraker").price(20.00).brand(torkuBrand).build();
            Product p4 = Product.builder().name("T-Shirt Beyaz").price(324.99).brand(maviBrand).build();
            Product p5 = Product.builder().name("Short").price(424.99).brand(maviBrand).build();
            Product p6 = Product.builder().name("Macbook Pro").price(80000.00).brand(appleBrand).build();
            Product p7 = Product.builder().name("Iphone 17 Pro Max").price(37299.00).brand(appleBrand).build();
            Product p8 = Product.builder().name("TV OLED65G36 65 inch").price(96029.10).brand(lgBrand).build();
            Product p9 = Product.builder().name("Monitor 24ML60MP-B").price(96029.10).brand(lgBrand).build();

            productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));
        }
    }
}
