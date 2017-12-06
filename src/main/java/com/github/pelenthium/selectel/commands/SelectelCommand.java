package com.github.pelenthium.selectel.commands;

import com.github.pelenthium.selectel.SelectelClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public interface SelectelCommand<R> {
    HttpRequestBase buildRequest(SelectelClient client);
    R parseResponse(CloseableHttpResponse response);
}
