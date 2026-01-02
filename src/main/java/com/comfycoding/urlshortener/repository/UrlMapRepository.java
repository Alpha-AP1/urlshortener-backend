package com.comfycoding.urlshortener.repository;

import com.comfycoding.urlshortener.model.UrlMapBean;

public interface UrlMapRepository {
    void saveUrlMap(UrlMapBean urlMapBean);
    UrlMapBean findUrlMapByShortKey(String shortKey);
    UrlMapBean increaseUrlClickCount(String shortKey, int count);
    void removeUrlMapByShortKey(String shortKey);
}