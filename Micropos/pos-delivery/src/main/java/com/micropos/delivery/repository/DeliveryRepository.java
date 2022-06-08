package com.micropos.delivery.repository;

import java.util.ArrayList;
import java.util.List;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.Order;

import org.springframework.stereotype.Repository;

@Repository
public class DeliveryRepository {
    
    private List<Delivery> deliveries = new ArrayList<>();

    private int num = 0;

    public Delivery createDelivery(Order order) {
        Delivery delivery = new Delivery(num, order);
        num++;
        deliveries.add(delivery);
        return delivery;
    }
    
    public List<Delivery> allDeliveries() {
        return deliveries;
    }

    public Delivery getDelivery(int id) {
        for(Delivery delivery : deliveries) {
            if(delivery.getId() == id) {
                return delivery;
            }
        }
        return null;
    }
}