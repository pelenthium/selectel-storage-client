package com.github.pelenthium.selectel.commands;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.model.AuthResponse;
import com.github.pelenthium.selectel.model.UploadResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public class Upload implements SelectelCommand<UploadResponse> {


    @Override
    public HttpRequestBase buildRequest(SelectelClient client) {
        AuthResponse authResponse = client.authorise();
        String.join("/", authResponse.getStorageUrl());
        return null;
    }

    @Override
    public UploadResponse parseResponse(CloseableHttpResponse response) {
        return null;
    }
}
