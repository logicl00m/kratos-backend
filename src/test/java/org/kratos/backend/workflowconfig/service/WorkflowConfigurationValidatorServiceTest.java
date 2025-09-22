package org.kratos.backend.workflowconfig.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kratos.backend.workflowconfig.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkflowConfigurationValidatorServiceTest {

    private WorkflowConfigurationValidatorService validator;

    @BeforeEach
    void setUp() {
        validator = new WorkflowConfigurationValidatorService();
    }

    @Test
    void testValidConfiguration() {
        WorkflowConfiguration config = createValidConfiguration();

        WorkflowValidationErrorResponse result = validator.validateConfiguration(config);

        assertTrue(result.valid());
        assertEquals("Workflow configuration is valid", result.message());
        assertTrue(result.errors().isEmpty());
    }

    @Test
    void testMissingRequiredFields() {
        // Missing id and version
        WorkflowConfiguration config = new WorkflowConfiguration(null, null, null, new States(), null);

        WorkflowValidationErrorResponse result = validator.validateConfiguration(config);

        assertFalse(result.valid());
        assertFalse(result.errors().isEmpty());

        // Should have errors for missing id and version
        assertTrue(result.errors().stream().anyMatch(e -> e.field() != null && e.field().contains("id")));
        assertTrue(result.errors().stream().anyMatch(e -> e.field() != null && e.field().contains("version")));
    }

    @Test
    void testInvalidInitialStateReference() {
        WorkflowConfiguration config = createValidConfiguration();

        // Set initial state to non-existent state
        States states = config.states();
        // create new states with bad initialState
        States badStates = new States("nonExistentState", states.getDefinitions());
        WorkflowConfiguration badConfig = new WorkflowConfiguration(config.id(), config.version(), config.forms(), badStates, config.scripts());

        WorkflowValidationErrorResponse result = validator.validateConfiguration(badConfig);

        assertFalse(result.valid());
        assertTrue(result.errors().stream()
            .anyMatch(e -> "INVALID_REFERENCE".equals(e.code()) && e.message().contains("nonExistentState")));
    }

    @Test
    void testInvalidStateReference() {
        WorkflowConfiguration config = createValidConfiguration();

        // Add an action that references a non-existent state
        Map<String, State> defs = new HashMap<>(config.states().getDefinitions());
        State userInput = defs.get("userInput");
        SimpleStateSpec spec = (SimpleStateSpec) userInput.spec();
        Map<String, Action> actions = new HashMap<>(spec.actions());
        actions.put("invalidAction", new Action("Invalid Action", "nonExistentState", null, null));
        SimpleStateSpec newSpec = new SimpleStateSpec(spec.forms(), actions);
        State newUserInput = new State(userInput.name(), userInput.kind(), newSpec);
        defs.put("userInput", newUserInput);
        States newStates = new States(config.states().getInitialState(), defs);
        WorkflowConfiguration newConfig = new WorkflowConfiguration(config.id(), config.version(), config.forms(), newStates, config.scripts());

        WorkflowValidationErrorResponse result = validator.validateConfiguration(newConfig);

        assertFalse(result.valid());
        assertTrue(result.errors().stream()
            .anyMatch(e -> "INVALID_REFERENCE".equals(e.code()) && e.message().contains("nonExistentState")));
    }

    @Test
    void testInvalidFormReference() {
        WorkflowConfiguration config = createValidConfiguration();

        // Reference a non-existent form
        Map<String, State> defs = new HashMap<>(config.states().getDefinitions());
        State userInput = defs.get("userInput");
        SimpleStateSpec spec = (SimpleStateSpec) userInput.spec();
        List<FormRef> formsList = spec.forms().stream().toList();
        // add invalid form ref
        List<FormRef> newForms = new java.util.ArrayList<>(formsList);
        newForms.add(new FormRef("nonExistentForm"));
        SimpleStateSpec newSpec = new SimpleStateSpec(newForms, spec.actions());
        State newUserInput = new State(userInput.name(), userInput.kind(), newSpec);
        defs.put("userInput", newUserInput);
        States newStates = new States(config.states().getInitialState(), defs);
        WorkflowConfiguration newConfig = new WorkflowConfiguration(config.id(), config.version(), config.forms(), newStates, config.scripts());

        WorkflowValidationErrorResponse result = validator.validateConfiguration(newConfig);

        assertFalse(result.valid());
        assertTrue(result.errors().stream()
            .anyMatch(e -> "INVALID_REFERENCE".equals(e.code()) && e.message().contains("nonExistentForm")));
    }

    @Test
    void testInvalidFieldType() {
        WorkflowConfiguration config = createValidConfiguration();

        // Add field with invalid kind
        Map<String, Form> forms = new HashMap<>(config.forms());
        Form userForm = forms.get("userForm");
        Map<String, Field> fields = new HashMap<>(userForm.fields());

        Field invalidField = new Field("Invalid Field", "invalidData", null, "invalidType", new TextFieldSpec("test"));
        fields.put("invalidField", invalidField);
        Form newUserForm = new Form(fields);
        forms.put("userForm", newUserForm);

        WorkflowConfiguration newConfig = new WorkflowConfiguration(config.id(), config.version(), forms, config.states(), config.scripts());

        WorkflowValidationErrorResponse result = validator.validateConfiguration(newConfig);

        assertFalse(result.valid());
        assertTrue(result.errors().stream().anyMatch(e -> "INVALID_ENUM_VALUE".equals(e.code())));
    }

    private WorkflowConfiguration createValidConfiguration() {
        // Forms
        Map<String, Field> userFields = new HashMap<>();
        Field nameField = new Field("Name", "userName", null, "text", new TextFieldSpec("Enter name"));
        userFields.put("name", nameField);
        Form userForm = new Form(userFields);
        Map<String, Form> forms = new HashMap<>();
        forms.put("userForm", userForm);

        // States
        Map<String, Action> actions = new HashMap<>();
        actions.put("submit", new Action("Submit", "completed", null, null));

        SimpleStateSpec userSpec = new SimpleStateSpec(List.of(new FormRef("userForm")), actions);
        State userInput = new State("User Input", "simple", userSpec);

        Map<String, State> stateDefs = new HashMap<>();
        stateDefs.put("userInput", userInput);

        Map<String, Object> completedProps = new HashMap<>();
        SwitchStateSpec completedSpec = new SwitchStateSpec("true", null);
        State completed = new State("Completed", "switch", completedSpec);
        stateDefs.put("completed", completed);

        States states = new States("userInput", stateDefs);

        return new WorkflowConfiguration("test-workflow", "1.0.0", forms, states, null);
    }
}
