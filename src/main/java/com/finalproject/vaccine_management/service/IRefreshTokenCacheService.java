package com.finalproject.vaccine_management.service;

public interface IRefreshTokenCacheService {
    String save(String jti, String username);
    String get(String jti);
    void remove(String jti);

}
