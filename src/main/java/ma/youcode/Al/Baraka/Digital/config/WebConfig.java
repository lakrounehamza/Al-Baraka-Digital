package ma.youcode.Al.Baraka.Digital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity  httpSecurity){
        httpSecurity.csrf(csrf ->  csrf.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/auth/**").permitAll());
        return httpSecurity.build();
    }
}
