package com.micropos.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.stereotype.Component;

@Component
public class HttpInboundGateway {

    @Bean
    public IntegrationFlow inGate() {
        return IntegrationFlows.from(WebFlux.inboundGateway("/api/deliveries/{deliveryId}")
                .requestMapping(r -> r.methods(HttpMethod.GET))
                .payloadExpression("#pathVariables.deliveryId"))
                .channel("sampleChannel")
                .get();
    }
}