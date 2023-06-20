package com.example.Train.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomSuccessHandler customSuccessHandler;
	
    @Bean
	public UserDetailsService getUserDetailsService() {
		return new   CustomUserDetailServiceImpl() ;
	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(this.getUserDetailsService());
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		  http.authorizeRequests()
		 .antMatchers("/").permitAll()
		 .antMatchers("/admin/**").hasRole("ADMIN")
		 .antMatchers("/user/**").hasRole("USER")
		 .and()
		 .formLogin()
	     .loginPage("/login")
		 .loginProcessingUrl("/login")
		 .successHandler(customSuccessHandler)
		 .and().csrf().disable();
	}
    

    
}
