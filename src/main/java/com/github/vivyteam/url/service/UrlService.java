package com.github.vivyteam.url.service;

import com.github.vivyteam.url.model.Url;
import com.github.vivyteam.url.repository.UrlRepository;
import com.github.vivyteam.url.util.Base62Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final Base62Util base62Util;
    private final UrlRepository urlRepository;

    public String generateShortenUrl(URI uri, String originalUrl) {
        Optional<Url> urlOptional = urlRepository.findUrlByOriginal(originalUrl);
        Url url = urlOptional.orElseGet(() -> urlRepository.save(Url.builder().original(originalUrl).build()));

        String encodedId = base62Util.encode(url.getId());
        return buildShortenUrl(uri, encodedId);
    }

    private String buildShortenUrl(URI uri, String encodedId) {
        return String.format("%s://%s:%s/%s", uri.getScheme(), uri.getHost(), uri.getPort(), encodedId);
    }

    public String getOriginalUrl(String shortenUrl) {
        long id = base62Util.decode(shortenUrl);
        Optional<Url> optionalUrl = urlRepository.findById(id);
        if (optionalUrl.isEmpty()) {
            throw new NoResultException("Original url doesn't exist, id : " + id);
        }
        return optionalUrl.get().getOriginal();
    }

}
