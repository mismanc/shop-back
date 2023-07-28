package com.simple.webshop.services;

import com.simple.webshop.domain.Order;
import com.simple.webshop.model.OrderDTO;
import com.simple.webshop.model.ProductDTO;
import com.simple.webshop.model.ShippingOption;
import com.simple.webshop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @MockBean
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void saveOrder() {
        Order order = createOrder();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderDTO orderDTO = OrderDTO.builder()
                .name(order.getClientName())
                .address(order.getClientAddress())
                .shippingOption(ShippingOption.STANDARD_FREE)
                .build();
        OrderDTO savedOrder = orderService.saveOrder(orderDTO);
        assertNotNull(savedOrder.getId());
        assertEquals(savedOrder.getName(), orderDTO.getName());
        assertEquals(savedOrder.getAddress(), orderDTO.getAddress());
    }

    @Test
    void calculateTotalProductValue() {
        ProductDTO p1 = new ProductDTO(1L, "Macbook Pro", new BigDecimal("100"), "Apple");
        ProductDTO p2 = new ProductDTO(2L, "Yoğurt", new BigDecimal("200"), "Torku");
        BigDecimal totalProduct = orderService.calculateTotalProductValue(Arrays.asList(p1,p2));
        assertEquals(totalProduct, new BigDecimal("354.00"));
    }

    @Test
    void calculateTotalShippingValue() {
        OrderDTO orderDTO = OrderDTO.builder()
                .name("John Doe")
                .address("Türkiye")
                .shippingOption(ShippingOption.STANDARD_FREE)
                .build();

        assertEquals(orderService.calculateTotalShippingValue(orderDTO.getShippingOption()), BigDecimal.ZERO);

        orderDTO.setShippingOption(ShippingOption.EXPRESS);
        assertEquals(orderService.calculateTotalShippingValue(orderDTO.getShippingOption()), BigDecimal.valueOf(10));
    }

    private Order createOrder(){
        return Order.builder()
                .id(1L)
                .clientName("John Doe")
                .clientAddress("Türkiye")
                .totalProductValue(BigDecimal.valueOf(200))
                .totalShippingValue(BigDecimal.ZERO)
                .build();
    }
}
