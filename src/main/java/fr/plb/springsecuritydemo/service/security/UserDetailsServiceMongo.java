package fr.plb.springsecuritydemo.service.security;

import fr.plb.springsecuritydemo.domain.User;
import fr.plb.springsecuritydemo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceMongo implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceMongo(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByLogin = userRepository.findOneByLogin(username);
        User user = userByLogin.orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> authorities = user.getAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getName()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.
                User.withUsername(user.getLogin()).password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
