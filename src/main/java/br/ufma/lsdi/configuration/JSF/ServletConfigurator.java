package br.ufma.lsdi.configuration.JSF;

import com.sun.faces.config.FacesInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

public class ServletConfigurator extends ServletRegistrationBean {

    public ServletConfigurator() {
        super();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FacesInitializer facesInitializer = new FacesInitializer();

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(FacesConfigurator.class);
        facesInitializer.onStartup(classSet, servletContext);
    }
}