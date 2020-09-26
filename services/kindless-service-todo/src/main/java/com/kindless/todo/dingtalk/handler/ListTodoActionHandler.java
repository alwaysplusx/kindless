package com.kindless.todo.dingtalk.handler;

import com.kindless.client.feign.user.UserFeignClient;
import com.kindless.core.dingtalk.DingtalkAction;
import com.kindless.core.dingtalk.DingtalkActionHandler;
import com.kindless.core.dingtalk.DingtalkResponse;
import com.kindless.core.utils.DateFormatter;
import com.kindless.domain.todo.Todo;
import com.kindless.domain.user.User;
import com.kindless.todo.dingtalk.AbstractActionHandler;
import com.kindless.todo.dto.TodoList;
import com.kindless.todo.dto.TodoListRequest;
import com.kindless.todo.service.TodoService;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class ListTodoActionHandler extends AbstractActionHandler implements DingtalkActionHandler {

    private final TodoService todoService;

    private final UserFeignClient userClient;

    private final DateFormatter dateFormatter;

    public ListTodoActionHandler(TodoService todoService, UserFeignClient userClient, DateFormatter dateFormatter) {
        super("list");
        this.todoService = todoService;
        this.userClient = userClient;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        User user = getActionUser(action);
        String size = action.getOptionValue("size", "5");
        String type = action.getOptionValue("type", TodoListRequest.LIST_TYPE_OF_UNDONE);
        String deadline = action.getOptionValue("deadline", null);
        TodoListRequest request = new TodoListRequest()
                .setSize(Integer.parseInt(size))
                .setSort(Sort.by(Order.desc("id")))
                .setUserId(user.getId())
                .setType(type)
                .setDeadline(dateFormatter.parse(deadline));
        TodoList todos = todoService.findTodos(request);
        return DingtalkResponse.text(toTodoListText(todos));
    }

    private String toTodoListText(TodoList todos) {
        StringBuilder o = new StringBuilder();
        o.append("TODO List:\n");
        Iterator<Todo> it = todos.iterator();
        for (; it.hasNext(); ) {
            Todo todo = it.next();
            o.append("\t#").append(todo.getShortId()).append(" ").append(todo.getTitle());
            if (it.hasNext()) {
                o.append("\n");
            }
        }
        return o.toString();
    }

    @Override
    protected UserFeignClient getUserClient() {
        return userClient;
    }

}
