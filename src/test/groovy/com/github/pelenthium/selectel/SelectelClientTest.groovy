package com.github.pelenthium.selectel

import com.github.pelenthium.selectel.commands.Auth
import com.github.pelenthium.selectel.commands.ContainerList
import com.github.pelenthium.selectel.commands.Upload
import com.github.pelenthium.selectel.model.UploadResponse
import org.apache.http.ProtocolVersion
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.message.BasicHttpResponse
import org.apache.http.message.BasicStatusLine
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SelectelClientTest extends Specification {

    ProtocolVersion version = new ProtocolVersion("HTTP", 1, 1)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SelectelContants.DATE_FORMAT)

    private CloseableHttpResponse wrap(final BasicHttpResponse original) {
        return new CloseableHttpResponseMock(response: original)
    }

    private def date(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("Europe/Moscow"))
    }

    def "Test authorise without username/password"() {
        setup:
        def client = SelectelClientBuilder.create().build()
        when:
        client.execute(new ContainerList())
        then:
        thrown(IllegalStateException)
    }

    def "Test authorise with bad username/password"() {
        when:
        def http = Stub(CloseableHttpClient)
        def response = new BasicHttpResponse(new BasicStatusLine(version, 403, "В авторизации отказано"))
        http.execute(_) >> wrap(response)
        SelectelClientBuilder.create()
                .httpClient(http)
                .authorize("bad user", "bad pass").build()
        then:
        thrown(IllegalArgumentException)
    }

    def "Test authorise with good username/password"() {
        setup:
        def http = Stub(CloseableHttpClient)
        def response = new BasicHttpResponse(new BasicStatusLine(version, 204, "OK"))
        response.setHeader(SelectelContants.X_EXPIRE_AUTH_TOKEN, 100L.toString())
        response.setHeader(SelectelContants.X_AUTH_TOKEN, "test-auth-token")
        response.setHeader(SelectelContants.X_STORAGE_TOKEN, "test-storage-token")
        response.setHeader(SelectelContants.X_STORAGE_URL, "http://test.selectel.ru/storage/url")
        def now = LocalDateTime.now().withNano(0)
        response.setHeader(SelectelContants.DATE, formatter.format(date(now)))
        http.execute(_) >> wrap(response)

        when:
        def client = SelectelClientBuilder.create()
                .httpClient(http)
                .authorize("bad user", "bad pass").build()
        then:
        client != null
        client.authorise() != null
        client.authorise().isSuccess()
        client.authorise().authToken == "test-auth-token"
        client.authorise().storageToken == "test-storage-token"
        client.authorise().storageUrl == "http://test.selectel.ru/storage/url"
        client.authorise().date == now
        client.authorise().expireAuthToken == 100L
    }
}
