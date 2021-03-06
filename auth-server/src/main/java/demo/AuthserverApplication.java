package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthserverApplication {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		System.out.println("name frm auth server-----------"+user.getName());
		return user;
	}
	
	@RequestMapping("/userRole")
	@ResponseBody
	 public Map<String, Object> userAndRole(Principal user) {
	  Map<String, Object> map = new LinkedHashMap<String, Object>();
	  map.put("name", user.getName());
	  map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities()));
	  return map;
	 }

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
				.withClient("acme").authorities("ROLE_CLIENT")
				.secret("acmesecret")
				.authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
				.scopes("webshop")
				;
		}
	}
}
