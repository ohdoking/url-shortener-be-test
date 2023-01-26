package com.github.vivyteam.url.api;

import com.github.vivyteam.url.api.request.ShortenedUrlRequest;
import com.github.vivyteam.url.service.UrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UrlController.class)
public class UrlControllerTest {

    @MockBean
    UrlService urlService;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void givenOriginalUrl_WhenCallPostShortUrl_ThenReturnShortenUrl() {
        // given
        ShortenedUrlRequest shortenedUrlRequest = getSampleShortenUrlRequest();
        BDDMockito.given(urlService.generateShortenUrl(any(URI.class), anyString())).willReturn(Mono.just("http://localhost:9000/shorten"));

        // when, then
        webClient.post()
                .uri("/url/short")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(shortenedUrlRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.shorten_url").isEqualTo("http://localhost:9000/shorten");

        Mockito.verify(urlService, times(1)).generateShortenUrl(any(URI.class), anyString());
    }

    @Test
    public void givenShortenUrl_WhenCallGetOriginalUrl_ThenReturnOriginalUrl() {
        // given
        String shortenUrlPath = "shorten";
        BDDMockito.given(urlService.getOriginalUrl(anyString())).willReturn(Mono.just("http://sample.com"));

        // when, then
        webClient.get()
                .uri("/url/short/{shortenUrlPath}/original", shortenUrlPath)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.url").isEqualTo("http://sample.com");

        Mockito.verify(urlService, times(1)).getOriginalUrl(anyString());
    }

    @Test
    public void givenShortenUrl_WhenCallGetRedirectOriginalUrl_ThenReturnMovedPermanentlyHttpStatus() {
        // given
        String shortenUrl = "shorten";
        BDDMockito.given(urlService.getOriginalUrl(anyString())).willReturn(Mono.just("http://sample.com"));

        // when, then
        webClient.get()
                .uri("/{shortenUrl}", shortenUrl)
                .exchange()
                .expectStatus().is3xxRedirection();

        Mockito.verify(urlService, times(1)).getOriginalUrl(anyString());
    }

    private ShortenedUrlRequest getSampleShortenUrlRequest() {
        return new ShortenedUrlRequest("https://github.com/VivyTeam/url-shortener-be-test");
    }
}