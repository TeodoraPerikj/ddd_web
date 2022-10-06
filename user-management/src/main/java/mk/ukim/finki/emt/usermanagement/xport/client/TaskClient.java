package mk.ukim.finki.emt.usermanagement.xport.client;

import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class TaskClient {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public TaskClient(@Value("${app.task-management.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public List<Task> findAll() {
        try {
            List<Task> x = restTemplate.exchange(uri().path("/api/tasks").build().toUri(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Task>>() {
                    }).getBody();

            return x;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
