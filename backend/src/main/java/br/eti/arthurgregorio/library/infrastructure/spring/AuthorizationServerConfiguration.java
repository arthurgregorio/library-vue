package br.eti.arthurgregorio.library.infrastructure.spring;

import br.eti.arthurgregorio.library.application.components.CurrentUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Map;

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
    private final AuthenticationManager authenticationManager;

    /**
     *
     * @param encoder
     * @param manager
     */
    public AuthorizationServerConfiguration(PasswordEncoder encoder, AuthenticationManager manager) {
        this.passwordEncoder = encoder;
        this.authenticationManager = manager;
        this.tokenStore = new InMemoryTokenStore();
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
                .tokenEnhancer(this.tokenEnhancer())
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
        tokenServices.setTokenStore(this.tokenStore);
        tokenServices.setTokenEnhancer(this.tokenEnhancer());
        return tokenServices;
    }

    /**
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new AuthenticatedUserTokenEnhancer();
    }

    /**
     * Token Enhancer to provide user authority info
     */
    public static class AuthenticatedUserTokenEnhancer implements TokenEnhancer {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

            final var currentUser = (CurrentUser) authentication.getPrincipal();
            final var token = (DefaultOAuth2AccessToken) accessToken;

            token.setAdditionalInformation(Map.of("authenticated_user", currentUser));

            return token;
        }
    }
}