package com.github.pelenthium.selectel.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.SelectelContants;
import com.github.pelenthium.selectel.model.AuthResponse;
import com.github.pelenthium.selectel.model.ContainerListResponse;
import com.github.pelenthium.selectel.model.ContainerResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerList implements SelectelCommand<ContainerListResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerList.class);

    @Override
    public HttpRequestBase buildRequest(SelectelClient client) {
        AuthResponse response = client.authorise();
        HttpGet get = new HttpGet(response.getStorageUrl() + "?format=json");
        get.addHeader(SelectelContants.X_AUTH_TOKEN, response.getAuthToken());
        return get;
    }

    @Override
    public ContainerListResponse parseResponse(CloseableHttpResponse response) {
        List<ContainerResponse> containers = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(new BufferedInputStream(response.getEntity().getContent()))) {
            containers = new Gson().fromJson(reader, new TypeToken<List<ContainerResponse>>(){}.getType());
        } catch (IOException e) {
            LOGGER.error("Failed parse response for GET container info request", e);
        }
        return ContainerListResponse.builder()
                .success(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                .containers(containers)
                .build();
    }
}
