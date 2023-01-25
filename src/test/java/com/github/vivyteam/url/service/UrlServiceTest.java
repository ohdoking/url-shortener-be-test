package com.github.vivyteam.url.service;

import com.github.vivyteam.url.model.Url;
import com.github.vivyteam.url.repository.UrlRepository;
import com.github.vivyteam.url.util.Base62Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.NoResultException;
import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    UrlService urlService;

    @Mock
    private Base62Util base62Util;
    @Mock
    private UrlRepository urlRepository;

    @Test
    public void givenOriginalUrl_whenExecuteGenerateShortenUrl_ThenReturnShortenUrl() {
        // given
        URI uri = URI.create("http://localhost:9000/short");
        String originalUrl = "https://sample.com";

        BDDMockito.given(urlRepository.findUrlByOriginal(anyString())).willReturn(Optional.empty());
        BDDMockito.given(urlRepository.save(any(Url.class))).willReturn(Url.builder().id(1l).original(originalUrl).build());
        BDDMockito.given(base62Util.encode(anyLong())).willReturn("shorten");

        // when
        String result = urlService.generateShortenUrl(uri, originalUrl);

        // then
        assertEquals("http://localhost:9000/shorten", result);

        // verify
        Mockito.verify(urlRepository, times(1)).findUrlByOriginal(anyString());
        Mockito.verify(urlRepository, times(1)).save(any(Url.class));
        Mockito.verify(base62Util, times(1)).encode(anyLong());
    }

    @Test
    public void givenExistOriginalUrl_whenExecuteGenerateShortenUrl_ThenReturnShortenUrl() {
        // given
        URI uri = URI.create("http://localhost:9000/short");
        String originalUrl = "https://sample.com";

        BDDMockito.given(urlRepository.findUrlByOriginal(anyString())).willReturn(Optional.of(Url.builder().id(1l).original(originalUrl).build()));
        BDDMockito.given(base62Util.encode(anyLong())).willReturn("shorten");

        // when
        String result = urlService.generateShortenUrl(uri, originalUrl);

        // then
        assertEquals("http://localhost:9000/shorten", result);

        // verify
        Mockito.verify(urlRepository, times(1)).findUrlByOriginal(anyString());
        Mockito.verify(urlRepository, never()).save(any(Url.class));
        Mockito.verify(base62Util, times(1)).encode(anyLong());
    }

    @Test
    public void givenShortenUrl_whenExecuteGetOriginalUrl_ThenReturnOriginalUrl() {
        //given
        String shortenUrl = "shorten";

        BDDMockito.given(base62Util.decode(anyString())).willReturn(1L);
        BDDMockito.given(urlRepository.findById(anyLong())).willReturn(Optional.of(
                Url.builder()
                        .id(1L)
                        .original("https://sample.com")
                        .build()
        ));

        // when
        String result = urlService.getOriginalUrl(shortenUrl);

        // then
        assertEquals("https://sample.com", result);

        // verify
        Mockito.verify(urlRepository, times(1)).findById(anyLong());
        Mockito.verify(base62Util, times(1)).decode(anyString());
    }

    @Test
    public void givenNonExistShortenUrl_whenExecuteGetOriginalUrl_ThenThrowNoResultException() {
        //given
        String shortenUrl = "shorten";

        BDDMockito.given(base62Util.decode(anyString())).willReturn(1L);
        BDDMockito.given(urlRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Exception exception = assertThrows(NoResultException.class, () -> {
            urlService.getOriginalUrl(shortenUrl);
        });

        // then
        assertEquals("Original url doesn't exist, id : 1", exception.getMessage());

        // verify
        Mockito.verify(urlRepository, times(1)).findById(anyLong());
        Mockito.verify(base62Util, times(1)).decode(anyString());
    }

}