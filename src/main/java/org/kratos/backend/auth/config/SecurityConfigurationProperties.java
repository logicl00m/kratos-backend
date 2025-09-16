package org.kratos.backend.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties (prefix = "security")
@Component
@Getter
@Setter
public class SecurityConfigurationProperties {
	
	private Paths paths;
	private Cors cors;
	
	@Getter
	@Setter
	public static class Paths {
		
		private List<String> publicPaths;
	}
	
	
	@Getter
	@Setter
	public static class Cors {
		
		private List<String> allowedOrigins;
		private List<String> allowedMethods;
		private List<String> allowedHeaders;
	}
	
}

