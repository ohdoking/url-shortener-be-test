package com.github.vivyteam.url.api;

import com.github.vivyteam.url.api.contract.OriginalUrlResponse;
import com.github.vivyteam.url.api.contract.ShortenedUrlResponse;
import com.github.vivyteam.url.api.request.ShortenedUrlRequest;
import com.github.vivyteam.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/url/short")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ShortenedUrlResponse> shortUrl(ServerHttpRequest serverHttpRequest, @RequestBody ShortenedUrlRequest shortenedUrlRequest) {
        log.info("url : " + shortenedUrlRequest.getUrl());
        //@TODO add url validation
        String shortenUrl = urlService.generateShortenUrl(serverHttpRequest.getURI(), shortenedUrlRequest.getUrl());
        return Mono.just(new ShortenedUrlResponse(shortenUrl));
    }

    @GetMapping("/url/{shortenedUrl}/original")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OriginalUrlResponse> getOriginalUrl(@PathVariable final String shortenedUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenedUrl);
        return Mono.just(new OriginalUrlResponse(originalUrl));
    }

    @GetMapping("/{shortenedUrl}")
    public ResponseEntity redirectOriginalUrl(@PathVariable final String shortenedUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenedUrl);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }


}
