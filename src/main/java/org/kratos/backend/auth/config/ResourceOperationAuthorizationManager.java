package org.kratos.backend.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kratos.backend.auth.dto.ResourceOperation;
import org.kratos.backend.auth.dto.ResourceOperationScope;
import org.kratos.backend.auth.service.ResourceOperationService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Component
@Slf4j
public class ResourceOperationAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
	
	private final ResourceOperationService resourceOperationService;
	
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		
		if (authentication == null || authentication.get() == null) {
			log.info("no authentication found");
			return new AuthorizationDecision(false);
		}
		Authentication auth = authentication.get();
		
		// get user id
		String userId = auth.getName();
		
		// get resource operation
		HttpServletRequest request = context.getRequest();
		ResourceOperationScope resourceOperationScope = getResourceOperation(request);
		
		// get the subject
		UUID subject = getSubject(request);
		
		// check permission
		boolean hasPermission = resourceOperationService.checkPermission(userId, subject, resourceOperationScope);
		
		log.info("user: {} | resource: {} | subject: {} | permission: {}",
		         userId,
		         resourceOperationScope,
		         subject,
		         hasPermission);
		
		return new AuthorizationDecision(hasPermission);
	}
	
	private ResourceOperationScope getResourceOperation(HttpServletRequest request) {
		// current pattern is /api/{version}/{scope}/{access-level}/{resource}/{operation}
		
		String path = request.getRequestURI();
		String[] pathElements = path.split("/");
		String scope = pathElements[3];
		String resource = pathElements[5];
		String operation = pathElements[6];
		
		return new ResourceOperationScope(new ResourceOperation(resource, operation), scope);
	}
	
	private UUID getSubject(HttpServletRequest request) {
		// fixme: exception handling
		// currently the subject is in request header X-Subject
		String subjectHeader = request.getHeader("X-Subject");
		return UUID.fromString(subjectHeader);
	}
	
}
