package com.github.vivyteam.url.api.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OriginalUrlResponse {
    private String url;
}
