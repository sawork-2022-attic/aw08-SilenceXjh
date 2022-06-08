package com.micropos.delivery.rest;

import java.util.List;

import com.micropos.api.DeliveriesApi;
import com.micropos.delivery.mapper.DeliveryMapper;
import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.service.DeliveryService;
import com.micropos.dto.DeliveryDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DeliveryController implements DeliveriesApi {
    
    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public ResponseEntity<List<DeliveryDto>> allDeliveries() {
        List<Delivery> deliveries = deliveryService.allDeliveries();
        if(deliveries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deliveryMapper.toDeliveryDtos(deliveries), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeliveryDto> showDeliveryById(Integer deliveryId) {
        Delivery delivery = deliveryService.getDelivery(deliveryId);
        if(delivery == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deliveryMapper.toDeliveryDto(delivery), HttpStatus.OK);
    }
}