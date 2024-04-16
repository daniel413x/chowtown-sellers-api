package com.api.restaurant.handlers;

import com.api.restaurant.dto.RestaurantDto;
//import com.api.restaurant.dto.RestaurantPATCHReq;
//import com.api.restaurant.dto.RestaurantPOSTReq;
import com.api.restaurant.dto.RestaurantPUTReq;
import com.api.utils.BaseHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RestaurantRoutesHandler extends BaseHandler {

    public RestaurantRoutesHandler() {
        initializeWebClient(System.getenv("RESTAURANT_SVC_ADDRESS"));
    }

    public Mono<ServerResponse> getByAuth0Id(ServerRequest req) {
        String auth0Id = req.pathVariable("auth0id");
        String authorizationHeader = req.headers().firstHeader("Authorization");
        return this.webClient.get()
                .uri("/" + auth0Id)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .bodyToMono(com.api.restaurant.dto.RestaurantDto.class)
                .flatMap(restaurantDto ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(restaurantDto))
                .onErrorResume(this::handleResponseError);
    }

    //  return a restaurant created with hardcoded values which the user can then update
    public Mono<ServerResponse> create(ServerRequest req) {
        String authorizationHeader = req.headers().firstHeader("Authorization");
        return this.webClient.post()
                        .uri("")
                        .header("Authorization", authorizationHeader)
                        .exchangeToMono(response -> {
                            return response.bodyToMono(RestaurantDto.class)
                                    .flatMap(restaurantDto -> ServerResponse
                                            .status(response.statusCode())
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(restaurantDto));
                        })
                .onErrorResume(this::handleResponseError);
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        String authorizationHeader = req.headers().firstHeader("Authorization");
        String auth0id = req.pathVariable("auth0id");
        System.out.println(auth0id);
        return req.bodyToMono(RestaurantPUTReq.class)
                .flatMap(body -> this.webClient.put()
                        .uri("/" + auth0id)
                        .header("Authorization", authorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .exchangeToMono(response -> {
                            return response.bodyToMono(RestaurantDto.class)
                                    .flatMap(restaurantDto -> ServerResponse
                                            .status(response.statusCode())
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(restaurantDto));
                        }))
                .onErrorResume(this::handleResponseError);
    }
}
