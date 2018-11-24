package br.ufma.lsdi.configuration.JSF;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class ServletContextConfigurator implements ServletContextInitializer {

    @Value("${javax.faces.PROJECT_STAGE}")
    private String project_stage;
    @Value("${facelets.DEVELOPMENT}")
    private String facelets_development;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", "true");
        servletContext.setInitParameter("javax.faces.VALIDATE_EMPTY_FIELDS", "false");
        servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
        servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "2");
        servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
//        servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", project_stage);
        servletContext.setInitParameter("facelets.DEVELOPMENT", facelets_development);
        servletContext.setInitParameter("primefaces.UPLOADER", "native");
        servletContext.setInitParameter("primefaces.THEME", "ultima-indigo");
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
    }
}
