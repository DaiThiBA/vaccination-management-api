package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.auth.AuthenticationRequest;
import com.finalproject.vaccine_management.dto.request.auth.IntrospectRequest;
import com.finalproject.vaccine_management.dto.request.auth.LogoutRequest;
import com.finalproject.vaccine_management.dto.request.auth.RefreshRequest;
import com.finalproject.vaccine_management.dto.response.AuthenticationResponse;
import com.finalproject.vaccine_management.dto.response.IntrospectResponse;
import com.finalproject.vaccine_management.entity.InvalidatedToken;
import com.finalproject.vaccine_management.entity.User;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.repository.IUserRepository;
import com.finalproject.vaccine_management.repository.InvalidateTokenRepository;
import com.finalproject.vaccine_management.service.IAuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService implements IAuthenticationService {

    IUserRepository userRepository;

    PasswordEncoder passwordEncoder;

    InvalidateTokenRepository invalidateTokenRepository;

    RefreshTokenCacheService refreshTokenCacheService;

    @Value("${jwt.signerKey}")
    @NonFinal
    protected String SIGNER_KEY;


    @Override
    public AuthenticationResponse authenticated(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow( () ->  new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var accessToken = generatedToken(user,1, ChronoUnit.HOURS, "access");
        var refreshToken = generatedToken(user, 7 ,ChronoUnit.DAYS, "refresh");

        //Lưu refreshToken vào cache
        try {
            SignedJWT jwt = SignedJWT.parse(refreshToken);
            String jti = jwt.getJWTClaimsSet().getJWTID();
            refreshTokenCacheService.save(jti, user.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return AuthenticationResponse.builder()
                .authenticated(authenticated)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public String generatedToken(User user, long amount, ChronoUnit unit, String type){

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("daithi1026@gmail.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(amount, unit).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("type", type);

        if("access".equals(type)){
            builder.claim("scope", buildScope(user));
        }

        JWTClaimsSet claimsSet = builder.build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("cannot create token", e);
            throw new RuntimeException(e);
        }
    }


    private String buildScope(User user){
        StringJoiner joiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()) ){
            for(String role : user.getRoles()){
                joiner.add(role);
            }
        }
        else{
            return "";
        }
        return joiner.toString();
    }

    public IntrospectResponse introspect (IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        var signedToken = verifyToken(logoutRequest.getToken());

        String type = signedToken.getJWTClaimsSet()
                .getStringClaim("type");

        String jti = signedToken.getJWTClaimsSet().getJWTID();
        
        // Nếu là refresh token, remove khỏi cache
        if ("refresh".equals(type)) {
            refreshTokenCacheService.remove(jti);
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedToken = verifyToken(request.getToken());

        String type = signedToken.getJWTClaimsSet().getStringClaim("type");

        if(!"refresh".equals(type)){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String oldJti = signedToken.getJWTClaimsSet().getJWTID();

        //checkCache
        String username = refreshTokenCacheService.get(oldJti);

        if (username == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new AppException(ErrorCode.USER_NOT_FOUND));

        refreshTokenCacheService.remove(oldJti);

        String newAccessToken = generatedToken(user,1, ChronoUnit.HOURS, "access");

        String newRefreshToken = generatedToken(user, 7 ,ChronoUnit.DAYS, "refresh");

        try {
            SignedJWT jwt = SignedJWT.parse(newRefreshToken);
            String newJti = jwt.getJWTClaimsSet().getJWTID();
            refreshTokenCacheService.save(newJti, user.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return AuthenticationResponse.builder()
                .authenticated(true)
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        //verifier
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        //jwt object
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if( (!verified) || expiryTime.before(new Date()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        String tokenId = signedJWT.getJWTClaimsSet().getJWTID();

        if(invalidateTokenRepository.existsById(tokenId)){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

}
