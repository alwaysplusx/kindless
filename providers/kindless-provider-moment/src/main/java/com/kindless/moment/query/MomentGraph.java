package com.kindless.moment.query;

import com.kindless.moment.domain.Moment;
import com.kindless.moment.graphql.annotation.GraphqlQuery;
import com.kindless.moment.graphql.annotation.condition.Equal;

import java.util.List;

@GraphqlQuery
public interface MomentGraph {

    Moment moment(@Equal("id") String id);

    List<Moment> moments();

}