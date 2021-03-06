package com.github.pelenthium.selectel

import com.github.pelenthium.selectel.commands.ContainerList
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.message.BasicHttpResponse
import org.apache.http.message.BasicStatusLine

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class SelectelAutorizeTest extends SelectelBaseTest {

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
        def now = LocalDateTime.now().withNano(0)

        def http = Stub(CloseableHttpClient)
        def response = new BasicHttpResponse(new BasicStatusLine(version, 204, "OK"))
        response.setHeader(SelectelContants.X_EXPIRE_AUTH_TOKEN, 100L.toString())
        response.setHeader(SelectelContants.X_AUTH_TOKEN, "test-auth-token")
        response.setHeader(SelectelContants.X_STORAGE_TOKEN, "test-storage-token")
        response.setHeader(SelectelContants.X_STORAGE_URL, "http://test.selectel.ru/storage/url")
        response.setHeader(SelectelContants.DATE, formatter.format(date(now)))
        http.execute(_) >> wrap(response)
        http

        when:
        def client = SelectelClientBuilder.create()
                .httpClient(http)
                .authorize("good user", "good pass").build()
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

    def "Test calling authorize() with expired token"() {
        setup:
        def now = LocalDateTime.now().withNano(0).minus(100, ChronoUnit.SECONDS)

        def http = Stub(CloseableHttpClient)
        def response = new BasicHttpResponse(new BasicStatusLine(version, 204, "OK"))
        response.setHeader(SelectelContants.X_EXPIRE_AUTH_TOKEN, 0L.toString())
        response.setHeader(SelectelContants.X_AUTH_TOKEN, "test-auth-token")
        response.setHeader(SelectelContants.X_STORAGE_TOKEN, "test-storage-token")
        response.setHeader(SelectelContants.X_STORAGE_URL, "http://test.selectel.ru/storage/url")
        response.setHeader(SelectelContants.DATE, formatter.format(date(now)))
        http.execute(_) >> wrap(response)
        http
        def client = SelectelClientBuilder.create()
                .httpClient(http)
                .authorize("good user", "good pass").build()

        when:
        client.authorise()
        then:
        thrown(IllegalStateException)
    }
}
