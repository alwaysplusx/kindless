package com.kindless.moment.graphql.type;

import graphql.language.AbstractNode;
import graphql.language.Node;
import graphql.language.NodeVisitor;
import graphql.language.TypeName;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxin
 */
public class ClassType extends AbstractNode<ClassType> {

    private Class<?> typeClass;

    public ClassType(Class<?> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>();
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    @Override
    public boolean isEqualTo(Node node) {
        return node instanceof ClassType && ((ClassType) node).getTypeClass() == this.typeClass;
    }

    @Override
    public ClassType deepCopy() {
        return new ClassType(typeClass);
    }

    @Override
    public TraversalControl accept(TraverserContext<Node> context, NodeVisitor visitor) {
        return visitor.visitTypeName(new TypeName(typeClass.getCanonicalName()), context);
    }

}
