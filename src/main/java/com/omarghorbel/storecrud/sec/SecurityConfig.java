package com.omarghorbel.storecrud.sec;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.formLogin();
        // on  vas lui demandé de ne pas generer le csrf synchronized token
        http.csrf().disable();
        /// on vas demaandé de ne pas utilisé les sessions on utilise une aythentification de type stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //
        http.authorizeRequests().antMatchers("/login/**","/register/**").permitAll();


        http.authorizeRequests().antMatchers("/hello/","/hello/**").permitAll();
        http.authorizeRequests().antMatchers("/image/","/image/**").permitAll();


//        http.authorizeRequests().antMatchers("/products/**","/products").permitAll();
//        http.authorizeRequests().antMatchers("/stores/**","/stores").permitAll();


        //http.authorizeRequests().antMatchers("/appUsers/**","/appRoles/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        // qu on un utilisateur veux s authentifier on va faire appel a ce filtre  JWTAuthentificationFilter qu on va creer
        // on fait JWTAuthentificationFilter  ce filtre la qui vas nou generele token JWT UNE Fois l utilisateur va saisir son username et son password
        //
        http.addFilter(new JWTAuthentificationFilter(authenticationManager()));
        http.addFilterBefore(new  JWTAutorisationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
