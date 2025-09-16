package org.kratos.backend.docs;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfiguration {
	
	@Value ("${auth.oauth2.resource-server}")
	private String resourceServer;
	
	@Value ("${auth.oauth2.realm}")
	private String realm;
	
	@Value ("${auth.headers.subject}")
	private String subjectHeader;
	
	@Value ("${springdoc.swagger-ui.schemes-names.oauth2}")
	private String oauth2SchemeName;
	
	@Value ("${springdoc.swagger-ui.schemes-names.x-subject}")
	private String xSubjectSchemeName;
	
	@Value ("${server.url}")
	private String serverUrl;
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.servers(List.of(new Server().url(serverUrl)))
				.components(new Components()
						            .addSecuritySchemes(oauth2SchemeName, createOAuthScheme())
						            .addSecuritySchemes(xSubjectSchemeName, createXSubjectScheme()));
	}
	
	private SecurityScheme createOAuthScheme() {
		OAuthFlows flows = createOAuthFlows();
		return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
		                           .flows(flows);
	}
	
	private OAuthFlows createOAuthFlows() {
		OAuthFlow flow = createAuthorizationCodeFlow();
		return new OAuthFlows().authorizationCode(flow);
	}
	
	private OAuthFlow createAuthorizationCodeFlow() {
		return new OAuthFlow()
				.authorizationUrl(String.format("%s/realms/%s/protocol/openid-connect/auth", resourceServer, realm))
				.tokenUrl(String.format("%s/realms/%s/protocol/openid-connect/token", resourceServer, realm));
	}
	
	private SecurityScheme createXSubjectScheme() {
		return new SecurityScheme()
				.type(SecurityScheme.Type.APIKEY)
				.name(subjectHeader)
				.in(SecurityScheme.In.HEADER);
	}
	
	@Bean
	public OpenApiCustomizer securePrivateEndpointsCustomizer() {
		return openApi -> {
			Paths paths = openApi.getPaths();
			if (paths != null) {
				paths.forEach((path, pathItem) -> {
					if (path.matches("^/api/v1/.*/private/.*")) {
						addSecurityItem(pathItem, oauth2SchemeName);
						addParametersItemIfNotExists(pathItem, getXSubjectHeader());
					}
				});
			}
		};
	}
	
	private void addSecurityItem(PathItem pathItem, String schemeName) {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(schemeName);
		
		if (pathItem.getPost() != null) {
			pathItem.getPost()
			        .addSecurityItem(securityRequirement);
		}
	}
	
	private Parameter getXSubjectHeader() {
		return new Parameter()
				.in(ParameterIn.HEADER.toString())
				.required(true)
				.name(subjectHeader)
				.description("subject identifier");
	}
	
	private void addParametersItemIfNotExists(PathItem pathItem, Parameter parameter) {
		Operation post = pathItem.getPost();
		if (post == null) {
			return;
		}
		if (post.getParameters() == null) {
			post.addParametersItem(parameter);
			return;
		}
		if (post.getParameters()
		        .stream()
		        .noneMatch(p -> p.getName()
		                         .equals(parameter.getName()))) {
			post.addParametersItem(parameter);
		}
	}
}

