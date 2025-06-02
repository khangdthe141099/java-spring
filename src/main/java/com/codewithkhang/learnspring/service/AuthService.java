package com.codewithkhang.learnspring.service;

import com.codewithkhang.learnspring.dto.request.AuthRequest;
import com.codewithkhang.learnspring.dto.request.IntrospectRequest;
import com.codewithkhang.learnspring.dto.response.AuthResponse;
import com.codewithkhang.learnspring.dto.response.IntrospectResponse;
import com.codewithkhang.learnspring.entity.Role;
import com.codewithkhang.learnspring.entity.User;
import com.codewithkhang.learnspring.exception.AppException;
import com.codewithkhang.learnspring.exception.ErrorCode;
import com.codewithkhang.learnspring.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "wLuWgiSZsatVUbmnGh3onC+MUOUBRN8Oaha06ldln8b9k+BBXBtAttQBkDq1mIT0";

    public AuthResponse authenticate(AuthRequest authRequest) {
        var user = userRepository
                .findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = generateToken(user);
        return AuthResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("com.codewithkhang.learnspring")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toString());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
//        return user.getRoles().stream()
//                .map(Role::toUpperCase)
//                .reduce((first, second) -> first + " " + second)
//                .orElse("");
        return user.getRoles().stream()
                .map(Role::getName)
                .reduce((first, second) -> first + " " + second)
                .orElse("");
    }

    //Verify the token and return the introspection response
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            var verified = signedJWT.verify(verifier);

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            return IntrospectResponse.builder()
                    .valid(verified && expirationTime.after(new Date()))
                    .build();
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
