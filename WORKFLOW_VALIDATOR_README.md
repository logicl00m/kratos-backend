# Workflow Configuration Validator

## Overview

The Workflow Configuration Validator is a comprehensive validation system that validates workflow configuration JSON against the v1.configuration.schema.json schema. It provides detailed, human-readable error messages to help developers quickly identify and fix configuration issues.

## Features

- **Schema Validation**: Validates against the JSON schema with detailed error reporting
- **Business Logic Validation**: Additional custom validations for workflow-specific rules
- **Reference Validation**: Validates that all state, form, and action references exist
- **User-Friendly Error Messages**: Clear, actionable error messages with suggestions
- **REST API Endpoints**: Easy-to-use validation endpoints

## API Endpoints

### 1. Validate with Request Wrapper
```
POST /api/v1/workflow-config/validate
Content-Type: application/json

{
  "configuration": {
    // Your workflow configuration here
  }
}
```

### 2. Validate Raw Configuration
```
POST /api/v1/workflow-config/validate-raw
Content-Type: application/json

{
  // Your workflow configuration directly here
}
```

## Response Format

### Success Response
```json
{
  "status": "SUCCESS",
  "message": "Workflow configuration is valid",
  "data": {
    "valid": true,
    "message": "Workflow configuration is valid",
    "errors": []
  }
}
```

### Error Response
```json
{
  "status": "FAILURE",
  "code": "VALIDATION_FAILED",
  "message": "Workflow configuration contains 3 error(s)",
  "data": {
    "valid": false,
    "message": "Workflow configuration contains 3 error(s)",
    "errors": [
      {
        "field": "/states/initialState",
        "code": "INVALID_REFERENCE",
        "message": "Initial state 'nonExistentState' is not defined in states",
        "suggestion": "Define the initial state in the states section or change the initialState reference",
        "invalidValue": "nonExistentState"
      },
      {
        "field": "/forms/userForm/fields/firstName/kind",
        "code": "INVALID_ENUM_VALUE",
        "message": "invalidType is not a valid enum value",
        "suggestion": "Use one of the allowed values for this field",
        "invalidValue": "invalidType"
      },
      {
        "field": "/states/userInput/spec/forms/.../id",
        "code": "INVALID_REFERENCE", 
        "message": "Referenced form 'nonExistentForm' is not defined",
        "suggestion": "Define the referenced form in the forms section",
        "invalidValue": "nonExistentForm"
      }
    ]
  }
}
```

## Error Codes

| Code | Description | Common Causes |
|------|-------------|---------------|
| `MISSING_REQUIRED_FIELD` | Required field is missing | Missing id, version, or other required properties |
| `INVALID_ENUM_VALUE` | Invalid enumeration value | Wrong field kind, state kind, etc. |
| `INVALID_TYPE` | Wrong data type | String instead of number, object instead of array |
| `INSUFFICIENT_PROPERTIES` | Too few properties | Not enough states, fields, or actions |
| `UNEXPECTED_PROPERTY` | Property not allowed | Typos in property names or extra properties |
| `INVALID_REFERENCE` | Referenced item doesn't exist | State, form, or action references that don't exist |
| `CONDITIONAL_VALIDATION_FAILED` | Conditional schema failed | Field spec doesn't match field kind |

## Validation Rules

### Schema Validation
- Validates against v1.configuration.schema.json
- Checks required fields: `id`, `version`
- Validates field types and enum values
- Ensures proper structure for forms, states, and scripts

### Business Logic Validation
1. **Initial State Exists**: The `initialState` must reference an actual state
2. **State References**: All `nextState` references in actions must exist
3. **Form References**: All form IDs referenced in states must be defined
4. **Field Action References**: Field actions should reference valid state actions (context-dependent)

### Custom Validations
- States must have at least one non-initialState entry
- Forms must have at least one field
- Simple states must have at least one action
- Field specs must match their kind (text fields need text.field.spec, etc.)

## Example Usage

### Valid Configuration
```json
{
  "id": "sample-workflow",
  "version": "1.0.0",
  "forms": {
    "userForm": {
      "fields": {
        "firstName": {
          "name": "First Name",
          "data": "firstName", 
          "kind": "text",
          "spec": {
            "placeholder": "Enter your first name"
          }
        }
      }
    }
  },
  "states": {
    "initialState": "userInput",
    "userInput": {
      "name": "User Input State",
      "kind": "simple",
      "spec": {
        "forms": [{"id": "userForm"}],
        "actions": {
          "submit": {
            "name": "Submit",
            "nextState": "completed"
          }
        }
      }
    },
    "completed": {
      "name": "Completed State",
      "kind": "switch",
      "spec": {
        "expression": "true"
      }
    }
  }
}
```

### Testing the Validator

You can test the validator using curl:

```bash
# Test valid configuration
curl -X POST http://localhost:8080/api/v1/workflow-config/validate-raw \
  -H "Content-Type: application/json" \
  -d @valid-workflow.json

# Test invalid configuration  
curl -X POST http://localhost:8080/api/v1/workflow-config/validate-raw \
  -H "Content-Type: application/json" \
  -d @invalid-workflow.json
```

## Integration

### Using in Your Service
```java
@Autowired
private WorkflowConfigurationValidatorService validator;

public void processWorkflow(Map<String, Object> config) {
    WorkflowValidationErrorResponse result = validator.validateConfiguration(config);
    
    if (!result.isValid()) {
        // Handle validation errors
        result.getErrors().forEach(error -> {
            log.error("Validation error in field {}: {}", 
                     error.getField(), error.getMessage());
        });
        throw new ValidationException("Invalid workflow configuration");
    }
    
    // Process valid configuration
    processValidWorkflow(config);
}
```

### Custom Validation Annotations
The system also supports using the `@ValidJsonSchema` annotation for automatic validation:

```java
public class WorkflowConfigurationRequest {
    @NotNull
    @ValidJsonSchema(schema = "/schemas/v1.configuration.schema.json")
    private Map<String, Object> configuration;
    
    // getters and setters
}
```

## Configuration Files

The validator uses the schema located at:
- `src/main/resources/schemas/v1.configuration.schema.json`

Example configurations are available at:
- `src/main/resources/schemas/examples/valid-workflow.json`
- `src/main/resources/schemas/examples/invalid-workflow.json`

## Error Message Suggestions

The validator provides helpful suggestions for common errors:

- **Missing required fields**: "Add the missing required field to your configuration"
- **Invalid enum values**: "Use one of the allowed values for this field"
- **Type mismatches**: "Check the data type - ensure it matches the expected type"
- **Reference errors**: "Define the referenced [item] in the [section] section"
- **Insufficient properties**: "Add at least one [item] definition"

This makes it easy for developers to understand what went wrong and how to fix it.
