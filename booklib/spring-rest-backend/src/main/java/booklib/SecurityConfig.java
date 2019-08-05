package booklib;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import java.net.URISyntaxException;

@EnableWebFluxSecurity
public class SecurityConfig {
	private final UsersService us;

	public SecurityConfig(UsersService us) {
		this.us = us;
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		return new MapReactiveUserDetailsService(us.getUsers());
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws URISyntaxException {
		// что-то похожее на правду: страница /login отдается всем, а все остальное - только после авторизации
		RedirectServerAuthenticationSuccessHandler h = new RedirectServerAuthenticationSuccessHandler("/index.html");
		http.authorizeExchange().pathMatchers("/login").permitAll()
						.and().authorizeExchange().anyExchange().authenticated()
						.and().formLogin().authenticationSuccessHandler(h)
						.and().csrf().disable()
						;
		http.requestCache().disable(); // наконец-то! если это не написать, то надо открывать только по ссылке http://localhost:8080/login,
		// а если открыть просто по адресу http://localhost:8080, то после успешного логина получим ошибку Whitelabel error page
		// потому что оно будет думать, что мы просили /, а не index.html, а на / у нас ничего не висит,
		// а перенаправление на index.html само по себе тут не работает.
		// Если же написать http.requestCache().disable(), то можно открывать по ссылке http://localhost:8080,
		// и оно отработает без ошибок.
		return http.build();
	}
}
