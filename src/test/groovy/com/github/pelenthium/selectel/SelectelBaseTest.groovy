package com.github.pelenthium.selectel

import com.github.pelenthium.selectel.model.AuthResponse
import org.apache.http.ProtocolVersion
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.message.BasicHttpResponse
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

abstract class SelectelBaseTest extends Specification {
    ProtocolVersion version = new ProtocolVersion("HTTP", 1, 1)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SelectelContants.DATE_FORMAT)

    CloseableHttpResponse wrap(final BasicHttpResponse original) {
        return new CloseableHttpResponseMock(response: original)
    }

    def date(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("Europe/Moscow"))
    }

    def buildBaseClient(LocalDateTime date = null, long expired = 10000L) {
        def client = Spy(SelectelClient, constructorArgs: ['ok', 'ok']) {
            authorise() >> new AuthResponse(true, "token", "stoken", "test", date?:LocalDateTime.now(), expired)
        }
        def http = Stub(CloseableHttpClient)
        client.setHttpClient(http)
        client
    }
}
