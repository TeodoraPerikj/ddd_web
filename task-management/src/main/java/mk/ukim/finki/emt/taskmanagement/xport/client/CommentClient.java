package mk.ukim.finki.emt.taskmanagement.xport.client;

import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.Comment;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.CommentId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.User;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.UserId;
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

    public Comment findById(CommentId commentId) {
        try {
            String path = "/api/comments/findById";

            URI uri = uri().path(path).queryParam("commentId", commentId).build().toUri();

            Comment comment = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Comment>() {}).getBody();

            return comment;
        } catch (Exception e) {
            return null;
        }

    }

    public Boolean deleteComment(CommentId commentId) {
        try {
            String path = "/api/comments/delete";

            //TODO: Make path variable

            URI uri = uri().path(path).queryParam("commentId", commentId).build().toUri();

            return restTemplate.exchange(uri, HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<Boolean>() {}).getBody();
        } catch (Exception e) {
            return false;
        }
    }

//    public Boolean setTaskAssigned(TaskId taskId, UserId userId) {
//        try {
//            String path = "/api/setTaskAssigned";
//
//            URI uri = uri().path(path).queryParam("taskId", taskId).queryParam("userId", userId).
//                    build().toUri();
//
//            return restTemplate.exchange(uri, HttpMethod.POST, null,
//                    new ParameterizedTypeReference<Boolean>() {}).getBody();
//        } catch (Exception e) {
//            return false;
//        }
//
//    }
//
//    public Boolean setTaskOwned(TaskId taskId, UserId userId) {
//        try {
//            String path = "/api/setTaskOwned";
//
//            URI uri = uri().path(path).queryParam("taskId", taskId).queryParam("userId", userId).
//                    build().toUri();
//
//            return restTemplate.exchange(uri, HttpMethod.POST, null,
//                    new ParameterizedTypeReference<Boolean>() {}).getBody();
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
