package com.kindless.moment.graphql.model;

import com.kindless.moment.domain.Moment;
import com.kindless.moment.graphql.annotation.GraphqlQuery;
import com.kindless.moment.graphql.annotation.condition.Equal;
import lombok.Data;

import java.util.List;

/**
 * @author wuxin
 */
@Data
@GraphqlQuery("moment")
public class MomentGraphqlQuery {

    @Equal
    String id;

    String userId;

    String content;

    Integer type;

    @GraphqlQuery
    public interface MomentGraph {

        Moment moment(@Equal("id") String id);

        List<Moment> moments();

    }

}
