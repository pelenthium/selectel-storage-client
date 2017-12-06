package example;

import com.github.pelenthium.selectel.SelectelClient;
import com.github.pelenthium.selectel.SelectelClientBuilder;
import com.github.pelenthium.selectel.commands.ContainerList;
import com.github.pelenthium.selectel.model.ContainerListResponse;

public class Example {
    public static void main(String[] args) {
        String username = args[0];
        String secret = args[1];

        SelectelClient build = SelectelClientBuilder.create()
                .authorize(username, secret)
                .build();
        ContainerListResponse list = build.execute(new ContainerList());
        System.out.println(list.isSuccess());
        list.getContainers().forEach((container) -> {
            System.out.print(container.getName());
            System.out.print(" => ");
            System.out.print(container.getCount());
            System.out.print(" => ");
            System.out.println(container.getBytes() / (1024 * 1024) + "Mb");
        });
    }
}
