package com.sdjzu.authoritycertificationservice.config;

import com.sdjzu.authoritycertificationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity//开启WebSecurity功能
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法上的保护
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    UserDetailsService userService() {
        return new UserService();
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //从数据库中获取用户认证信息
        auth.userDetailsService(userService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http.csrf().disable();
        http.authorizeRequests()
                //.antMatchers("/admin/category/all").authenticated()
                .antMatchers("/css/**", "/test").permitAll()
                .antMatchers("/admin/**", "/reg").hasRole("ADMIN")///admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
                .anyRequest().authenticated()//其他的路径都是登录后即可访问
                .and().formLogin().loginPage("/login_page")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        /*ValueOperations<String, Object> operations = redisTemplate.opsForValue();
                        String username = authentication.getName();
                        operations.set("username", username,600, TimeUnit.SECONDS);
                        System.err.println(operations.get("username"));*/
                        String username = authentication.getName();
                        httpServletRequest.getSession().setAttribute("username",username);
                        System.err.println("session:"+httpServletRequest.getSession());
                        System.err.println(httpServletRequest.getSession().getAttribute("username"));
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"resultCode\":\"200\",\"result\":\"登录成功!\",\"role\":" + "\"" + authentication.getAuthorities() + "\"}");
                        //out.write("{\"resultCode\":\"200\",\"result\":\"登录成功!\",\"role\":" + "\"" + authentication.getAuthorities() + "\"}");
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"resultCode\":\"100\",\"result\":\"登录失败!\"}");
                        out.flush();
                        out.close();
                    }
                })
                .loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and().logout().permitAll().and().csrf().disable().exceptionHandling()

                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"resultCode\":\"100\",\"result\":\"权限不足!\"}");
                        out.flush();
                        out.close();
                    }
                })
                .and().logout().addLogoutHandler(new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                /*ValueOperations<String, String> operations = redisTemplate.opsForValue();
                String username = authentication.getName();
                redisTemplate.delete("username");
                boolean exists = redisTemplate.hasKey("username");
                if (exists) {
                    System.out.println(operations.get("username"));
                } else {
                    System.out.println("exists is false");
                }*/
                httpServletRequest.getSession().invalidate();
            }
        }).and().sessionManagement().invalidSessionUrl("/login")
        ;
        ////logout默认的url是 /logout,如果csrf启用，则请求方式是POST，否则请求方式是GET、POST、PUT、DELETE


    }


}
