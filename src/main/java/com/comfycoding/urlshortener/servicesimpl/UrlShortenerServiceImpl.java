package com.comfycoding.urlshortener.servicesimpl;

import com.comfycoding.urlshortener.model.PlanType;
import com.comfycoding.urlshortener.model.ShortKeyBean;
import com.comfycoding.urlshortener.model.UrlMapBean;
import com.comfycoding.urlshortener.model.UserBean;
import com.comfycoding.urlshortener.repository.KeyPoolRepository;
import com.comfycoding.urlshortener.repository.PlanTypeRepository;
import com.comfycoding.urlshortener.repository.UrlMapRepository;
import com.comfycoding.urlshortener.repository.UserRepository;
import com.comfycoding.urlshortener.services.UrlShortenerService;
import com.comfycoding.urlshortener.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final KeyPoolRepository keyPoolRepository;
    private final UrlMapRepository urlMapRepository;
    private final UserRepository userRepository;
    private final PlanTypeRepository planTypeRepository;

    @Override
    @Transactional
    public String shortenUrl(UrlMapBean request) {
        //Validate for valid user
        UserBean userBean = userRepository.findUserById(request.getUserId());
        if(Objects.isNull(userBean)) {
            log.warn("User not valid/found: {}", request.getUserId());
            throw new IllegalStateException("User not valid/found: " + request.getUserId());
        }
        if(Objects.isNull(userBean.getPlanType())) {
            log.info("User {} cannot create more URLs due to missing plan type", userBean.getUserId());
            throw new IllegalStateException("Url creation not allowed due to missing plan info!");
        }
        //Get Plan type url limit
        PlanType planType = planTypeRepository.getPlanTypeByName(userBean.getPlanType().getName());
        userBean.setPlanType(planType);
        //Validate user url plan limit
        if(!userBean.isUrlCreationAllowed()) {
            log.info("User {} has reached the maximum URLs for their plan", userBean.getUserId());
            throw new IllegalStateException("No more url creation allowed as plan limit reached!");
        }
        //Get unique short key
        log.info("Adding shortUrl for user request : {}" + request);
        ShortKeyBean keyBean = keyPoolRepository.getOneKey();
        if(Objects.isNull(keyBean)) {
            log.error("No more unique keys remain in key pool, request failed for : {}", request);
            throw new IllegalStateException("Some error occurred!!");
        }
        log.debug("{} short key generated for user id {} for url : {}", keyBean.getShortKey(), userBean.getUserId(), request.getOriginalUrl());
        request.setShortKey(keyBean.getShortKey());
        urlMapRepository.saveUrlMap(request);
        //Increase user url count
        userRepository.increaseUrlCount(userBean.getUserId(), 1);
        log.info("Short url added for user: {}", request.getUserId());
        return keyBean.getShortKey();
    }

    @Override
    public String getOriginalUrl(String shortUrlKey) {
        //Basic validations
        if(Objects.isNull(shortUrlKey) || shortUrlKey.length() != Constants.SHORT_KEY_LENGTH) {
            log.debug("Invalid short key supplied : {}", shortUrlKey);
            throw new IllegalArgumentException("Invalid short url key!");
        }
        UrlMapBean urlMapBean = urlMapRepository.findUrlMapByShortKey(shortUrlKey);
        if(Objects.isNull(urlMapBean)) {
            log.debug("Short key not found in DB :  {}", shortUrlKey);
            throw new IllegalStateException("Short Url not found");
        }
        if(Objects.isNull(urlMapBean.getValidTo()) || urlMapBean.getValidTo().before(new Date())) {
            log.info("{} short key is expired for user id", shortUrlKey, urlMapBean.getUserId());
            throw new IllegalStateException("Short URL expired!");
        }
        //Increase the short url count
        log.info("{} short key accessed", shortUrlKey);
        urlMapRepository.increaseUrlClickCount(shortUrlKey, 1);
        //Return the original Url
        return urlMapBean.getOriginalUrl();
    }
}
