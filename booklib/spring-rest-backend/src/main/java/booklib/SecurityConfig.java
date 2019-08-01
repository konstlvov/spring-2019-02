package booklib;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

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
	private CorsConfigurationSource configurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedHeader("X-Requested-With");
		config.addAllowedHeader("Content-Type");
		config.addAllowedHeader("Cookie");
		config.addAllowedHeader("BSSCookie");
		config.addAllowedHeader("Access-Control-Allow-Credentials");
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.GET);
		source.registerCorsConfiguration("/fluxbooks/**", config);
		return source;
	}


	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		// что-то похожее на правду: страница /login отдается всем, а все остальное - только после авторизации
//		http.authorizeExchange().pathMatchers("/login").permitAll()
//						.and().authorizeExchange().anyExchange().authenticated()
//		        .and().formLogin();

		// allow all
		http.authorizeExchange().anyExchange().permitAll().and().cors().configurationSource(configurationSource())
		.and().csrf()
		;

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