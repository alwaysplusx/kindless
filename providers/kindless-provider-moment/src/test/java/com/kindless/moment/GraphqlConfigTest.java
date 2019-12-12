package com.kindless.moment;

import com.kindless.moment.domain.Moment;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.language.*;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraphqlConfigTest {

    private GraphQLSchema graphQLSchema() {
        return GraphQLSchema
                .newSchema()
                .query(
                        GraphQLObjectType
                                .newObject()
                                .name("Query")
                                .field(momentQueryType())
                                .description("Query查询Schema")
                                .build()
                )
                .build();
    }

    private GraphQLFieldDefinition momentQueryType() {
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name("moment")
                .description("通过Id查找Moment")
                .type(momentType())
                .argument(idArgument())
                .dataFetcher(environment -> {
                    log.info("fetch moment: {}", environment);
                    return Moment.builder().userId(1L).content("this is content").build();
                })
                .build();
    }

    private GraphQLArgument idArgument() {
        return GraphQLArgument
                .newArgument()
                .name("id")
                .description("主键")
                .type(Scalars.GraphQLBigInteger)
                // TODO 将注解转化为参数的查询条件定义, 只有就可以进行自动组装查询条件？
                .definition(idParameterDefinition())
                .build();
    }

    private GraphQLObjectType momentType() {
        return GraphQLObjectType
                .newObject()
                .name("Moment")
                .field(idFieldDefinition())
                .field(userIdFieldDefinition())
                .field(contentFieldDefinition())
                .description("用户发布的Moment数据模型")
                .build();
    }

    private GraphQLFieldDefinition idFieldDefinition() {
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name("id")
                .type(Scalars.GraphQLBigInteger)
                .description("主键")
                .build();
    }

    private GraphQLFieldDefinition contentFieldDefinition() {
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name("content")
                .type(Scalars.GraphQLString)
                .description("内容文本")
                .build();
    }

    private GraphQLFieldDefinition userIdFieldDefinition() {
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name("userId")
                .type(Scalars.GraphQLBigInteger)
                .description("归属用户ID")
                .build();
    }

    private InputValueDefinition idParameterDefinition() {
        SourceLocation sourceLocation = new SourceLocation(-1, -1, "id");
        Description description = new Description("", sourceLocation, false);
        InputValueDefinition valueDefinition = new InputValueDefinition("id");
        valueDefinition.setDescription(description);
        valueDefinition.setDefaultValue(new StringValue(""));
        valueDefinition.setType(new TypeName("String"));
        return valueDefinition;
    }

    public static void main(String[] args) {
        GraphqlConfigTest graphqlConfig = new GraphqlConfigTest();
        GraphQLSchema graphQLSchema = graphqlConfig.graphQLSchema();
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult result = graphQL.execute("query { moment(id: \"1\") {id userId content} }");
        System.out.println(result);
    }
}
