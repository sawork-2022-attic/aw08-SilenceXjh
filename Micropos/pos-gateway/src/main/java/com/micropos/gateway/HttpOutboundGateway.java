package com.micropos.gateway;

import com.micropos.dto.DeliveryDto;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.stereotype.Component;

@Component
public class HttpOutboundGateway {
    @Bean
    public IntegrationFlow outGate() {
        return IntegrationFlows.from("sampleChannel")
                .handle(WebFlux.outboundGateway(m -> "http://localhost:5555/api/deliveries/" + m.getPayload())
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(DeliveryDto.class)
                        )
                .get();
    }
}