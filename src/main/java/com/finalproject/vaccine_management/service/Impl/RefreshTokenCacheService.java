package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.service.IRefreshTokenCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenCacheService implements IRefreshTokenCacheService {

    @CachePut(value = "refreshTokens", key = "#jti")
    public String save(String jti, String username) {
        return username;
    }

    @Cacheable(value = "refreshTokens", key = "#jti")
    public String get(String jti) {
        return null;
    }

    @CacheEvict(value = "refreshTokens", key = "#jti")
    public void remove(String jti) {
    }
}
