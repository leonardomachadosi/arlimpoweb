package br.ufma.lsdi.configuration.JSF;

import br.ufma.lsdi.configuration.scope.CustomScopeRegisteringBeanFactoryPostProcessor;
import br.ufma.lsdi.util.PasswordChangeFilter;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

@Configuration
public class FacesConfigurator implements EmbeddedServletContainerCustomizer {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletConfigurator();
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new CustomScopeRegisteringBeanFactoryPostProcessor();
    }

    @Bean
    public FilterRegistrationBean rewriteFilter() {
        FilterRegistrationBean rewriteFilter = new FilterRegistrationBean(new RewriteFilter());
        rewriteFilter.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR);
        rewriteFilter.addUrlPatterns("/*");
        return rewriteFilter;
    }


    @Bean
    public FilterRegistrationBean passwordFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new PasswordChangeFilter());
        filterRegistrationBean.addUrlPatterns("/pages/private/dashboard/dashboard.xhtml");
        return filterRegistrationBean;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);

        mappings.add("ttf", "application/font-sfnt");
        mappings.add("woff", "application/font-woff");
        mappings.add("woff2", "application/font-woff2");
        mappings.add("eot", "application/vnd.ms-fontobject");
        mappings.add("eot?#iefix", "application/vnd.ms-fontobject");
        mappings.add("svg", "image/svg+xml");
        mappings.add("svg#exosemibold", "image/svg+xml");
        mappings.add("svg#exobolditalic", "image/svg+xml");
        mappings.add("svg#exomedium", "image/svg+xml");
        mappings.add("svg#exoregular", "image/svg+xml");
        mappings.add("svg#fontawesomeregular", "image/svg+xml");

        configurableEmbeddedServletContainer.setMimeMappings(mappings);
    }

}
