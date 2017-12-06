package com.github.pelenthium.selectel

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.message.BasicHttpResponse
import org.apache.http.params.HttpParams

class CloseableHttpResponseMock implements CloseableHttpResponse {
    @Delegate
    BasicHttpResponse response
    HttpParams params

    @Override
    void close() throws IOException {

    }
}
