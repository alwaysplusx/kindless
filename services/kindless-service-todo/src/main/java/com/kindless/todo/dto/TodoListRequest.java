package com.kindless.todo.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TodoListRequest {

    public static final String LIST_TYPE_OF_DONE = "done";
    public static final String LIST_TYPE_OF_ALL = "all";
    public static final String LIST_TYPE_OF_UNDONE = "undone";

    private Long userId;

    private int size = 5;

    private Sort sort = Sort.unsorted();

    private Date deadline;

    private String type = LIST_TYPE_OF_UNDONE;

}
