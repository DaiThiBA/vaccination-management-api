package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.auth.AuthenticationRequest;
import com.finalproject.vaccine_management.dto.request.auth.IntrospectRequest;
import com.finalproject.vaccine_management.dto.response.AuthenticationResponse;
import com.finalproject.vaccine_management.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticated( AuthenticationRequest request);
    public IntrospectResponse introspect (IntrospectRequest request) throws JOSEException, ParseException;
}
