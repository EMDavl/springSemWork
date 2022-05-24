package ru.itis.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class ApplicationInitializer implements WebApplicationInitializer {

    private static final String TMP_FOLDER = "C:\\Users\\PC-1\\Desktop\\springSemWork\\tmp";
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);

        ContextLoaderListener listener = new ContextLoaderListener(context);
        servletContext.addListener(listener);

        ServletRegistration.Dynamic dispatcherServlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(context));

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
                MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);

        servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, false, "/*");

        dispatcherServlet.setMultipartConfig(multipartConfigElement);
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");
        dispatcherServlet.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

}
