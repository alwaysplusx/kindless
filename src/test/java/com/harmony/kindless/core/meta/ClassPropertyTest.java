package com.harmony.kindless.core.meta;

import java.util.List;

import com.harmony.kindless.core.ClassProperty;
import com.harmony.kindless.core.ClassProperty.MethodProperty;
import com.harmony.kindless.core.controller.UserController;

/**
 * @author wuxii@foxmail.com
 */
public class ClassPropertyTest {

    public static void main(String[] args) {
        ClassProperty property = new ClassProperty(UserController.class);
        System.out.println(property);
        List<MethodProperty> methodProperties = property.getMethodProperties();
        for (MethodProperty props : methodProperties) {
            System.out.println(props);
        }
    }

}
