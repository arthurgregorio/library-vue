package br.eti.arthurgregorio.library.infrastructure.spring;

import br.eti.arthurgregorio.library.domain.entities.administration.Authorities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 12/02/2020
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${library.oauth.resource-id}")
    private String resourceId;

    /**
     * {@inheritDoc}
     *
     * @param configurer
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) {
        configurer.resourceId(this.resourceId).stateless(false);
    }

    /**
     * {@inheritDoc}
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                .and()
                    .authorizeRequests()
                        .antMatchers("/actuator/info/**", "/actuator/health/**")
                            .permitAll()
                        .antMatchers("/actuator/**")
                            .hasAnyAuthority(Authorities.ADMINISTRATOR)
                        .antMatchers("/api/administration/**")
                            .hasAnyAuthority(Authorities.ADMINISTRATOR)
                        .antMatchers("/api/registration/**")
                            .hasAnyAuthority(Authorities.ADMINISTRATOR, Authorities.LIBRARIAN)
                        .antMatchers("/api/**", "/actuator/**")
                            .authenticated()
                .and()
                    .exceptionHandling()
                        .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}