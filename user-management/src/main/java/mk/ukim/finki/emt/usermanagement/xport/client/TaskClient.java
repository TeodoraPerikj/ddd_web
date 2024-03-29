package mk.ukim.finki.emt.usermanagement.xport.client;

import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.TaskId;
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

    public Task findById(TaskId taskId) {
        try {
            String path = "/api/findById";

            URI uri = uri().path(path).queryParam("taskId", taskId).build().toUri();

            Task task = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Task>() {
                    }).getBody();

            return task;
        } catch (Exception e) {
            return null;
        }

    }

    public Boolean deleteTaskAssigned(TaskId taskId) {
        try {
            String path = "/api/deleteTaskAssigned";

            URI uri = uri().path(path).queryParam("taskId", taskId).
                    build().toUri();

            return restTemplate.exchange(uri, HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<Boolean>() {
                    }).getBody();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean deleteTaskOwned(TaskId taskId) {
        try {
            String path = "/api/deleteTaskOwned";

            URI uri = uri().path(path).queryParam("taskId", taskId).
                    build().toUri();

            return restTemplate.exchange(uri, HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<Boolean>() {
                    }).getBody();
        } catch (Exception e) {
            return false;
        }
    }
}