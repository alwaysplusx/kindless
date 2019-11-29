package com.kindless.moment.controller;

import com.kindless.moment.client.MomentProviderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuxin
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/provider/moment")
public class GraphQLProviderController implements MomentProviderClient {

    // private final GraphQL graphQL;

    @Override
    public <T> T getOne(String graphQLString, String resultType) {
        // ExecutionResult result = graphQL.execute(graphQLString);
        // return result.getData();
        return null;
    }

    @Override
    public <T> List<T> getAll(String graphQLString, String resultType) {
        return null;
    }

    @Override
    public <T> Page<T> getPage(String graphQLString, String resultType) {
        return null;
    }

}
