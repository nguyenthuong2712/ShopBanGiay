package org.example.webbangiay.controller;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.LogoutRequest;
import org.example.webbangiay.dto.response.AuthenticationResponse;
import org.example.webbangiay.dto.response.IntrospectResponse;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.request.AuthenticationRequest;
import org.example.webbangiay.dto.request.IntrospectRequest;
import org.example.webbangiay.service.authenticaton_service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/au-th")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> authenticate(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
         authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .code(1000)
                .build();
    }

}