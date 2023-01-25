package com.github.vivyteam.url.repository;

import com.github.vivyteam.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findUrlByOriginal(String original);
}
