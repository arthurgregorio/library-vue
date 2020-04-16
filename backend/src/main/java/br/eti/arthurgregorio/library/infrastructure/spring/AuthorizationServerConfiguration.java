package br.eti.arthurgregorio.library.infrastructure.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final TokenStore tokenStore = new InMemoryTokenStore();

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
                .authenticationManager(this.authenticationManager)
                .userDetailsService(this.userDetailsService);
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
                        .refreshTokenValiditySeconds(300000)
                        .resourceIds(this.resourceId)
                        .secret(this.passwordEncoder.encode(this.webClientSecret))
                        .accessTokenValiditySeconds(50000);
    }

    /**
     *
     * @return
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(this.tokenStore);
        return tokenServices;
    }
}