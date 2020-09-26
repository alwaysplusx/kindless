package com.kindless.todo.dingtalk.handler;

import com.kindless.client.feign.user.UserFeignClient;
import com.kindless.core.dingtalk.DingtalkAction;
import com.kindless.core.dingtalk.DingtalkResponse;
import com.kindless.domain.todo.Todo;
import com.kindless.domain.user.User;
import com.kindless.todo.dingtalk.AbstractActionHandler;
import com.kindless.todo.service.TodoService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AddTodoActionHandler extends AbstractActionHandler {

    private final TodoService todoService;

    private final UserFeignClient userClient;

    public AddTodoActionHandler(TodoService todoService, UserFeignClient userClient) {
        super("add");
        this.todoService = todoService;
        this.userClient = userClient;
    }

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        String title = action.getOptionValue("title", "未命名");
        String message = action.getOptionValue("message", null);
        String deadline = action.getOptionValue("deadline", null);
        User user = getActionUser(action);

        // build it
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setMessage(message);
        todo.setDeadline(parseDate(deadline));
        todo.setDone(false);
        todo.setUserId(user.getId());
        Todo savedTodo = todoService.save(todo);
        return DingtalkResponse.text("todo has bean created, id=" + savedTodo.getId());
    }

    @Override
    protected UserFeignClient getUserClient() {
        return userClient;
    }

    private Date parseDate(String date) {
        // SimpleDateFormat sdf = new SimpleDateFormat();
        return null;
    }

}
