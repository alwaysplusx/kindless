package com.kindless.config.web;

import com.kindless.config.auditor.AuditorConfig;
import com.kindless.core.auditor.WebAuditorExtractor;
import com.kindless.core.web.filter.AuditorHttpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author wuxin
 */
@Configuration
@Import(AuditorConfig.class)
public class WebConfig {

    @Bean
    public FilterRegistrationBean<AuditorHttpFilter> auditorHttpFilter(WebAuditorExtractor auditorExtractor) {
        FilterRegistrationBean<AuditorHttpFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new AuditorHttpFilter(auditorExtractor));
        filterBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterBean;
    }

}
