package booklib;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

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

//	private CorsConfigurationSource configurationSource() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.addAllowedOrigin("*");
//		config.setAllowCredentials(true);
//		config.addAllowedHeader("X-Requested-With");
//		config.addAllowedHeader("Content-Type");
//		config.addAllowedHeader("Cookie");
//		config.addAllowedHeader("Access-Control-Allow-Credentials");
//		config.addAllowedMethod(HttpMethod.POST);
//		config.addAllowedMethod(HttpMethod.OPTIONS);
//		config.addAllowedMethod(HttpMethod.GET);
//		config.addAllowedMethod(HttpMethod.PUT);
//		source.registerCorsConfiguration("/fluxbooks/**", config);
//		return source;
//	}


	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

		// allow all:
		// http.authorizeExchange().anyExchange().permitAll();

		// что-то похожее на правду: страница /login отдается всем, а все остальное - только после авторизации
		RedirectServerAuthenticationSuccessHandler h = new RedirectServerAuthenticationSuccessHandler("/index.html");
		http.authorizeExchange().pathMatchers("/login").permitAll()
						.and().authorizeExchange().anyExchange().authenticated()
						//.and().cors().configurationSource(configurationSource())
		        .and().formLogin().authenticationSuccessHandler(h)

						.and().csrf().disable()
    ;

		// allow all
//		http.authorizeExchange().anyExchange().permitAll().and().cors().configurationSource(configurationSource())
//		.and().csrf()
//		.and().formLogin()
//		;

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