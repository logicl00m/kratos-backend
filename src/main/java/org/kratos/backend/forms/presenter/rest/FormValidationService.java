package org.kratos.backend.forms.presenter.rest;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
public class FormValidationService {

    /**
     * Validates a form field based on its validation rules
     * @param field The form field to validate
     * @param value The value to validate
     * @return true if valid, false otherwise
     */
    public boolean validateField(FormFieldDTO field, Object value) {
        if (field.validation() == null) {
            return true; // No validation rules
        }

        FormValidationDTO validation = field.validation();

        // Check required field
        if (validation.required() != null && validation.required()) {
            if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                return false;
            }
        }

        // Skip further validation if value is null or empty
        if (value == null || (value instanceof String && ((String) value).isEmpty())) {
            return true;
        }

        // Type-specific validations
        switch (field.type()) {
            case "text":
                return validateText(validation, (String) value);
            case "number":
                return validateNumber(validation, value);
            case "date":
                return validateDate(validation, (String) value);
            default:
                // For other types, just check general constraints
                if (value instanceof String) {
                    return validateText(validation, (String) value);
                }
                return true;
        }
    }

    private boolean validateText(FormValidationDTO validation, String value) {
        // Check regex
        if (validation.regex() != null && !validation.regex().isEmpty()) {
            try {
                if (!Pattern.matches(validation.regex(), value)) {
                    return false;
                }
            } catch (PatternSyntaxException e) {
                // Invalid regex pattern, skip validation
                return true;
            }
        }

        // Check min/max length
        if (validation.min() != null) {
            if (value.length() < validation.min().intValue()) {
                return false;
            }
        }

        if (validation.max() != null) {
            if (value.length() > validation.max().intValue()) {
                return false;
            }
        }

        return true;
    }

    private boolean validateNumber(FormValidationDTO validation, Object value) {
        double numValue;
        if (value instanceof Number) {
            numValue = ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                numValue = Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return false; // Not a valid number
            }
        } else {
            return false; // Not a number
        }

        // Check min/max
        if (validation.min() != null) {
            if (numValue < validation.min().doubleValue()) {
                return false;
            }
        }

        if (validation.max() != null) {
            if (numValue > validation.max().doubleValue()) {
                return false;
            }
        }

        return true;
    }

    private boolean validateDate(FormValidationDTO validation, String value) {
        // Basic date format validation (YYYY-MM-DD)
        if (!value.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        // Additional validation can be added here if needed
        return true;
    }

    /**
     * Validates an entire form
     * @param formSections The form sections to validate
     * @param formData The form data to validate against
     * @return true if valid, false otherwise
     */
    public boolean validateForm(Map<String, FormSection> formSections, Map<String, Object> formData) {
        for (Map.Entry<String, FormSection> entry : formSections.entrySet()) {
            FormSection section = entry.getValue();
            for (FormFieldDTO field : section.fields()) {
                Object value = formData.get(field.id());
                if (!validateField(field, value)) {
                    return false;
                }
            }
        }
        return true;
    }
}