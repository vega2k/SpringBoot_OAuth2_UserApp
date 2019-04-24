package me.vega2k.useroauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
//Resource (Rest API 서비스)의 인증을 처리해주는 역할
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		//token기반의 인증만 처리하려면 true, token 이외에 여러가지 인증을 처리하려면 false
		resources.resourceId("resource_id").stateless(false);
	}
	
	//실제인증을 처리하는 부분
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.anonymous().disable()
			.authorizeRequests()
					.antMatchers("/users/**").authenticated()
					.and()
			.exceptionHandling()
					.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
