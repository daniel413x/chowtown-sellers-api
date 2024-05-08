package com.api.orders.router;

import com.api.orders.handlers.OrderRoutesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class OrdersRouter {

    @Bean
    public RouterFunction<ServerResponse> orderRoutes(OrderRoutesHandler orderRoutesHandler) {
        return RouterFunctions
                .route()
                .nest(RequestPredicates.path("/api/orders"), builder -> {
                    builder.GET("/get-restaurant-orders", orderRoutesHandler::getRestaurantOrders);
                })
                .build();
    }
}
