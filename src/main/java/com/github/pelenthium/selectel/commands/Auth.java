package com.github.pelenthium.selectel.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.SelectelContants;
import com.github.pelenthium.selectel.model.AuthResponse;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

@AllArgsConstructor
public class Auth implements SelectelCommand<AuthResponse> {

    private String username;
    private String secret;

    @Override
    public HttpRequestBase buildRequest(SelectelClient client) {
        HttpGet httpGet = new HttpGet(SelectelContants.AUTH_URL);
        httpGet.addHeader(SelectelContants.X_AUTH_USER, username);
        httpGet.addHeader(SelectelContants.X_AUTH_KEY, secret);
        return httpGet;
    }

    @Override
    public AuthResponse parseResponse(CloseableHttpResponse response) {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {
            return AuthResponse.builder().build();
        }
        String expire = response.getFirstHeader(SelectelContants.X_EXPIRE_AUTH_TOKEN).getValue();
        Long expireTime = 0L;
        if (!expire.isEmpty()) {
            try {
                expireTime = Long.valueOf(expire);
            } catch (NumberFormatException e) {}
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SelectelContants.DATE_FORMAT);
        return AuthResponse.builder()
                .success(true)
                .authToken(response.getFirstHeader(SelectelContants.X_AUTH_TOKEN).getValue())
                .storageToken(response.getFirstHeader(SelectelContants.X_STORAGE_TOKEN).getValue())
                .storageUrl(response.getFirstHeader(SelectelContants.X_STORAGE_URL).getValue())
                .date(LocalDateTime.parse(response.getFirstHeader(SelectelContants.DATE).getValue(), formatter))
                .expireAuthToken(expireTime)
                .build();
    }
}
