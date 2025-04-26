package ru.kpfu.itis.kononenko.shedule.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import ru.kpfu.itis.kononenko.shedule.service.UserService;

import static org.springframework.security.config.Customizer.withDefaults;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(withDefaults())
                .httpBasic(withDefaults())
                .formLogin(form -> form
                        .loginPage("/auth/sign-in").permitAll()
                        .defaultSuccessUrl("/profile", true)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/auth/**", "/static/**", "/currency").permitAll()
                        .anyRequest().authenticated()
                )
                .rememberMe(withDefaults())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/auth/sign-in"))
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                )
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }



}
