package com.cyberfilms.cyberfilms

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

import javax.servlet.ServletContext
import javax.servlet.ServletException
import javax.servlet.ServletRegistration

class SpringWebInitializer implements WebApplicationInitializer {
    @Override
    void onStartup(ServletContext servletContext) throws ServletException {
        println("doing startup")
        AnnotationConfigWebApplicationContext context = new AnnotationConfigApplicationContext()
        context.register(TemplateConfiguration.class)
        context.setServletContext(servletContext)

        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(context))
        servlet.addMapping("/")
        servlet.setLoadOnStartup(1)
        println("done doing startup")
    }
}
