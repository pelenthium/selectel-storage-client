package com.github.pelenthium.selectel.commands;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.SelectelContants;
import com.github.pelenthium.selectel.model.AccountInfoResponse;
import com.github.pelenthium.selectel.model.AuthResponse;
import com.github.pelenthium.selectel.utils.IntString;
import com.github.pelenthium.selectel.utils.LongString;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpRequestBase;

public class AccountInfo implements SelectelCommand<AccountInfoResponse> {

    @Override
    public HttpRequestBase buildRequest(SelectelClient client) {
        AuthResponse response = client.authorise();
        HttpHead get = new HttpHead(response.getStorageUrl());
        get.addHeader(SelectelContants.X_AUTH_TOKEN, response.getAuthToken());
        return get;
    }

    @Override
    public AccountInfoResponse parseResponse(CloseableHttpResponse response) {
        AccountInfoResponse.AccountInfoResponseBuilder builder = AccountInfoResponse.builder();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
            builder.success(true)
                    .objectCount(new IntString(response.getFirstHeader(SelectelContants.X_ACCOUNT_OBJECT_COUNT).getValue()).toInt())
                    .containerCount(new IntString(response.getFirstHeader(SelectelContants.X_ACCOUNT_CONTAINER_COUNT).getValue()).toInt())
                    .byteUsed(new LongString(response.getFirstHeader(SelectelContants.X_ACCOUNT_BYTES_USED).getValue()).toLong());
        }
        return builder.build();
    }
}
