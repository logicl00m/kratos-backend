package org.kratos.backend.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
	
	private final ResourceOperationAuthorizationManager resourceOperationAuthorizationManager;
	private final SecurityConfigurationProperties securityConfigurationProperties;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.anonymous(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> {
					                       List<String> publicPaths = securityConfigurationProperties.getPaths()
					                                                                                 .getPublicPaths();
					                       log.info("allowing the public paths: {}", publicPaths);
										   
					                       publicPaths
							                       .forEach(path -> auth.requestMatchers(path)
							                                            .permitAll());
					                       auth.requestMatchers("/api/v1/**")
					                           .access(AuthorizationManagers.allOf(
							                           AuthenticatedAuthorizationManager.authenticated(),
							                           resourceOperationAuthorizationManager
					                           ));
				                       }
				)
				.oauth2ResourceServer(jwt -> jwt.jwt(Customizer.withDefaults()))
				.cors(cors -> cors.configurationSource(request -> {
					var config = new CorsConfiguration();
					config.setAllowCredentials(true);
					SecurityConfigurationProperties.Cors corsConfig = securityConfigurationProperties.getCors();
					
					log.debug("allowing cors origins: {}", corsConfig.getAllowedOrigins());
					log.debug("allowing cors methods: {}", corsConfig.getAllowedMethods());
					log.debug("allowing cors headers: {}", corsConfig.getAllowedHeaders());
					
					config.setAllowedOrigins(corsConfig.getAllowedOrigins());
					config.setAllowedMethods(corsConfig.getAllowedMethods());
					config.setAllowedHeaders(corsConfig.getAllowedHeaders());
					return config;
				}))
				.build();
	}
}
