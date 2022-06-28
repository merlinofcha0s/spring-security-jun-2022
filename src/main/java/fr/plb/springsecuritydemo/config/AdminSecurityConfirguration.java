package fr.plb.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import java.security.DigestInputStream;

@Configuration
@Order(1)
public class AdminSecurityConfirguration extends WebSecurityConfigurerAdapter {


    public DigestAuthenticationFilter getDigestAuthenticationFilter() throws Exception {
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setUserDetailsService(userDetailsServiceBean());
        filter.setAuthenticationEntryPoint(getDigestEntryPoint());
        return filter;
    }

    private DigestAuthenticationEntryPoint getDigestEntryPoint() {
        DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
        entryPoint.setRealmName("vinyl-realm");
        entryPoint.setKey("vinyl-key-secret");
        return entryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/all-admin")
                .addFilter(getDigestAuthenticationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(getDigestEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/all-admin").hasRole("ADMIN");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("password1").roles("USER")
                .and()
                .withUser("admin")
                .password("password2")
                .roles("ADMIN");
    }
}
