package mk.ukim.finki.emt.taskmanagement;

import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.EstimationDays;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.User;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.UserId;
import mk.ukim.finki.emt.taskmanagement.service.TaskService;
import mk.ukim.finki.emt.taskmanagement.service.form.TaskCreateForm;
import mk.ukim.finki.emt.taskmanagement.xport.client.UserClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TaskServiceImplTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserClient userClient;

    @Test
    public void testTaskCreating() {

        TaskCreateForm taskCreateForm = new TaskCreateForm();

        taskCreateForm.setTitle("Task 1");
        taskCreateForm.setDescription("Description for Task 1");
        taskCreateForm.setStartDate("2022-09-19T07:00:00.000000");
        taskCreateForm.setDueDate("2022-09-25T07:00:00.000000");
        taskCreateForm.setEstimationDays(5);
        taskCreateForm.setCreator(UserId.randomId(UserId.class));

        taskService.create(taskCreateForm);

        Assertions.assertEquals(1, taskService.listAll().size());
    }

    @Test
    public void testPlaceOrderWithRealData() {
        List<User> userList = userClient.findAll();
        User u1 = userList.get(1);

        TaskCreateForm taskCreateForm = new TaskCreateForm();
        taskCreateForm.setTitle("Task 3");
        taskCreateForm.setDescription("Description for Task 3");
        taskCreateForm.setStartDate("2022-09-19T07:00:00.000000");
        taskCreateForm.setDueDate("2022-09-25T07:00:00.000000");
        taskCreateForm.setEstimationDays(5);
        taskCreateForm.setCreator(u1.getId());

        taskService.create(taskCreateForm);

        Assertions.assertEquals(3, taskService.listAll().size());
    }

    @Test
    public void testFindByUsername(){
        User user = userClient.findByUsername("user1");

        Assertions.assertEquals("user1", user.getUsername());
    }
}
