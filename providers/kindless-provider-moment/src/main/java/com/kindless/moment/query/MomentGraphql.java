package com.kindless.moment.query;

import com.kindless.moment.domain.Moment;
import com.kindless.moment.domain.MomentResource;
import com.kindless.moment.graphql.annotation.GraphqlImport;
import com.kindless.moment.graphql.annotation.GraphqlQuery;
import com.kindless.moment.graphql.annotation.condition.Equal;

import java.util.List;

@GraphqlQuery
@GraphqlImport(MomentResource.class)
public interface MomentGraphql {

    Moment moment(@Equal("id") String id);

    List<Moment> moments();

    MomentResource momentResource(@Equal("id") String id);

}
