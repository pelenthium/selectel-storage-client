package example;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.SelectelClientBuilder;
import com.github.pelenthium.selectel.commands.AccountInfo;
import com.github.pelenthium.selectel.model.AccountInfoResponse;

public class AccountInfoExample {

    public static void main(String[] args) {
        String username = args[0];
        String secret = args[1];
        String bucket = args[2];

        SelectelClient client = SelectelClientBuilder.create()
                .authorize(username, secret)
                .bucket(bucket)
                .build();
        AccountInfoResponse response = client.execute(new AccountInfo());
        System.out.println(response.isSuccess());
        System.out.println(response.getContainerCount());
        System.out.println(response.getObjectCount());
        System.out.println(response.getByteUsed());
    }
}
