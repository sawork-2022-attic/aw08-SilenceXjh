package com.micropos.delivery.service;

import java.util.List;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.Order;
import com.micropos.delivery.repository.DeliveryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    
    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery createDelivery(Order order) {
        return deliveryRepository.createDelivery(order);
    }

    public List<Delivery> allDeliveries() {
        return deliveryRepository.allDeliveries();
    }

    public Delivery getDelivery(int id) {
        return deliveryRepository.getDelivery(id);
    }

}