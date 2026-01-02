package com.comfycoding.urlshortener.mapper.common;

import com.comfycoding.urlshortener.model.UrlMapBean;
import com.comfycoding.urlshortener.dto.UrlShortenerRequestBean;

public class UrlMapper {
    public static UrlMapBean mapUrlShortenerRequestBeanToUrlMapBean(UrlShortenerRequestBean requestBean) {
        UrlMapBean urlMapBean = new UrlMapBean();
        urlMapBean.setUserId(requestBean.getUserId());
        urlMapBean.setActualUrl(requestBean.getActualUrl());
        urlMapBean.setValidTo(requestBean.getValidTo());
        return urlMapBean;
    }
}
