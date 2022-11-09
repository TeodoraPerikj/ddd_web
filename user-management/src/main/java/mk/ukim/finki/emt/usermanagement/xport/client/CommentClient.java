package mk.ukim.finki.emt.usermanagement.xport.client;

import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.TaskId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class CommentClient {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public CommentClient(@Value("${app.comment-management.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public Boolean deleteComments(UserId userId) {
        try {
            String path = "/api/deleteComments";

            URI uri = uri().path(path).queryParam("userId", userId).
                    build().toUri();

            return restTemplate.exchange(uri, HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<Boolean>() {
                    }).getBody();
        } catch (Exception e) {
            return false;
        }
    }
}
