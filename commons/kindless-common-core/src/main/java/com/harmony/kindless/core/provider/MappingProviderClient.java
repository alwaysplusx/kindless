package com.harmony.kindless.core.provider;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wuxin
 */
public interface MappingProviderClient extends ProviderClient {

    @Override
    default <T> T getOne(String graphQLString, Class<T> resultType) {
        return getOne(graphQLString, resultType.getName());
    }

    @Override
    default <T> List<T> getAll(String graphQLString, Class<T> resultType) {
        return getAll(graphQLString, resultType.getName());
    }

    @Override
    default <T> Page<T> getPage(String graphQLString, Class<T> resultType) {
        return getPage(graphQLString, resultType.getName());
    }


    @PostMapping("/one")
    <T> T getOne(@RequestBody String graphQLString,
                 @RequestParam(required = false, name = "resultType") String resultType);

    @PostMapping("/all")
    <T> List<T> getAll(@RequestBody String graphQLString,
                       @RequestParam(required = false, name = "resultType") String resultType);

    @PostMapping("/page")
    <T> Page<T> getPage(@RequestBody String graphQLString,
                        @RequestParam(required = false, name = "resultType") String resultType);


}
