package com.github.vivyteam.url.api.request;

import com.github.vivyteam.url.validation.UrlConstraint;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedUrlRequest {
    @UrlConstraint
    private String url;
}
