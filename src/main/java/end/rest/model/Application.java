package end.rest.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {
    final static String URL = "http://91.241.64.178:7081/api/users";
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> forEntity = template.getForEntity(URL, String.class);
        List<String> listCoocki = forEntity.getHeaders().get("Set-Cookie");
//    stream().forEach(System.out::println);
        User user = new User(3L, "James", "Brown", (byte) 33);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie",listCoocki.stream().collect(Collectors.joining(";")));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        forEntity = template.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.println(forEntity.getBody());
        user.setName("Thomas");
        user.setLastName("Shelby");
        entity = new HttpEntity<>(user, headers);
        forEntity = template.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.println(forEntity.getBody());

        forEntity = template.exchange("http://91.241.64.178:7081/api/users/3", HttpMethod.DELETE, entity, String.class);
        System.out.println(forEntity.getBody());
    }
}
