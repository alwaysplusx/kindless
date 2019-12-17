package com.kindless.moment.graphql.type;

import graphql.language.*;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;

import java.util.List;

/**
 * @author wuxin
 */
public class FieldType implements Type<FieldType> {

    public static FieldType of(java.lang.reflect.Field field) {
        return null;
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public SourceLocation getSourceLocation() {
        return null;
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }

    @Override
    public boolean isEqualTo(Node node) {
        return false;
    }

    @Override
    public FieldType deepCopy() {
        return null;
    }

    @Override
    public TraversalControl accept(TraverserContext<Node> context, NodeVisitor visitor) {
        // return visitor.visitTypeName();
        return null;
    }

}
