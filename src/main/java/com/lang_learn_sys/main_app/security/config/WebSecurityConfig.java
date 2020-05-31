package com.lang_learn_sys.main_app.security.config;

import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
//                .antMatchers("/registration").permitAll()
//                     //Доступ только для пользователей с ролью Администратор
//                .antMatchers("/customer/**").hasRole("USER")
//                .antMatchers("/instructor/**").hasRole("INSTRUCTOR")
//                .antMatchers("/accountant/**").hasRole("ACCOUNTANT")
//                .antMatchers("/sales_dep/**").hasRole("SALES_DEP")
//                .antMatchers("/actuator/**").hasRole("TECH")
//                .antMatchers("/employee/**").hasAnyRole("INSTRUCTOR","ACCOUNTANT","SALES_DEP","TECH","ADMIN")
//                .antMatchers("/admin/**").hasAnyRole("TECH","ADMIN")
//                     //Доступ разрешен всем пользователей
//                .antMatchers("/resources/**").permitAll()
                .antMatchers("/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

}