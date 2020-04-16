package br.eti.arthurgregorio.library.infrastructure.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 25/03/2020
 */
@Configuration
public class MailTemplateConfiguration {

    /**
     *
     * @return
     */
    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(this.htmlTemplateResolver());
        return templateEngine;
    }

    /**
     *
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver htmlTemplateResolver(){

        final var templateResolver = new SpringResourceTemplateResolver();

        templateResolver.setPrefix("classpath:/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return templateResolver;
    }
}