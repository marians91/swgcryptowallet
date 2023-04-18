package com.swag.crypto.wallet.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/users").hasAnyRole("ADMIN")
                                .requestMatchers("/user/{userId}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/users/update/{id}").hasRole("ADMIN")
                                .requestMatchers("/user/update/{userId}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/user/delete/{email}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/portfolio/{userId}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/landing").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/wallet/init").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/wallet/redirect/create/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/wallet/redirect/import/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/wallet/create/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/wallet/import/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/transaction/redirect/send/{address}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/transaction/redirect/receive/{transaction}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/transaction/send/{address}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/transaction/receive").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/transaction/redirect/portfolio/{address}").hasAnyRole("ADMIN", "USER")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/wallet/init")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}

