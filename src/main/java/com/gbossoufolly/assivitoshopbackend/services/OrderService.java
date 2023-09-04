package com.gbossoufolly.assivitoshopbackend.services;

import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.models.WebOrder;
import com.gbossoufolly.assivitoshopbackend.repository.WebOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final WebOrderRepository webOrderRepository;
    public OrderService(WebOrderRepository webOrderRepository) {

        this.webOrderRepository = webOrderRepository;
    }

    public List<WebOrder> getOrders(LocalUser user) {

        return webOrderRepository.findByUser(user);
    }
}
