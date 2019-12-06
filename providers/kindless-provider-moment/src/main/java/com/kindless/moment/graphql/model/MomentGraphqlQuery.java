package com.kindless.moment.graphql.model;

import com.kindless.moment.graphql.annotation.GraphqlQuery;
import com.kindless.moment.graphql.annotation.condition.Equal;
import lombok.Data;

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

}
