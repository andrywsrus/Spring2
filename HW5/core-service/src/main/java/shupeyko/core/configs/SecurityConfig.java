package shupeyko.core.configs;

import shupeyko.core.services.UserService;
import shupeyko.core.utils.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring().antMatchers("/auth/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(JwtRequestFilter jwtRequestFilter, HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests()
                .antMatchers("/api/v1/orders/**").authenticated()
                .antMatchers("h2-console/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider... providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        return new StandardAuthenticationProvider(userService, passwordEncoder);
    }

    @Bean
    public JwtRequestFilter jwtFilter(JwtTokenUtil jwtTokenUtil) {
        return new JwtRequestFilter(jwtTokenUtil);
    }
}