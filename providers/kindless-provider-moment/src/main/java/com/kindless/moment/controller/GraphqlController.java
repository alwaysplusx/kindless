package com.kindless.moment.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxin
 */
@RestController
public class GraphqlController {

    private final GraphQL graphql;

    public GraphqlController(GraphQL graphql) {
        this.graphql = graphql;
    }

    @GetMapping("/graphql")
    public Object query(@RequestParam("query") String query) {
        ExecutionResult result = graphql.execute(query);
        if (result.getErrors().isEmpty()) {
            return result.getData();
        }
        return "failed";
    }

}
