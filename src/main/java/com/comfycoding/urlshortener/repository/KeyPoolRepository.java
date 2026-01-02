package com.comfycoding.urlshortener.repository;

import com.comfycoding.urlshortener.model.ShortKeyBean;

import java.util.List;

public interface KeyPoolRepository {
    void insertKey(ShortKeyBean shortKey);
    ShortKeyBean findKey(String shortKey);
    ShortKeyBean getOneKey();
    List<ShortKeyBean> getKeys(int keyCount);
    void markShortKeyAsUsed(String shortKey);
}

