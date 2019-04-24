package me.vega2k.useroauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

//Token 발행, 
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	//발급하는 token 저장소
	@Autowired
	private TokenStore tokenStore;
	
	//실제로 인증하는 역할을 하는 핵심적인 인터페이스
	@Autowired
	private AuthenticationManager authenticationManagerBeans;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @Autowired
	 private UserDetailsService userDetailsService;
	 
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		//token을 발급받기 위해 인증에 필요한 정보 설정
		configurer
				.inMemory()
				.withClient("vega2k-client")
				.secret(passwordEncoder.encode("vega2k-password"))
				.authorizedGrantTypes("password",
						"authorization_code",
						"refresh_token",
						"implicit")
				.scopes("read","write","trust")
				.accessTokenValiditySeconds(1*60*60)
				.refreshTokenValiditySeconds(6*60*60);
				
	}
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManagerBeans)
				.userDetailsService(userDetailsService);
	}
	
}
