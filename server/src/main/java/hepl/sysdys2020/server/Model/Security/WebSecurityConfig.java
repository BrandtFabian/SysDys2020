package hepl.sysdys2020.server.Model.Security;


import hepl.sysdys2020.server.Model.Service.UserDetailsImpl;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsImpl() {
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
         .authorizeRequests()
                ///stock + panier
                .antMatchers("/user/panier/**").authenticated()
                .antMatchers("/create/cart/**").permitAll()
                .antMatchers("/cart/add/**").permitAll()
                .antMatchers("/delete/add/**").permitAll()
                .antMatchers("/create/order/**").authenticated()
                .antMatchers("/delivery/amount/**").authenticated()
                .antMatchers("/finishorder/**").authenticated()
                .antMatchers("/server/panier/**").authenticated()
                .antMatchers("/server/stock/all").permitAll()
                .and()
                .formLogin().and()
                .httpBasic()
               ;


    }


}
