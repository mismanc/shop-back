package com.simple.webshop.services;

import com.simple.webshop.domain.Order;
import com.simple.webshop.model.OrderDTO;
import com.simple.webshop.model.ProductDTO;
import com.simple.webshop.model.ShippingOption;
import com.simple.webshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                .totalProductValue(calculateTotalProductValue(orderDTO.getProducts()))
                .totalShippingValue(calculateTotalShippingValue(orderDTO.getShippingOption()))
                .clientAddress(orderDTO.getAddress())
                .clientName(orderDTO.getName())
                .build();
        Order savedOrder = orderRepository.save(order);
        orderDTO.setId(savedOrder.getId());
        return orderDTO;
    }

    public BigDecimal calculateTotalProductValue(List<ProductDTO> products) {
        BigDecimal kdv = new BigDecimal("1.18");
        // Add KDV and calculate total
        return products.stream().map(p -> p.price().multiply(kdv)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalShippingValue(ShippingOption shippingOption) {
        return shippingOption.equals(ShippingOption.STANDARD_FREE) ? BigDecimal.ZERO : BigDecimal.valueOf(10);
    }
}
