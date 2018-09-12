package com.himalaya.auth.config;

import com.himalaya.auth.filter.CustomAuthFilter;
import com.himalaya.auth.service.AuthUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月7日 下午4:41:54
* Description
*/
@Configuration
@EnableWebSecurity
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthFilter customAuthFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.addFilterBefore(customAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/index").hasRole("SEARCH")
//            .antMatchers("/index").hasRole("USER")
            .antMatchers("/user").hasRole("ADMIN")
			.antMatchers("/get").hasRole("SEARCH")
			.antMatchers("/post").hasRole("SEARCH")
            .anyRequest().authenticated()
            .and()
            .logout().permitAll()
            .and()
            .formLogin();
        http.csrf().disable();
    }

	/**
	 * 自定义用户信息服务
	 */
	@Bean
	public AuthUserDetailServiceImpl customizedUserDetailsService() {
		return new AuthUserDetailServiceImpl();
	}
	
	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customizedUserDetailsService());
	}
}
