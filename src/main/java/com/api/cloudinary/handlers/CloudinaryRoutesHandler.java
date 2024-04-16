package com.api.cloudinary.handlers;

import com.api.cloudinary.dto.CloudinaryDto;
import com.api.cloudinary.dto.CloudinaryPOSTReq;
import com.api.utils.BaseHandler;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CloudinaryRoutesHandler extends BaseHandler {

    public CloudinaryRoutesHandler() {
        initializeWebClient(System.getenv("CLOUDINARY_SVC_ADDRESS"));
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        String authorizationHeader = req.headers().firstHeader("Authorization");
        return req.multipartData().map(multipart -> multipart.toSingleValueMap().get("file"))
                .cast(FilePart.class)
                .flatMap(filePart ->
                            webClient.post()
                                    .uri("/image")
                                    .header("Authorization", authorizationHeader)
                                    // contentType will be set automatically when using .body(BodyInserters.fromMultipartData())
                                    // "file" is the name of the part expected by the receiving service
                                    .body(BodyInserters.fromMultipartData("image", filePart))
                                    .exchangeToMono(response -> {
                                        return response.bodyToMono(CloudinaryDto.class)
                                                .flatMap(responseBody -> ServerResponse
                                                        .status(response.statusCode())
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .bodyValue(responseBody));
                                    })
                )
                .onErrorResume(this::handleResponseError);
    }
}
