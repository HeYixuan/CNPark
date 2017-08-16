package org.springframe.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // oauth server 不需要 csrf 防护
                .authorizeRequests()
                .antMatchers("/**").permitAll() // public 路径不需要认证即可访问
                .anyRequest().authenticated() //其他页面都需要登录后访问
                .and()
                .httpBasic().disable(); // 禁止 basic 认证
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin").roles("ADMIN")
            .and()
                .withUser("HeYixuan")
                .password("123456").roles("USER");
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //密码加密
        return new BCryptPasswordEncoder();
    }
}
