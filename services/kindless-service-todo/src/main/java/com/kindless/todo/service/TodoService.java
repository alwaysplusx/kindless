package com.kindless.todo.service;

import com.kindless.core.service.Service;
import com.kindless.domain.todo.Todo;
import com.kindless.todo.dto.TodoList;
import com.kindless.todo.dto.TodoListRequest;

public interface TodoService extends Service<Todo> {

    TodoList findTodos(TodoListRequest request);

    long nextShortId(Long userId);

}
