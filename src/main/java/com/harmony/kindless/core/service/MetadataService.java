package com.harmony.kindless.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.ClassProperty;
import com.harmony.kindless.core.ClassProperty.MethodProperty;
import com.harmony.kindless.core.domain.Module;
import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.domain.Resource;
import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * 通过类路径下的类生成的类信息来辅助生成基础的系统数据, 在基础数据之上可以通过web端提供的页面功能完善系统数据的详细信息
 * 
 * @author wuxii@foxmail.com
 */
@Service
public class MetadataService {

    @Autowired
    private ModuleService menuService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PermissionService permissionService;

    public void initMetadata() {
        List<ClassProperty> metadatas = getClassProperties();
        // init module first
        for (ClassProperty classProperty : metadatas) {
            List<MethodProperty> methodProperties = classProperty.getMethodProperties();
            Module lastMenu = initModules(classProperty);
            initResources(lastMenu, methodProperties);
            initPermissions(lastMenu, methodProperties);
        }
    }

    /**
     * 初始化与classProperty相关的{@linkplain Module}数据
     * 
     * @param classProperty
     */
    public Module init(ClassProperty classProperty) {
        String module = classProperty.getModule();

        String path = classProperty.hasPath() ? classProperty.getPaths().iterator().next() : "";

        Module last = null;

        StringTokenizer st = new StringTokenizer(module, "/");
        String current = null;
        String parent = null;

        while (st.hasMoreTokens()) {
            current = st.nextToken();
            Module menu = new Module(current);
            menu.setPath(path);

            applyCreatorInfo(menu);

            if (parent != null) {
                menu.setParent(new Module(parent));
            }
            last = menuService.save(menu);
            // next
            parent = current;
        }

        return last;

    }

    protected List<ClassProperty> getClassProperties() {
        List<ClassProperty> result = new ArrayList<>();
        for (Class clazz : getControllerClasses()) {
            result.add(parse(clazz));
        }
        return result;
    }

    private void initResources(Module menu, List<MethodProperty> methodProperties) {
        for (MethodProperty methodProperty : methodProperties) {
            for (String r : methodProperty.getResources()) {
                Resource resource = new Resource();
                resource.setPath(r);
                resource.setMenu(menu);
                applyCreatorInfo(resource);
                resourceService.save(resource);
            }
        }
    }

    private void initPermissions(Module menu, List<MethodProperty> methodProperties) {
        for (MethodProperty methodProperty : methodProperties) {
            List<String> permissions = methodProperty.getPermissions();
            for (String p : permissions) {
                Permission permission = new Permission(p);
                permission.setModule(menu);
                applyCreatorInfo(permission);
                permission = permissionService.save(permission);
            }
        }
    }

    private Module initModules(ClassProperty classProperty) {
        String module = classProperty.getModule();
        String path = classProperty.hasPath() ? classProperty.getPaths().iterator().next() : "";

        Module last = null;

        StringTokenizer st = new StringTokenizer(module, "/");
        String current = null;
        String parent = null;

        while (st.hasMoreTokens()) {
            current = st.nextToken();
            Module menu = new Module(current);
            menu.setPath(path);

            applyCreatorInfo(menu);

            if (parent != null) {
                menu.setParent(new Module(parent));
            }
            last = menuService.save(menu);
            // next
            parent = current;
        }

        return last;
    }

    protected ClassProperty parse(Class<?> clazz) {
        String module = ClassProperty.getModule(clazz);
        if (module == null && clazz.getSimpleName().endsWith("Controller")) {
            module = clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 10);
        }
        return new ClassProperty(module, clazz);
    }

    private Set<Class> getControllerClasses() {
        Set<Class> result = new LinkedHashSet<>();
        /*Class[] classes = ApplicationContext.getApplicationClasses(new ClassFilter() {

            @Override
            public boolean accept(Class<?> c) {
                return isController(c);
            }
        });
        Collections.addAll(result, classes);*/
        return result;
    }

    protected boolean isController(Class<?> clazz) {
        return AnnotatedElementUtils.hasAnnotation(clazz, Controller.class);
    }

    protected Module root() {
        Module menu = new Module();
        menu.setCode("Root");
        menu.setName("顶级模块");
        menu.setOrdinal(0);
        menu.setRemark("此处为顶级菜单");
        applyCreatorInfo(menu);
        return menuService.saveOrUpdate(menu);
    }

    private void applyCreatorInfo(BaseEntity entity) {
        // FIXME 当前运行环境的用户, curent context 取消应用状态的检查
        entity.setCreatedTime(new Date());
        entity.setCreatorCode("admin");
        entity.setCreatorName("系统");
        entity.setCreatorId(1l);
    }

}
