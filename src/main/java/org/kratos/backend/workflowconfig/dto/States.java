package org.kratos.backend.workflowconfig.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedHashMap;
import java.util.Map;

public class States {
    private String initialState;
    private final Map<String, State> definitions = new LinkedHashMap<>();

    public States() {}

    public States(String initialState, Map<String, State> definitions) {
        this.initialState = initialState;
        if (definitions != null) this.definitions.putAll(definitions);
    }

    @JsonCreator
    public States(JsonNode node) {
        if (node == null || !node.isObject()) return;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = (ObjectNode) node;
        obj.fieldNames().forEachRemaining(field -> {
            JsonNode val = obj.get(field);
            if ("initialState".equals(field) && val != null && val.isTextual()) {
                this.initialState = val.asText();
            } else {
                try {
                    State s = mapper.treeToValue(val, State.class);
                    definitions.put(field, s);
                } catch (Exception e) {
                    // ignore malformed state - schema validation will catch
                }
            }
        });
    }

    public String getInitialState() { return initialState; }
    public Map<String, State> getDefinitions() { return definitions; }
}

