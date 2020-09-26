package com.kindless.todo.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.todo.Todo;
import com.kindless.todo.dto.TodoList;
import com.kindless.todo.dto.TodoListRequest;
import com.kindless.todo.repository.TodoRepository;
import com.kindless.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl extends ServiceSupport<Todo> implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoList findTodos(TodoListRequest request) {
        int size = request.getSize();
        if (size <= 0) {
            long total = todoRepository.count(conditionOf(request));
            return new TodoList().setTotal(total);
        }
        Sort sort = request.getSort();
        PageRequest pageRequest = PageRequest.of(0, size, sort);
        Page<Todo> todoPage = todoRepository.findAll(conditionOf(request), pageRequest);
        return new TodoList()
                .setTotal(todoPage.getTotalElements())
                .setList(todoPage.toList());
    }

    private Specification<Todo> conditionOf(TodoListRequest request) {
        Long userId = request.getUserId();
        Date deadline = request.getDeadline();
        String type = request.getType();
        return (Specification<Todo>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Path<Long> userIdPath = root.get("userId");
            predicates.add(cb.equal(userIdPath, userId));
            if (deadline != null) {
                Path<Date> deadlinePath = root.get("deadline");
                predicates.add(cb.lessThan(deadlinePath, deadline));
            }
            if (type != null) {
                Path<Boolean> donePath = root.get("done");
                switch (type) {
                    case TodoListRequest.LIST_TYPE_OF_ALL:
                        break;
                    case TodoListRequest.LIST_TYPE_OF_DONE:
                        predicates.add(cb.equal(donePath, true));
                        break;
                    case TodoListRequest.LIST_TYPE_OF_UNDONE:
                        predicates.add(cb.equal(donePath, false));
                        break;
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    protected PagingAndSortingRepository<Todo, Long> getRepository() {
        return todoRepository;
    }

}
