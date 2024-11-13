package com.hk.transportProject.member.config;

import com.hk.transportProject.member.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/BUSTAEWAJO")
                .username("root")
                .password("1234")
                .build();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(BCryptPasswordEncoder bCryptPasswordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
