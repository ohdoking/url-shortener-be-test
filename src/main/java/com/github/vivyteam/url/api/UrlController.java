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
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/url/short")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ShortenedUrlResponse> shortUrl(ServerHttpRequest serverHttpRequest, @Valid @RequestBody ShortenedUrlRequest shortenedUrlRequest) {
        log.debug("Request shortUrl, original_url : " + shortenedUrlRequest.getUrl());
        return urlService.generateShortenUrl(serverHttpRequest.getURI(), shortenedUrlRequest.getUrl())
                .publishOn(Schedulers.boundedElastic())
                .map(shortenUrlString -> ShortenedUrlResponse.builder()
                        .shortenUrl(shortenUrlString)
                        .build()
                );
    }

    @GetMapping("/url/short/{shortenUrlPath}/original")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OriginalUrlResponse> getOriginalUrl(@PathVariable final String shortenUrlPath) {
        return urlService.getOriginalUrl(shortenUrlPath)
                .publishOn(Schedulers.boundedElastic())
                .map(originalUrl -> OriginalUrlResponse.builder()
                        .url(originalUrl)
                        .build()
                );
    }

    @GetMapping("/{shortenedUrl}")
    public Mono<ResponseEntity> redirectOriginalUrl(@PathVariable final String shortenedUrl) {
        return urlService.getOriginalUrl(shortenedUrl)
                .publishOn(Schedulers.boundedElastic())
                .map(originalUrl -> ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .header(HttpHeaders.LOCATION, originalUrl)
                        .build()
                );
    }


}
