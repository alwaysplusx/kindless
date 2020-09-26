package com.kindless.todo.config;

import com.kindless.core.dingtalk.DingtalkActionDispatcher;
import com.kindless.core.dingtalk.DingtalkActionHandler;
import com.kindless.core.dingtalk.action.ActionParser;
import com.kindless.todo.dingtalk.Options;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DingtalkConfig {

    @Bean
    public DingtalkActionDispatcher dingtalkActionDispatcher(ObjectProvider<DingtalkActionHandler> handlers) {
        return new DingtalkActionDispatcher(handlers);
    }

    @Bean
    public ActionParser actionParser() {
        ActionParser parser = new ActionParser();
        parser.addActionOptions(Options.addAction());
        parser.addActionOptions(Options.deleteAction());
        parser.addActionOptions(Options.listAction());
        return parser;
    }

}
