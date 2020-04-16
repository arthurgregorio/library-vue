package br.eti.arthurgregorio.library.infrastructure.spring;

import br.eti.arthurgregorio.library.infrastructure.misc.Translator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 31/01/2020
 */
@Configuration
public class LocaleConfiguration extends AcceptHeaderLocaleResolver {

    private final List<Locale> locales;

    /**
     *
     */
    public LocaleConfiguration() {
        this.locales = List.of(
                new Locale("pt", "BR"),
                new Locale("en", "US"),
                new Locale("es", "ES"));
    }

    /**
     * {@inheritDoc}
     *
     * @param request
     * @return
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), this.locales);
    }

    /**
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {

        Locale.setDefault(new Locale("pt", "BR"));

        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setAlwaysUseMessageFormat(true);
        messageSource.setUseCodeAsDefaultMessage(true);

        messageSource.setBasenames("classpath:i18n/messages", "classpath:i18n/mail");

        Translator.setMessageSource(messageSource);

        return messageSource;
    }

    /**
     *
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        final var validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(this.messageSource());
        return validatorFactoryBean;
    }
}