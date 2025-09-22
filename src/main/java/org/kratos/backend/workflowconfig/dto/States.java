package org.kratos.backend.workflowconfig.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashMap;
import java.util.Map;

public class States {

    @NotBlank(message = "Initial state is required")
    @JsonProperty("initialState")
    private String initialState;

    @NotNull(message = "State definitions cannot be null")
    @Size(min = 1, message = "At least one state definition is required")
    @Valid
    private final Map<String, State> definitions = new LinkedHashMap<>();

    public States() {}

    public States(String initialState, Map<String, State> definitions) {
        this.initialState = initialState;
        if (definitions != null) {
            this.definitions.putAll(definitions);
        }
    }

    @JsonProperty("initialState")
    public String getInitialState() {
        return initialState;
    }

    @JsonProperty("initialState")
    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    @JsonAnySetter
    public void setStateDefinition(String key, State state) {
        if (!"initialState".equals(key) && state != null) {
            this.definitions.put(key, state);
        }
    }

    @JsonAnyGetter
    public Map<String, State> getDefinitions() {
        return definitions;
    }
}
