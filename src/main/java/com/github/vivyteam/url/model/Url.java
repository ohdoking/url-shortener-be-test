package com.github.vivyteam.url.model;

import lombok.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Cacheable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("url")
public class Url {
    @Id
    private long id;

    private String original;
}
