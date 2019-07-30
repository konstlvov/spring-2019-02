package booklib;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
						.username("user")
						.password("user")
						.roles("USER")
						.build();
		return new MapReactiveUserDetailsService(user);
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		// allow all
		http.authorizeExchange().anyExchange().permitAll();

		// allow all but HttpMethod.DELETE
		//		http.authorizeExchange().pathMatchers(HttpMethod.DELETE).denyAll()
    //						.and().authorizeExchange().anyExchange().permitAll()
    //						;

//		http.authorizeExchange()
//						.anyExchange().authenticated()
//						//.and().httpBasic()
//						.and().formLogin();

		return http.build();
	}
}