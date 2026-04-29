package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.auth.AuthenticationRequest;
import com.finalproject.vaccine_management.dto.request.auth.IntrospectRequest;
import com.finalproject.vaccine_management.dto.request.auth.LogoutRequest;
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

        var token = generatedToken(user);

        return AuthenticationResponse.builder()
                .authenticated(authenticated)
                .token(token)
                .build();

    }



    public String generatedToken(User user){

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("daithi1026@gmail.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

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
        var signToken = verifyToken(logoutRequest.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidateTokenRepository.save(invalidatedToken);

    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        //verifier
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        //jwt object
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if( (!verified) && expiryTime.after(new Date()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        String tokenId = signedJWT.getJWTClaimsSet().getJWTID();

        if(invalidateTokenRepository.existsById(tokenId)){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }


}
