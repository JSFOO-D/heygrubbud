package com.assignment.heygrubbud.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;

//import java.util.List;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig {
//
//    private static final String SECURE_ADMIN_PASSWORD = "foodie123";


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    TODO
//        http
//                .csrf().disable()
//                .formLogin()
//                .loginPage("/index.html")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/chat.html")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/index.html")
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/js/**", "/lib/**", "/images/**", "/css/**", "/index.html", "/").permitAll()
//                .antMatchers("/websocket").hasRole("ADMIN")
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
//                .anyRequest().authenticated();
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//      TODO
//        auth.authenticationProvider(new AuthenticationProvider() {
//
//            @Override
//            public boolean supports(Class<?> authentication) {
//                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//            }
//
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//
//                List<GrantedAuthority> authorities = SECURE_ADMIN_PASSWORD.equals(token.getCredentials()) ?
//                        AuthorityUtils.createAuthorityList("ROLE_ADMIN") : null;
//
//                return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
//            }
//        });
//    }
//}
