package com.github.vivyteam.url.service;

import com.github.vivyteam.url.exception.NoUrlException;
import com.github.vivyteam.url.model.Url;
import com.github.vivyteam.url.repository.UrlRepository;
import com.github.vivyteam.url.util.Base62Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final Base62Util base62Util;
    private final UrlRepository urlRepository;

    public Mono<String> generateShortenUrl(URI uri, String originalUrl) {
        return urlRepository.findUrlByOriginal(originalUrl)
                .switchIfEmpty(Mono.defer(() -> urlRepository.save(Url.builder().original(originalUrl).build())))
                .map(url -> {
                    String encodedId = base62Util.encode(url.getId());
                    return buildShortenUrl(uri, encodedId);
                });
    }

    private String buildShortenUrl(URI uri, String encodedId) {
        return String.format("%s://%s:%s/%s", uri.getScheme(), uri.getHost(), uri.getPort(), encodedId);
    }

    public Mono<String> getOriginalUrl(String shortenUrl) {
        long id = base62Util.decode(shortenUrl);
        return urlRepository.findById(id)
                .switchIfEmpty(Mono.error(new NoUrlException("Original url doesn't exist, id : " + id)))
                .map(url -> url.getOriginal());
    }

}
