package shupeyko.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class JwtUtil {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests()
                .requestMatchers("/api/**")
                .hasAuthority("api.resource")
                .anyRequest()
                .authenticated()
                .and()
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .oauth2ResourceServer(configurer -> {
                    configurer.jwt(jwtConfigurer -> {
                        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
                            Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
                            Map<String, Object> postman = (Map<String, Object>) resourceAccess.get("postman");
                            List<String> roles = (List<String>) postman.get("roles");

                            return roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .map(it -> (GrantedAuthority) it)
                                    .toList();
                        });

                        jwtConfigurer.jwtAuthenticationConverter(converter);
                    });
                })
                .build();
    }

}
