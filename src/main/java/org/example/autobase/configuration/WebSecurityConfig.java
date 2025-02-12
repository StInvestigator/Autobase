package org.example.autobase.configuration;

import lombok.RequiredArgsConstructor;
import org.example.autobase.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> mapping = new ArrayList<>(List.of("/logs", "/requests", "/trips", "/Vdate"));

        http.csrf().disable();

        http.authorizeRequests().mvcMatchers("/", "/login", "/logout").permitAll();

        http.authorizeRequests().mvcMatchers("/stats")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

        for (String map : mapping) {
            http.authorizeRequests().mvcMatchers(map)
                    .access("hasAnyRole('ROLE_ADMIN')");
        }

        http.authorizeRequests().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http.authorizeRequests().and().formLogin()
            .loginProcessingUrl("/j_spring_security_check")
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
}
