package ma.youcode.Al.Baraka.Digital.config;

import ma.youcode.Al.Baraka.Digital.security.AccessDeniedHandlerImpl;
import ma.youcode.Al.Baraka.Digital.security.AuthTokenFilter;
import ma.youcode.Al.Baraka.Digital.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthTokenFilter  authTokenFilter(){
        return   new AuthTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        if (userDetailsService instanceof UserDetailsPasswordService) {
            provider.setUserDetailsPasswordService((UserDetailsPasswordService) userDetailsService);
        }

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public AuthenticationEntryPointImpl authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }
    @Bean
    public AccessDeniedHandlerImpl accessDeniedHandler(){
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity  httpSecurity){
        httpSecurity.csrf(csrf ->  csrf.disable())
                .cors(withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth
                                .requestMatchers("/api/auth/signin","/api/auth/signup").permitAll()
                                .requestMatchers("/api/auth/logout").authenticated()
                                .requestMatchers( "/api/client/**").hasAuthority("CLIENT")
                                .requestMatchers( "/api/admin/**").hasAuthority("ADMIN")
                                .requestMatchers( "/api/agent/**").hasAuthority("AGENT_BANCAIRE")
                                .anyRequest().authenticated()).exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .authenticationProvider(daoAuthenticationProvider());
        httpSecurity.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain oauth2PendingFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/auth/api/agent/operations/pending")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasAuthority("SCOPE_operations.read"))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(oauth2JwtDecoder())
                                .jwtAuthenticationConverter(scopeJwtAuthenticationConverter())));

        return http.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");
        converter.setAuthoritiesClaimName("realmaccess.roles");

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(converter);
        return authConverter;
    }

    @Bean
    public JwtAuthenticationConverter scopeJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();
        scopeConverter.setAuthorityPrefix("SCOPE_");
        scopeConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(scopeConverter);
        return converter;
    }

    @Bean
    public JwtDecoder oauth2JwtDecoder() {
        String jwkSetUri =
                "http://localhost:8180/realms/albaraka/protocol/openid-connect/certs";

        String issuer =
                "http://localhost:8180/realms/albaraka";

        NimbusJwtDecoder decoder =
                NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        OAuth2TokenValidator<Jwt> validator =
                JwtValidators.createDefaultWithIssuer(issuer);

        decoder.setJwtValidator(validator);
        return decoder;




    }

}