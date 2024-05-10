package com.api.orders.handlers;

import com.api.utils.BaseHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderRoutesHandler extends BaseHandler {

    WebClient userServiceAddress;

    public OrderRoutesHandler() {
        initializeWebClient(System.getenv("ORDERS_SVC_ADDRESS"));
        this.userServiceAddress = WebClient.builder()
                .baseUrl(System.getenv("CUSTOMER_USER_SVC_ADDRESS") + "/user")
                .build();
    }

    public Mono<ServerResponse> getOrderUser(ServerRequest req) {
        String id = req.pathVariable("id");
        String authorizationHeader = req.headers().firstHeader("Authorization");
        return this.userServiceAddress.get()
                .uri("/get-order-user" + "/" + id)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .bodyToMono(Object.class)
                .flatMap(res ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res))
                .onErrorResume(this::handleResponseError);
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

    public Mono<ServerResponse> patchOrderStatus(ServerRequest req) {
        String id = req.pathVariable("id");
        String authorizationHeader = req.headers().firstHeader("Authorization");
        return req.bodyToMono(Object.class)
                .flatMap(body -> this.webClient.patch()
                        .uri( "/" +id + "/status")
                        .header("Authorization", authorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .exchangeToMono(res -> ServerResponse.status(res.statusCode()).build()))
                .onErrorResume(this::handleResponseError);
    }
}
