package com.github.pelenthium.selectel.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.SelectelContants;
import com.github.pelenthium.selectel.model.AuthResponse;
import com.github.pelenthium.selectel.model.UploadResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

public class Upload implements SelectelCommand<UploadResponse> {

    private File file;
    private String path;
    private InputStream stream;
    private byte[] bytes;

    private String absolutePath;

    public Upload(File file, String path) {
        this.file = file;
        this.path = path;
    }

    public Upload(InputStream stream, String path) {
        this.stream = stream;
        this.path = path;
    }

    public Upload(byte[] bytes, String path) {
        this.bytes = bytes;
        this.path = path;
    }

    @Override
    public HttpRequestBase buildRequest(SelectelClient client) {
        AuthResponse authResponse = client.authorise();
        absolutePath = String.join("/", authResponse.getStorageUrl(), client.getBucket(), path);
        HttpPut put = new HttpPut(absolutePath);
        put.addHeader(SelectelContants.X_AUTH_TOKEN, authResponse.getAuthToken());
        EntityBuilder builder = EntityBuilder.create();
        if (file != null) {
            builder.setFile(file);
        }
        if (stream != null) {
            builder.setStream(stream);
        }
        if (bytes != null) {
            builder.setBinary(bytes);
        }
        put.setEntity(builder.build());
        return put;
    }

    @Override
    public UploadResponse parseResponse(CloseableHttpResponse response) {
        return UploadResponse.builder()
                .path(absolutePath)
                .success(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED)
                .build();
    }
}
