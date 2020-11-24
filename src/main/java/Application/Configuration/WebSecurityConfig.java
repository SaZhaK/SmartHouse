package Application.Configuration;

import Application.Services.UserService;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/console/**").permitAll();

        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/button/**").permitAll()
                .antMatchers("/sound/**").permitAll()
                .antMatchers("/joystick/**").permitAll()
                .antMatchers("/red/**").permitAll()
                .antMatchers("/green/**").permitAll()
                .antMatchers("/blue/**").permitAll()
                .antMatchers("/angle/**").permitAll()
                .antMatchers("/fan/**").permitAll()
                .antMatchers("/js/**").permitAll()
                //.antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/registration").permitAll()
                .antMatchers("/", "/resources/**").permitAll()
;

        httpSecurity.headers().frameOptions().disable();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}