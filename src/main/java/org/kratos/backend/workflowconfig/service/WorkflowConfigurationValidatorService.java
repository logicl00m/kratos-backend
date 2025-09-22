package org.kratos.backend.workflowconfig.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.kratos.backend.workflowconfig.dto.*;
import org.kratos.backend.workflowconfig.dto.WorkflowValidationErrorResponse.WorkflowValidationError;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkflowConfigurationValidatorService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JsonSchema schema;

    public WorkflowConfigurationValidatorService() {
        try (InputStream schemaStream = getClass().getResourceAsStream("/schemas/v1.configuration.schema.json")) {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
            this.schema = factory.getSchema(schemaStream);
        } catch (Exception e) {
            throw new RuntimeException("Could not load workflow configuration schema", e);
        }
    }

    public WorkflowValidationErrorResponse validateConfiguration(WorkflowConfiguration configuration) {
        try {
            JsonNode jsonNode = objectMapper.valueToTree(configuration);
            Set<ValidationMessage> schemaErrors = schema.validate(jsonNode);

            List<WorkflowValidationError> errors = new ArrayList<>();

            // Add schema validation errors with enhanced messages
            errors.addAll(processSchemaErrors(schemaErrors, jsonNode));

            // Add custom business logic validations
            errors.addAll(performCustomValidations(configuration));

            boolean isValid = errors.isEmpty();
            String message = isValid ? "Workflow configuration is valid" :
                           String.format("Workflow configuration contains %d error(s)", errors.size());

            return new WorkflowValidationErrorResponse(isValid, message, errors);

        } catch (Exception e) {
            List<WorkflowValidationError> errors = List.of(
                new WorkflowValidationError(
                    "root",
                    "INVALID_JSON",
                    "Invalid JSON structure: " + e.getMessage(),
                    "Ensure the configuration is valid JSON format",
                    null
                )
            );
            return new WorkflowValidationErrorResponse(false, "Invalid JSON structure", errors);
        }
    }

    private List<WorkflowValidationError> processSchemaErrors(Set<ValidationMessage> schemaErrors, JsonNode jsonNode) {
        return schemaErrors.stream()
            .map(error -> createEnhancedError(error, jsonNode))
            .collect(Collectors.toList());
    }

    private WorkflowValidationError createEnhancedError(ValidationMessage error, JsonNode jsonNode) {
        String path = error.getInstanceLocation().toString();
        String message = error.getMessage();
        String code = determineErrorCode(error);
        String suggestion = generateSuggestion(error);
        Object invalidValue = getValueAtPath(jsonNode, path);

        return new WorkflowValidationError(path, code, message, suggestion, invalidValue);
    }

    private String determineErrorCode(ValidationMessage error) {
        String keyword = error.getSchemaLocation().toString();
        if (keyword.contains("required")) return "MISSING_REQUIRED_FIELD";
        if (keyword.contains("enum")) return "INVALID_ENUM_VALUE";
        if (keyword.contains("type")) return "INVALID_TYPE";
        if (keyword.contains("minProperties")) return "INSUFFICIENT_PROPERTIES";
        if (keyword.contains("additionalProperties")) return "UNEXPECTED_PROPERTY";
        if (keyword.contains("allOf")) return "CONDITIONAL_VALIDATION_FAILED";
        return "SCHEMA_VIOLATION";
    }

    private String generateSuggestion(ValidationMessage error) {
        String message = error.getMessage();
        String path = error.getInstanceLocation().toString();

        if (message.contains("required")) {
            return "Add the missing required field to your configuration";
        }
        if (message.contains("enum")) {
            return "Use one of the allowed values for this field";
        }
        if (message.contains("type")) {
            return "Check the data type - ensure it matches the expected type";
        }
        if (path.contains("states") && message.contains("minProperties")) {
            return "Add at least one state definition in addition to 'initialState'";
        }
        if (path.contains("forms") && message.contains("fields")) {
            return "Each form must have at least one field defined";
        }
        if (path.contains("actions") && message.contains("minProperties")) {
            return "Each state must have at least one action defined";
        }
        if (message.contains("additionalProperties")) {
            return "Remove unexpected properties or check for typos in property names";
        }

        return "Please review the field value and ensure it meets the schema requirements";
    }

    private Object getValueAtPath(JsonNode root, String path) {
        try {
            if (path == null || path.isEmpty() || "/".equals(path)) return null;
            String[] parts = path.substring(1).split("/");
            JsonNode current = root;
            for (String part : parts) {
                if (current == null) return null;
                if (current.isArray()) {
                    int idx = Integer.parseInt(part);
                    current = current.get(idx);
                } else {
                    current = current.get(part);
                }
            }
            if (current == null || current.isMissingNode()) return null;
            if (current.isValueNode()) return current.asText();
            return objectMapper.treeToValue(current, Object.class);
        } catch (Exception e) {
            return null;
        }
    }

    private List<WorkflowValidationError> performCustomValidations(WorkflowConfiguration config) {
        List<WorkflowValidationError> errors = new ArrayList<>();

        // Validate initial state exists
        errors.addAll(validateInitialStateExists(config));

        // Validate state references in actions
        errors.addAll(validateStateReferences(config));

        // Validate form references in states
        errors.addAll(validateFormReferences(config));

        // Validate field action references
        errors.addAll(validateFieldActionReferences(config));

        return errors;
    }

    private List<WorkflowValidationError> validateInitialStateExists(WorkflowConfiguration config) {
        List<WorkflowValidationError> errors = new ArrayList<>();
        if (config == null || config.states() == null) return errors;
        String initialState = config.states().getInitialState();
        if (initialState != null && !config.states().getDefinitions().containsKey(initialState)) {
            errors.add(new WorkflowValidationError(
                "/states/initialState",
                "INVALID_REFERENCE",
                String.format("Initial state '%s' is not defined in states", initialState),
                "Define the initial state in the states section or change the initialState reference",
                initialState
            ));
        }
        return errors;
    }

    private List<WorkflowValidationError> validateStateReferences(WorkflowConfiguration config) {
        List<WorkflowValidationError> errors = new ArrayList<>();
        if (config == null || config.states() == null) return errors;

        Map<String, State> definitions = config.states().getDefinitions();
        Set<String> definedStates = new HashSet<>(definitions.keySet());

        for (Map.Entry<String, State> entry : definitions.entrySet()) {
            String stateName = entry.getKey();
            State state = entry.getValue();
            if (state == null) continue;
            if (state.spec() instanceof SimpleStateSpec simpleSpec) {
                Map<String, Action> actions = simpleSpec.actions();
                if (actions != null) {
                    for (Action action : actions.values()) {
                        String nextState = action.nextState();
                        if (nextState != null && !definedStates.contains(nextState)) {
                            errors.add(new WorkflowValidationError(
                                String.format("/states/%s/spec/actions/.../nextState", stateName),
                                "INVALID_REFERENCE",
                                String.format("Referenced state '%s' is not defined", nextState),
                                "Define the referenced state or fix the state name",
                                nextState
                            ));
                        }
                    }
                }
            }
        }
        return errors;
    }

    private List<WorkflowValidationError> validateFormReferences(WorkflowConfiguration config) {
        List<WorkflowValidationError> errors = new ArrayList<>();
        if (config == null || config.states() == null) return errors;

        Map<String, Form> forms = config.forms() != null ? config.forms() : Collections.emptyMap();
        Set<String> definedForms = new HashSet<>(forms.keySet());

        for (Map.Entry<String, State> entry : config.states().getDefinitions().entrySet()) {
            String stateName = entry.getKey();
            State state = entry.getValue();
            if (state == null) continue;
            if (state.spec() instanceof SimpleStateSpec simpleSpec) {
                List<FormRef> stateForms = simpleSpec.forms();
                if (stateForms != null) {
                    for (FormRef ref : stateForms) {
                        String formId = ref.id();
                        if (formId != null && !definedForms.contains(formId)) {
                            errors.add(new WorkflowValidationError(
                                String.format("/states/%s/spec/forms/.../id", stateName),
                                "INVALID_REFERENCE",
                                String.format("Referenced form '%s' is not defined", formId),
                                "Define the referenced form in the forms section",
                                formId
                            ));
                        }
                    }
                }
            }
        }

        return errors;
    }

    private List<WorkflowValidationError> validateFieldActionReferences(WorkflowConfiguration config) {
        List<WorkflowValidationError> errors = new ArrayList<>();
        if (config == null) return errors;

        // Collect all available actions per state
        Map<String, Set<String>> stateActions = new HashMap<>();
        if (config.states() != null) {
            for (Map.Entry<String, State> entry : config.states().getDefinitions().entrySet()) {
                String stateName = entry.getKey();
                State state = entry.getValue();
                if (state != null && state.spec() instanceof SimpleStateSpec simpleSpec) {
                    Map<String, Action> actions = simpleSpec.actions();
                    if (actions != null) stateActions.put(stateName, actions.keySet());
                }
            }
        }

        Map<String, Form> forms = config.forms() != null ? config.forms() : Collections.emptyMap();
        for (Map.Entry<String, Form> formEntry : forms.entrySet()) {
            String formName = formEntry.getKey();
            Form form = formEntry.getValue();
            if (form == null || form.fields() == null) continue;
            for (Map.Entry<String, Field> fieldEntry : form.fields().entrySet()) {
                String fieldName = fieldEntry.getKey();
                Field field = fieldEntry.getValue();
                List<String> fieldActions = field.actions();
                if (fieldActions == null) continue;

                for (String actionRef : fieldActions) {
                    boolean found = stateActions.values().stream().anyMatch(set -> set.contains(actionRef));
                    if (!found) {
                        errors.add(new WorkflowValidationError(
                            String.format("/forms/%s/fields/%s/actions", formName, fieldName),
                            "INVALID_REFERENCE",
                            String.format("Field action '%s' does not reference any known action in states", actionRef),
                            "Ensure the action is defined in at least one state's actions or remove the reference",
                            actionRef
                        ));
                    }
                }
            }
        }

        return errors;
    }
}
