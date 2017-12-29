package com.github.pelenthium.selectel

import com.github.pelenthium.selectel.commands.AccountInfo
import org.apache.http.message.BasicHttpResponse
import org.apache.http.message.BasicStatusLine
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AccountInfoTest extends SelectelBaseTest {

    private static Logger logger = LoggerFactory.getLogger(AccountInfoTest)

    def "Load account info"() {
        setup:

        def client = buildBaseClient()

        def response = new BasicHttpResponse(new BasicStatusLine(version, 204, "OK"))
        response.setHeader(SelectelContants.X_ACCOUNT_BYTES_USED, 12345.toString())
        response.setHeader(SelectelContants.X_ACCOUNT_CONTAINER_COUNT, 2.toString())
        response.setHeader(SelectelContants.X_ACCOUNT_OBJECT_COUNT, 42.toString())
        client.getHttpClient().execute(_) >> wrap(response)

        when:
        def clientInfo = client.execute(new AccountInfo())
        then:
        client != null
        clientInfo != null
        clientInfo.success
        clientInfo.getByteUsed() == 12345
        clientInfo.getContainerCount() == 2
        clientInfo.getObjectCount() == 42
    }

    def "failed load data" () {
        setup:

        def client = buildBaseClient()

        def response = new BasicHttpResponse(new BasicStatusLine(version, 401, "No"))
        client.getHttpClient().execute(_) >> wrap(response)

        when:
        def clientInfo = client.execute(new AccountInfo())
        then:
        client != null
        clientInfo != null
        !clientInfo.success
    }
}
