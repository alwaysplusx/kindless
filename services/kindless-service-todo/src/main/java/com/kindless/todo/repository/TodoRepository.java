package com.kindless.todo.repository;

import com.kindless.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {
}
