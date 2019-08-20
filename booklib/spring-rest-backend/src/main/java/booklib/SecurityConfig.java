package booklib;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.net.URISyntaxException;

@EnableWebFluxSecurity
public class SecurityConfig {
	private final UsersService us;
	private final YamlConfig config;

	public SecurityConfig(UsersService us, YamlConfig config) {
		this.us = us;
		this.config = config;
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		return new MapReactiveUserDetailsService(us.getUsers());
	}

	private CorsConfigurationSource configurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedHeader("X-Requested-With");
		config.addAllowedHeader("Content-Type");
		config.addAllowedHeader("Cookie");
		config.addAllowedHeader("Access-Control-Allow-Credentials");
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.GET);
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.DELETE);
		source.registerCorsConfiguration("/fluxbooks/**", config);
		return source;
	}

	public SecurityWebFilterChain securityForAngularLiveDevServ(ServerHttpSecurity http) throws URISyntaxException {
		// тестовое, для отладки фронтенда: запретить всем выполнять HttpMethod.DELETE
		http
						.authorizeExchange().pathMatchers(HttpMethod.DELETE, "/fluxbooks/**").denyAll() // .hasAuthority("DELETE_EVERYTHING") //   .hasRole("admin")
						.and().authorizeExchange().anyExchange().permitAll()
						.and().cors().configurationSource(configurationSource())
						.and().csrf().disable()
		;
		return http.build();
		// конец отладочного блока
	}

	public SecurityWebFilterChain securityForAngularProd(ServerHttpSecurity http) throws URISyntaxException {
		// что-то похожее на правду: страница /login отдается всем, а все остальное - только после авторизации
		// Конфигурация доступа:
		// После успешной авторизации перейти на index.html:
		RedirectServerAuthenticationSuccessHandler h = new RedirectServerAuthenticationSuccessHandler("/index.html");
		http.authorizeExchange().pathMatchers("/login").permitAll()// страницу /login отдавать можно всем
						// Конфигурация: изменение данных доступно только пользователю с ролью admin
						// (URL в antPatterns можно не указывать, тогда запрет на вызов метода будет распространяться на любой URL,
						// но, поскольку в задании надо сделать разграничение по URL, то указываю его. Таким образом можно
						// регулировать доступ более гранулярно, выдавая доступ к нужным api пользователям с конкретными ролями).
						.and().authorizeExchange().pathMatchers(HttpMethod.DELETE, "/fluxbooks/**").hasRole("admin")
						.and().authorizeExchange().pathMatchers(HttpMethod.POST, "/fluxbooks/**").hasRole("admin")
						.and().authorizeExchange().pathMatchers(HttpMethod.PUT, "/fluxbooks/**").hasRole("admin")
						.and().authorizeExchange().pathMatchers(HttpMethod.GET, "/fluxbooks/**").hasRole("user")
						.and().authorizeExchange().anyExchange().authenticated()// anyExchange должен идти последним в списке, иначе приложение не стартует
						.and().formLogin().authenticationSuccessHandler(h)
						.and().csrf().disable() // нужно, чтобы работали cross-origin запросы. В данном случае ангулярное приложение хостит тот же веб-контейнер, что и api, и это можно не писать
		;
		http.requestCache().disable(); // наконец-то! если это не написать, то надо открывать только по ссылке http://localhost:8080/login,
		// а если открыть просто по адресу http://localhost:8080, то после успешного логина получим ошибку Whitelabel error page
		// потому что оно будет думать, что мы просили /, а не index.html, а на / у нас ничего не висит,
		// а перенаправление на index.html само по себе тут не работает.
		// Если же написать http.requestCache().disable(), то можно открывать по ссылке http://localhost:8080,
		// и оно отработает без ошибок.
		return http.build();
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws URISyntaxException {
		String sc = config.getSecurityConfig();
		if (sc.equals("angular-live-dev")) {
			return securityForAngularLiveDevServ(http);
		}
		else {
			return securityForAngularProd(http);
		}
	}
}
