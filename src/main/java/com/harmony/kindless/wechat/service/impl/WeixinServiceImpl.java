package com.harmony.kindless.wechat.service.impl;

import com.harmony.kindless.apis.CodeException;
import com.harmony.kindless.wechat.WxMpMessageRuleHandler;
import com.harmony.kindless.wechat.service.WeixinService;
import com.harmony.umbrella.util.MemberUtils;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wuxii
 */
@Service
public class WeixinServiceImpl implements WeixinService, ApplicationContextAware {

    private static final Map<Class<?>, Method> SERVICE_GETTER_METHODS;

    static {
        Map<Class<?>, Method> service = Stream
                .of(WxMpService.class.getMethods())
                .filter(MemberUtils::isReadMethod)
                .filter(e -> e.getName().endsWith("Service"))
                .collect(Collectors.toMap(Method::getReturnType, e -> e));
        SERVICE_GETTER_METHODS = Collections.unmodifiableMap(service);
    }

    private final List<WxMpMessageRuleHandler> ruleHandlers = new ArrayList<>();

    private WxMpMessageRouter messageRouter;

    @Autowired
    private WxMpService wxMpService;

    @Override
    public WxMpService getWxMpService() {
        return wxMpService;
    }

    @Override
    public <T> T getWxService(Class<T> serviceType) {
        Method method = SERVICE_GETTER_METHODS.get(serviceType);
        if (method == null) {
            throw new CodeException("weixin service not found " + serviceType);
        }
        try {
            return (T) method.invoke(wxMpService);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CodeException("get weixin service failed", e);
        }
    }

    @Override
    public WxMpXmlOutMessage routeMessage(WxMpXmlMessage inMessage) {
        return getWxMpMessageRouter().route(inMessage);
    }

    private WxMpMessageRouter getWxMpMessageRouter() {
        if (this.messageRouter == null) {
            synchronized (ruleHandlers) {
                if (this.messageRouter == null) {
                    WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
                    OrderComparator.sort(ruleHandlers);
                    ruleHandlers.forEach(e -> e.config(router));
                    this.messageRouter = router;
                }
            }
        }
        return messageRouter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ruleHandlers.addAll(applicationContext.getBeansOfType(WxMpMessageRuleHandler.class).values());
    }

}
