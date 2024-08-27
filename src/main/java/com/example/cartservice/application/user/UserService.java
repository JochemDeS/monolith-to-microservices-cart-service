package com.example.cartservice.application.user;

import com.example.cartservice.domain.UserId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements GetUser {
    @Value("${microservices.user.url}")
    private String userServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<UserId> byToken(String token) {
        final var headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList(String.format("Bearer %s", token)));
        final var entity = new HttpEntity<>(headers);

        final var response = restTemplate.exchange(
                String.format("%s/auth/me", userServiceUrl),
                HttpMethod.GET,
                entity,
                UserResponse.class);

        return Optional.ofNullable(response.getBody())
                .map(UserResponse::id)
                .map(id -> UserId.builder()
                        .value(id.longValue())
                        .build());
    }
}
