package com.github.pelenthium.selectel;


import org.apache.http.impl.client.CloseableHttpClient;

public class SelectelClientBuilder {

    private String username;
    private String secret;
    private String bucket;
    private boolean auth = false;
    private CloseableHttpClient httpClient;

    public static SelectelClientBuilder create() {
        return new SelectelClientBuilder();
    }

    public SelectelClientBuilder username(String username) {
        this.username = username;
        return this;
    }

    public SelectelClientBuilder secret(String secret) {
        this.secret = secret;
        return this;
    }

    public SelectelClientBuilder bucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public SelectelClientBuilder httpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public SelectelClientBuilder authorize(String username, String secret) {
        this.username = username;
        this.secret = secret;
        auth = true;
        return this;
    }

    public SelectelClient build() {
        SelectelClient selectelClient = new SelectelClient(username, secret);
        selectelClient.setBucket(bucket);
        selectelClient.setHttpClient(httpClient);
        if (auth) {
            selectelClient.authorise();
        }
        return selectelClient;
    }
}
