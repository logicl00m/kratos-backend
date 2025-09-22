package org.kratos.backend.common.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.InputStream;
import java.util.Set;

public class JsonSchemaValidator implements ConstraintValidator<ValidJsonSchema, Object> {

    private final ObjectMapper mapper = new ObjectMapper();
    private JsonSchema schema;

    @Override
    public void initialize(ValidJsonSchema annotation) {
        try (InputStream schemaStream = getClass().getResourceAsStream(annotation.schema())) {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
            schema = factory.getSchema(schemaStream);
        } catch (Exception e) {
            throw new RuntimeException("Could not load schema: " + annotation.schema(), e);
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            JsonNode jsonNode = mapper.valueToTree(value);
            Set<ValidationMessage> errors = schema.validate(jsonNode);

            if (!errors.isEmpty()) {
                context.disableDefaultConstraintViolation();

                for (ValidationMessage error : errors) {
                    String fieldPath = error.getInstanceLocation()
                                            .toString();
                    context.buildConstraintViolationWithTemplate(error.getMessage())
                           .addPropertyNode(fieldPath)
                           .addConstraintViolation();
                }
                return false;
            }

            return true;
        } catch (Exception e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid JSON structure")
                   .addConstraintViolation();
            return false;
        }
    }
}
