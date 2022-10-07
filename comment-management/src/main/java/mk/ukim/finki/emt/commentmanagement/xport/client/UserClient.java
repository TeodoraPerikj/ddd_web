package mk.ukim.finki.emt.commentmanagement.xport.client;

import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.TaskId;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.User;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.UserId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
public class UserClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public UserClient(@Value("${app.user-management.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public List<User> findAll() {
        try {
            List<User> x = restTemplate.exchange(uri().path("/api/user").build().toUri(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                    }).getBody();

            return x;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public User findByUsername(String username) {
        try {
            String path = "/api/findByUsername";

            URI uri = uri().path(path).queryParam("username", username).build().toUri();

            User user = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<User>() {}).getBody();

            return user;
        } catch (Exception e) {
            return null;
        }

    }

    public User findById(UserId userId) {
        try {
            String path = "/api/findById";

            URI uri = uri().path(path).queryParam("userId", userId).build().toUri();

            User user = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<User>() {}).getBody();

            return user;
        } catch (Exception e) {
            return null;
        }

    }
}
