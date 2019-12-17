package com.kindless.moment.graphql.type;

import graphql.language.AbstractNode;
import graphql.language.Node;
import graphql.language.NodeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;

import java.util.List;

/**
 * @author wuxin
 */
public class JavaType<T extends JavaType<T>> extends AbstractNode<T> {

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public boolean isEqualTo(Node node) {
        return false;
    }

    @Override
    public T deepCopy() {
        return null;
    }

    @Override
    public TraversalControl accept(TraverserContext<Node> context, NodeVisitor visitor) {
        return null;
    }

}
