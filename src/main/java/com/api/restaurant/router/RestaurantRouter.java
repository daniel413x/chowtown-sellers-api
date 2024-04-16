package com.api.restaurant.router;

import com.api.restaurant.handlers.RestaurantRoutesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class RestaurantRouter {

    @Bean
    public RouterFunction<ServerResponse> restaurantRoutes(RestaurantRoutesHandler restaurantRoutesHandler) {
        return RouterFunctions
                .route()
                .nest(RequestPredicates.path("/api/restaurant"), builder -> {
                    builder.GET("/{auth0id}", restaurantRoutesHandler::getByAuth0Id);
                    builder.POST("", restaurantRoutesHandler::create);
                    builder.PUT("/{auth0id}", restaurantRoutesHandler::update);
                })
                .build();
    }
}
