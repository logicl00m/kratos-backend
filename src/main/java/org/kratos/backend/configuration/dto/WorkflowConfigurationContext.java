package org.kratos.backend.configuration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.configuration.service.Executor;
import org.kratos.backend.configuration.service.StateTransitionHandler;

import java.util.List;
import java.util.Map;

public record WorkflowConfigurationContext(
		@JsonProperty ("id") String id,
		@JsonProperty ("version") String version,
		@JsonProperty ("initialState") String initialState,
		@JsonProperty ("forms") Map<String, Object> forms,
		@JsonProperty ("states") Map<String, State> states,
		@JsonProperty ("scripts") Map<String, Script> scripts
) {
	
	public record Script(
			@JsonProperty ("name") String name,
			@JsonProperty ("runtime") String runtime,
			@JsonProperty ("code") String code
	) {
	
	}
	
	public record State(
			@JsonProperty ("name") String name,
			@JsonProperty ("kind") Kind kind,
			@JsonTypeInfo (
					use = JsonTypeInfo.Id.NAME,
					include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
					property = "kind"
			)
			@JsonSubTypes ({
					@JsonSubTypes.Type (value = SimpleStateSpec.class, name = "simple"),
					@JsonSubTypes.Type (value = SwitchStateSpec.class, name = "switch")
			})
			Spec spec
	) {
		
		@RequiredArgsConstructor
		public enum Kind {
			@JsonProperty ("simple") SIMPLE(true),
			@JsonProperty ("switch") SWITCH(false),
			@JsonProperty ("allOf") ALL_OF(true),
			@JsonProperty ("anyOf") ANY_OF(true),
			@JsonProperty ("doWhile") DO_WHILE(true);
			
			public final boolean requiresExternalIntervention;
			
			@Getter
			StateTransitionHandler handler;
		}
		
		public interface Spec {
		
		}
		
		public record SimpleStateSpec(
				@JsonProperty ("forms") List<FormRef> forms,
				@JsonProperty ("actions") Map<String, SimpleActionSpec> actions
		) implements Spec {
		
		}
		
		public record FormRef(@JsonProperty ("id") String id) {
		
		}
		
		public interface ExecutionSpec {
		
		}
		
		@Getter
		public enum Executors {
			@JsonProperty ("inline") INLINE,
			@JsonProperty ("script") SCRIPT;
			
			Executor handler;
		}
		
		public record Execution(
				@JsonProperty ("kind") Executors kind,
				
				@JsonTypeInfo (
						use = JsonTypeInfo.Id.NAME,
						include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
						property = "kind"
				)
				@JsonSubTypes ({
						@JsonSubTypes.Type (value = InlineExecutionSpec.class, name = "inline"),
						@JsonSubTypes.Type (value = ScriptExecutionSpec.class, name = "script")
				})
				ExecutionSpec spec
		) {
		
		}
		
		public record SimpleActionSpec(
				@JsonProperty ("name") String name,
				@JsonProperty ("nextState") String nextState,
				@JsonProperty ("validation") Execution validation,
				@JsonProperty ("operation") Execution operation
		) {
		
		}
		
		public record SwitchStateSpec(
				@JsonProperty ("expression") Execution expression,
				@JsonProperty ("operation") Execution operation,
				@JsonProperty ("nextStates") List<String> nextStates
		) {
		
		}
		
		public record InlineExecutionSpec(
				@JsonProperty ("runtime") String runtime,
				@JsonProperty ("code") String code
		) implements ExecutionSpec {
		
		}
		
		public record ScriptExecutionSpec(
				@JsonProperty ("script") String script,
				@JsonProperty ("function") String function
		) implements ExecutionSpec {
		
		}
	}
}
