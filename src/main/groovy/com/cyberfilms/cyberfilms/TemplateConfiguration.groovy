package com.cyberfilms.cyberfilms

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver

@Configuration
class TemplateConfiguration {

    @Autowired
    private ApplicationContext applicationContext

    @Bean(name = "textTemplateEngine")
    TemplateEngine textTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine()
        templateEngine.addTemplateResolver(textTemplateResolver())
        return templateEngine
    }

    private ITemplateResolver textTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver()
        templateResolver.setPrefix("/templates/")
        templateResolver.setSuffix(".template")
        templateResolver.setTemplateMode(TemplateMode.TEXT)
        templateResolver.setCharacterEncoding("UTF8")
        templateResolver.setCheckExistence(true)
        templateResolver.setCacheable(false)
        return templateResolver
    }
}
