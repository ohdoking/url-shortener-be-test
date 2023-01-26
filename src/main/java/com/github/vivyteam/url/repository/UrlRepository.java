package com.github.vivyteam.url.repository;

import com.github.vivyteam.url.model.Url;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UrlRepository extends ReactiveCrudRepository<Url, Long> {
    Mono<Url> findUrlByOriginal(String original);
}
