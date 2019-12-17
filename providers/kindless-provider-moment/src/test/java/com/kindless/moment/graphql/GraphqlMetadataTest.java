package com.kindless.moment.graphql;

import com.kindless.moment.domain.Moment;
import com.kindless.moment.query.MomentGraphql;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author wuxin
 */
@Slf4j
public class GraphqlMetadataTest {

    @Test
    public void test() {
        GraphqlMetadata metadata = GraphqlMetadata.of(MomentGraphql.class);
        log.info("graphql query metadata: {}", metadata);

        GraphqlMetadata metadata1 = GraphqlMetadata.of(Moment.class);
        log.info("graphql type metadata: {}", metadata1);
    }

}
