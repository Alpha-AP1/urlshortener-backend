package com.comfycoding.urlshortener.services;

import com.comfycoding.urlshortener.model.UrlMapBean;

public interface UrlShortenerService {
    String shortenUrl(UrlMapBean requestBean);
    String getOriginalUrl(String shortUrlKey);
}
