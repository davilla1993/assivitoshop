package com.gbossoufolly.assivitoshopbackend.api.controllers.order;

import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.models.WebOrder;
import com.gbossoufolly.assivitoshopbackend.services.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user) {
        return orderService.getOrders(user);

    }
}
