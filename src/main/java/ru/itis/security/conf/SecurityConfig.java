package ru.itis.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import ru.itis.security.filters.CookieAuthFilter;
import ru.itis.security.handlers.CustomLogoutHandler;

@ComponentScan("ru.itis")
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private CustomLogoutHandler logoutHandler;
    @Autowired
    private CookieAuthFilter cookieAuthFilter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/signIn")
                .usernameParameter("email")
                .successHandler(successHandler)
                .failureUrl("/signIn?error")
                .permitAll()
                .and()
                .logout()
                .addLogoutHandler(logoutHandler)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signIn?logout")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/signUp/**")
                .anonymous()
                .anyRequest()
                .authenticated();

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(cookieAuthFilter, LogoutFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
