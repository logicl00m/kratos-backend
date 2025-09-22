package org.kratos.backend.configuration.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.configuration.service.StateTransitionHandler;

import java.util.List;
import java.util.Map;

/**
 * Root record for workflow configuration.
 */
public record WorkflowConfigurationContext(
		@JsonProperty ("id") String id,
		@JsonProperty ("version") String version,
		@JsonProperty ("initialState") String initialState,
		@JsonProperty ("forms") Map<String, Form> forms,
		@JsonProperty ("states") Map<String, State> states,
		@JsonProperty ("scripts") Map<String, Script> scripts
) {
	
	public record Script() {
		// Empty object per schema (but could be extended in future)
	}
	
	public record Form(
			@JsonProperty ("fields") Map<String, Field> fields
	) {
	
	}
	
	public record Field(
			@JsonProperty ("name") String name,
			@JsonProperty ("data") String data,
			@JsonProperty ("actions") List<String> actions,
			@JsonProperty ("kind") Kind kind,
			
			@JsonTypeInfo (
					use = JsonTypeInfo.Id.NAME,
					include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
					property = "kind"
			)
			@JsonSubTypes ({
					@JsonSubTypes.Type (value = TextFieldSpec.class, name = "text"),
					@JsonSubTypes.Type (value = NumberFieldSpec.class, name = "number")
			})
			Spec spec
	) {
		
		public enum Kind {
			@JsonProperty ("text") TEXT,
			@JsonProperty ("number") NUMBER
		}
		
		/**
		 * Marker interface for spec types.
		 * Jackson can be configured with polymorphic deserialization based on 'kind'.
		 */
		public sealed interface Spec permits TextFieldSpec, NumberFieldSpec {
		
		}
		
		public record TextFieldSpec(
				@JsonProperty ("placeholder") String placeholder
		) implements Spec {
		
		}
		
		public record NumberFieldSpec(
				@JsonProperty ("placeholder") String placeholder,
				@JsonProperty ("min") Double min,
				@JsonProperty ("max") Double max
		) implements Spec {
		
		}
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
			@JsonProperty ("simple") SIMPLE(true, null),
			@JsonProperty ("switch") SWITCH(false, null),
			@JsonProperty ("allOf") ALL_OF(true, null),
			@JsonProperty ("anyOf") ANY_OF(true, null),
			@JsonProperty ("doWhile") DO_WHILE(true, null);
			
			public final boolean requiresExternalIntervention;
			public final StateTransitionHandler handler;
			
		}
		
		public sealed interface Spec permits SimpleStateSpec, SwitchStateSpec {
		
		}
		
		public record SimpleStateSpec(
				@JsonProperty ("forms") List<FormRef> forms,
				@JsonProperty ("actions") Map<String, SimpleActionSpec> actions
		) implements Spec {
		
		}
		
		public record FormRef(@JsonProperty ("id") String id) {
		
		}
		
		public record SimpleActionSpec(
				@JsonProperty ("name") String name,
				@JsonProperty ("nextState") String nextState,
				@JsonProperty ("validation") String validation,
				@JsonProperty ("operation") String operation
		) {
		
		}
		
		public record SwitchStateSpec(
				@JsonProperty ("expression") String expression,
				@JsonProperty ("operation") String operation
		) implements Spec {
		
		}
	}
}

