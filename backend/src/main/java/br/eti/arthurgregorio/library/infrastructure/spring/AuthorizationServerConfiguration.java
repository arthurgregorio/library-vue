package br.eti.arthurgregorio.library.infrastructure.spring;

import br.eti.arthurgregorio.library.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 31/01/2020
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Value("${library.oauth.resource-id}")
    private String resourceId;

    @Value("${library.oauth.web-client.name}")
    private String webClientName;
    @Value("${library.oauth.web-client.secret}")
    private String webClientSecret;

    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    /**
     *
     * @param passwordEncoder
     * @param authenticationService
     * @param authenticationManager
     */
    public AuthorizationServerConfiguration(PasswordEncoder passwordEncoder,
                                            AuthenticationService authenticationService,
                                            AuthenticationManager authenticationManager) {
        this.tokenStore = new InMemoryTokenStore();

        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * {@inheritDoc}
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(this.tokenStore)
                .userDetailsService(this.authenticationService)
                .authenticationManager(this.authenticationManager);
    }

    /**
     * {@inheritDoc}
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
    }

    /**
     * {@inheritDoc}
     *
     * @param configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                    .withClient(this.webClientName)
                        .authorizedGrantTypes("password", "refresh_token")
                        .scopes("all")
                        .refreshTokenValiditySeconds(172800) // 48 hours
                        .resourceIds(this.resourceId)
                        .secret(this.passwordEncoder.encode(this.webClientSecret))
                        .accessTokenValiditySeconds(43200); // 12 hours
    }

    /**
     *
     * @return
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(this.tokenStore);
        return tokenServices;
    }
}