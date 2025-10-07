package org.kratos.backend.configuration.dto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.Kind;
import org.kratos.backend.configuration.service.impl.SimpleStateTransitionHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateTransitionRegistry {
	
	private final SimpleStateTransitionHandler SIMPLE;
	
	@PostConstruct
	public void init() {
		Kind.SIMPLE.handler = SIMPLE;
	}
	
}
