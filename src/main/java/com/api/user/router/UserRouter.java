package com.api.user.router;

import com.api.user.handlers.UserRoutesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserRoutesHandler userRoutesHandler) {
        return RouterFunctions
                .route()
                .nest(RequestPredicates.path("/api/user"), builder -> {
                    builder.POST("", userRoutesHandler::create);
                })
                .build();
    }
}
