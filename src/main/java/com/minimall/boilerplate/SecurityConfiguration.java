package com.minimall.boilerplate;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Title: .
 * <p>Description: </p>

 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  private HttpStatusEntryPoint httpStatusEntryPoint() {
    return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
  }

  @Configuration
  @Order(0)
  public class LoginSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .requestMatchers()
              .antMatchers("/api/wechat/login", "/api/login")
              .and()
              .authorizeRequests().anyRequest().authenticated()
              .and()
              .csrf().disable()
              .anonymous().disable()
              .exceptionHandling()
              .authenticationEntryPoint(httpStatusEntryPoint())
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
  }

  @Configuration
  @Order(1)
  public class WxLoginSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .requestMatchers()
              .antMatchers("/api/wechat/employeeInfo")
              .and()
              .authorizeRequests().anyRequest().authenticated()
              .and()
              .csrf().disable()
              .anonymous().disable()
              .exceptionHandling()
              .authenticationEntryPoint(httpStatusEntryPoint())
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
  }

  @Configuration
  public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .csrf().disable()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

              .and()
              .requestMatchers()
              .antMatchers("/api/**")

              // 系统及共通接口
              .and()
              .authorizeRequests()
              .antMatchers(GET,
                      "/api/pages**",
                      "/api/dictionaries**",
                      "/api/system/menus",
                      "/api/system/menus/authorities"
              ).access("isAuthenticated()")
              .antMatchers(GET, "/api/authorities**")
              .access("@webSecurity.check(authentication, 'SY02') and hasAuthority('AUTH_READ')")
              .antMatchers(POST,
                      "/api/pages/queries",
                      "/api/dictionaries/queries"
              ).access("isAuthenticated()")
              .antMatchers(POST, "/api/authorities/queries")
              .access("@webSecurity.check(authentication, 'SY02') and hasAuthority('AUTH_READ')");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
      web
              .ignoring().antMatchers("/asserts/**")
              .and().ignoring().antMatchers("/api/signup")
              .and().ignoring().antMatchers("/api/alive")

              // 测试
              .and().ignoring().antMatchers("/api/user/**")
              .and().ignoring().antMatchers("/api/commodity/**")
              .and().ignoring().antMatchers("/api/order/**")
              .and().ignoring().antMatchers("/api/messages/**");

    }
  }
}
