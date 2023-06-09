package com.foxminded.university.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeHttpRequests(
						registry -> registry.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
				.oauth2ResourceServer(oauth2Configurer -> oauth2Configurer
						.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwt -> {
							Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
							Collection<String> roles = realmAccess.get("roles");
							var grantedAuthorities = roles.stream()
									.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
							return new JwtAuthenticationToken(jwt, grantedAuthorities);
						})));

		return httpSecurity.build();
	}

}
