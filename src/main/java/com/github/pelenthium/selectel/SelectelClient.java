package com.github.pelenthium.selectel;

import com.github.pelenthium.selectel.commands.Auth;
import com.github.pelenthium.selectel.commands.SelectelCommand;
import com.github.pelenthium.selectel.model.AuthResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Client for execute Selectel Command via REST API
 * You can create SelectelClient by SelectelClientBuilder
 */
public class SelectelClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectelClient.class);

    @Setter
    private CloseableHttpClient httpClient;
    @Getter
    @Setter
    private String bucket;

    private AuthResponse authResponse;
    private String username;
    private String secret;


    public SelectelClient(String username, String secret) {
        this.username = username;
        this.secret = secret;
    }

    public AuthResponse authorise() {
        if (!this.checkToken()) {
            if (username == null || secret == null) {
                throw new IllegalStateException("You need to set username/secret to SelectelClient");
            }
            authResponse = execute(new Auth(username, secret));
            if (!authResponse.isSuccess()) {
                throw new IllegalArgumentException("Username or password isn't correct, authorization failed");
            }
        }
        return authResponse;
    }

    public <R> R execute(SelectelCommand<R> command) {
        try (CloseableHttpResponse response = getHttpClient().execute(command.buildRequest(this))) {
            return command.parseResponse(response);
        } catch (IOException e) {
            LOGGER.warn("Failed http request {}", e.getMessage());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return null;
    }

    public CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClientBuilder.create().build();
        }
        return httpClient;
    }

    private boolean checkToken() {
        if (authResponse == null) {
            return false;
        } else {
            LocalDateTime expireDate = authResponse.getDate().plus(authResponse.getExpireAuthToken(), ChronoUnit.SECONDS);
            if (expireDate.compareTo(LocalDateTime.now()) < 0) {
                LOGGER.warn("Token is expired");
                return false;
            }
            return true;
        }
    }
}
