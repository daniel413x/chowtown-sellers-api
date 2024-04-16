package com.api.cloudinary.router;

import com.api.cloudinary.handlers.CloudinaryRoutesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class CloudinaryRouter {

    @Bean
    public RouterFunction<ServerResponse> cloudinaryRoutes(CloudinaryRoutesHandler cloudinaryRoutesHandler) {
        return RouterFunctions
                .route()
                .nest(RequestPredicates.path("/api/cloudinary"), builder -> {
                    builder.POST("", cloudinaryRoutesHandler::create);
                })
                .build();
    }
}
