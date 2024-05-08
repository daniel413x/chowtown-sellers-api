package com.api.orders.handlers;

import com.api.utils.BaseHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderRoutesHandler extends BaseHandler {

    public OrderRoutesHandler() {
        initializeWebClient(System.getenv("ORDERS_SVC_ADDRESS"));
    }

    public Mono<ServerResponse> getRestaurantOrders(ServerRequest req) {
        String authorizationHeader = req.headers().firstHeader("Authorization");
        return this.webClient.get()
                .uri("/get-restaurant-orders")
                .header("Authorization", authorizationHeader)
                .retrieve()
                .bodyToMono(Object.class)
                .flatMap(res ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res))
                .onErrorResume(this::handleResponseError);
    }
}
