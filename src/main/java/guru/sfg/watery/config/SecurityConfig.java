package guru.sfg.watery.config;

import guru.sfg.watery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//        return new LdapShaPasswordEncoder();
//        return new StandardPasswordEncoder();
//        return new BCryptPasswordEncoder();
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize->{
                    authorize
                            .antMatchers("/","/webjars/**","/login","/resources/**").permitAll()
                            .antMatchers("/waters/find","/waters*").permitAll()
                            .antMatchers(HttpMethod.GET,"/api/v1/water/**").permitAll()
                            .mvcMatchers(HttpMethod.GET,"/api/v1/waterUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//    }

    //In Memory Authentication Fluent API
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
                .password("{bcrypt}$2a$10$yEXeTTHAcFLd8o108B4Wu..cRtkkbqfMPahlyIM..hPjaQiGalPZS")
                .roles("ADMIN")
                .and()
                .withUser("user")
//                .password("{SSHA}6HdxGo8hTVuIIQxmhavABRQ1fGWNQr72GBoR/Q==")
//                .password("a093e3ee4a93f0edb392d3538540d38eb5811a829dd4be48906df96432447b4bcbf1e2ca26869289")
//                .password("$2a$10$zWYVT98Uc1HGQfFRaxG7wu2ltWqxcTsZU6r8.1wfOdQSPTL3DQrc6")
                .password("{sha256}680df977cbb82501407d0df1a770bd0a3cc390df246351e1c88b87349af0a31226979c58bc753a09")
                .roles("USER")
                .and()
                .withUser("scott")
//                .password("{ldap}{SSHA}kWnaNVkJz+Q3uloTQRyLLY+Vq6OXkwe6o0UqUw==")
                .password("{bcrypt15}$2a$15$LKhy11k623pcmQ7q1o1mtujHp52ubwzIOLen4cu0ngn2uxkVl13em")
                .roles("CUSTOMER");
    }



}
