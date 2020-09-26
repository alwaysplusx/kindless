package com.kindless.todo.dto;

import com.kindless.domain.todo.Todo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Accessors(chain = true)
@Data
public class TodoList implements Iterable<Todo> {

    private long total;
    private List<Todo> list = Collections.emptyList();

    public TodoList() {
    }

    public TodoList(List<Todo> list) {
        this.list = list;
    }

    @Override
    public Iterator<Todo> iterator() {
        return list.iterator();
    }

}

