package com.comfycoding.urlshortener.controller;

import com.comfycoding.urlshortener.model.UrlMapBean;
import com.comfycoding.urlshortener.dto.UrlShortenerRequestBean;
import com.comfycoding.urlshortener.mapper.common.UrlMapper;
import com.comfycoding.urlshortener.services.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping("shorten")
    public ResponseEntity<String> shortenUrl(@Valid @RequestBody UrlShortenerRequestBean urlShortenerRequestBean) {
        UrlMapBean urlMapBean = UrlMapper.mapUrlShortenerRequestBeanToUrlMapBean(urlShortenerRequestBean);
        String shortUrl = this.urlShortenerService.shortenUrl(urlMapBean);
        return ResponseEntity.ok().body(shortUrl);

    }

    @GetMapping("/{shortUrlKey}")
    public ResponseEntity<String> redirectToActualUrl(@PathVariable String shortUrlKey) {
        String actualUrl = this.urlShortenerService.getOriginalUrl(shortUrlKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", actualUrl)
                .build();
    }
}
