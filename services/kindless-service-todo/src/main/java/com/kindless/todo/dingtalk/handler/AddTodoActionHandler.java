package com.kindless.todo.dingtalk.handler;

import com.kindless.client.feign.user.UserFeignClient;
import com.kindless.core.dingtalk.DingtalkAction;
import com.kindless.core.dingtalk.DingtalkResponse;
import com.kindless.core.utils.DateFormatter;
import com.kindless.domain.todo.Todo;
import com.kindless.domain.user.User;
import com.kindless.todo.dingtalk.AbstractActionHandler;
import com.kindless.todo.service.TodoService;
import org.springframework.stereotype.Component;

@Component
public class AddTodoActionHandler extends AbstractActionHandler {

    private final TodoService todoService;

    private final UserFeignClient userClient;

    private final DateFormatter dateFormatter;

    public AddTodoActionHandler(TodoService todoService, UserFeignClient userClient, DateFormatter dateFormatter) {
        super("add");
        this.todoService = todoService;
        this.userClient = userClient;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        String title = action.getOptionValue("title", "未命名");
        String message = action.getOptionValue("message", null);
        String deadline = action.getOptionValue("deadline", null);
        User user = getActionUser(action);

        // build it
        Todo todo = new Todo();
        todo.setShortId(todoService.nextShortId(user.getId()));
        todo.setTitle(title);
        todo.setMessage(message);
        todo.setDeadline(dateFormatter.parse(deadline));
        todo.setDone(false);
        todo.setUserId(user.getId());
        Todo savedTodo = todoService.save(todo);
        return DingtalkResponse.text("#" + savedTodo.getShortId() + " todo has bean created");
    }

    @Override
    protected UserFeignClient getUserClient() {
        return userClient;
    }

}
