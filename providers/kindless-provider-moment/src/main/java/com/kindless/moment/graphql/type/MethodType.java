package com.kindless.moment.graphql.type;


import graphql.language.*;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxin
 */
public class MethodType implements Type<MethodType> {

    private final Method method;

    public MethodType(Method method) {
        this.method = method;
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>();
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
        return node instanceof MethodType && ((MethodType) node).method == method;
    }

    @Override
    public MethodType deepCopy() {
        return new MethodType(method);
    }

    @Override
    public TraversalControl accept(TraverserContext<Node> context, NodeVisitor visitor) {
        return visitor.visitTypeName(new TypeName(method.getName()), context);
    }
}
