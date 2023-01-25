package com.github.vivyteam.url.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Cacheable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(nullable = false)
    private String original;
}
