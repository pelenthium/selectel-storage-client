package example;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.SelectelClientBuilder;
import com.github.pelenthium.selectel.commands.Upload;
import com.github.pelenthium.selectel.model.UploadResponse;

import java.io.File;

public class UploadExample {

    public static void main(String[] args) {
        String username = args[0];
        String secret = args[1];
        String bucket = args[2];
        String file = args[3];

        SelectelClient client = SelectelClientBuilder.create()
                .authorize(username, secret)
                .bucket(bucket)
                .build();
        UploadResponse response = client.execute(new Upload(new File(file), "/test/test1.txt"));
        System.out.println(response.isSuccess());
    }
}
