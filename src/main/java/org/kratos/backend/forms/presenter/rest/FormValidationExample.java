package org.kratos.backend.forms.presenter.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormValidationExample {
    
    public static void main(String[] args) {
        // Create form fields as in validForm.json
        FormFieldDTO legalNameField = new FormFieldDTO(
            "borrower_legal_name",
            "Legal Name",
            "text",
            "default",
            "{{ data.borrower.legalName }}",
            Arrays.asList("save", "validate"),
            new FormValidationDTO(true, 2, 100, null), // Required, min 2 chars, max 100
            null,
            null
        );
        
        FormFieldDTO birthdateField = new FormFieldDTO(
            "borrower_birthdate",
            "Birthdate",
            "date",
            "default",
            "{{ data.borrower.birthdate }}",
            Arrays.asList("save", "validate"),
            new FormValidationDTO(true, null, null, null), // Required
            null,
            null
        );
        
        FormFieldDTO documentField = new FormFieldDTO(
            "borrower_document",
            "Document Upload",
            "file",
            "default",
            "{{ data.borrower.documents[0] }}",
            Arrays.asList("upload", "replace", "validate"),
            new FormValidationDTO(true, null, null, null), // Required
            null,
            null
        );
        
        // Create a section
        FormSection section = new FormSection(Arrays.asList(legalNameField, birthdateField, documentField));
        
        // Create form sections map
        Map<String, FormSection> formSections = new HashMap<>();
        formSections.put("applicationCore", section);
        
        // Create form data to validate
        Map<String, Object> formData = new HashMap<>();
        formData.put("borrower_legal_name", "John Doe");
        formData.put("borrower_birthdate", "1990-01-01");
        formData.put("borrower_document", "document.pdf");
        
        // In a real implementation, you would inject this service
        FormValidationService validationService = new FormValidationService();
        
        // Validate the form
        boolean isValid = validationService.validateForm(formSections, formData);
        
        System.out.println("Form is valid: " + isValid);
        
        // Test with invalid data
        Map<String, Object> invalidFormData = new HashMap<>();
        invalidFormData.put("borrower_legal_name", ""); // Empty required field
        invalidFormData.put("borrower_birthdate", "1990-01-01");
        invalidFormData.put("borrower_document", "document.pdf");
        
        boolean isInvalid = validationService.validateForm(formSections, invalidFormData);
        System.out.println("Form with empty name is valid: " + isInvalid);
    }
}