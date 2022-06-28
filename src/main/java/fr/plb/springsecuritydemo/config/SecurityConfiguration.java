package fr.plb.springsecuritydemo.config;

import fr.plb.springsecuritydemo.domain.PersistentLoginToken;
import fr.plb.springsecuritydemo.service.PersistentLoginsTokenService;
import fr.plb.springsecuritydemo.service.security.UserDetailsServiceMongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceMongo userDetailsServiceMongo;
    private final PersistentLoginsTokenService persistentLoginsTokenService;

    public SecurityConfiguration(UserDetailsServiceMongo userDetailsServiceMongo,
                                 PersistentLoginsTokenService persistentLoginsTokenService) {
        this.userDetailsServiceMongo = userDetailsServiceMongo;
        this.persistentLoginsTokenService = persistentLoginsTokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/*").authenticated()
                .and().rememberMe()
                .tokenRepository(persistentLoginsTokenService)
                .and()
                .httpBasic()
                .and().logout().logoutUrl("/logout-perso")
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceMongo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
