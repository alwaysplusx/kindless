package com.kindless.todo.controller;

import com.kindless.core.WebResponse;
import com.kindless.domain.todo.Todo;
import com.kindless.todo.dto.TodoList;
import com.kindless.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todos")
    public WebResponse<TodoList> list() {
        List<Todo> todos = todoService.findAll(Sort.by(Order.desc("id")));
        return WebResponse.ok(new TodoList(todos));
    }

}
