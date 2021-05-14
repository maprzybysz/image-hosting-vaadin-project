package pl.maprzybysz.imagehostingvaadin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.maprzybysz.imagehostingvaadin.repository.AppUserRepository;
import pl.maprzybysz.imagehostingvaadin.model.AppUser;
import pl.maprzybysz.imagehostingvaadin.service.UserDetailsServiceImpl;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private AppUserRepository appUserRepository;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AppUserRepository appUserRepository) {
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/galleryImage").hasAnyRole("USER", "ADMIN")
                .antMatchers("/uploadImage").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        appUserRepository.save(new AppUser("User", passwordEncoder().encode("123"), "ROLE_USER"));
        appUserRepository.save(new AppUser("Admin", passwordEncoder().encode("123"), "ROLE_ADMIN"));
    }

}
