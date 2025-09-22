package org.kratos.backend.forms.presenter.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.common.constants.ResponseStatus;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v0/client/private/form")
@RequiredArgsConstructor
@Validated
public class FormDummyResource {
    
    private final FormValidationService validationService;

    @GetMapping
    public Response<List<FormListItem>> getAllForms() {
        //get and return all forms
        List<FormListItem> forms = new ArrayList<>();
        forms.add(new FormListItem("1", "Sample Form", 1, "2023-01-01"));
        return Response.<List<FormListItem>>builder()
                .status(ResponseStatus.ALL_OK)
                .data(forms)
                .build();
    }

    @PostMapping("/create")
    public Response<FormDTO> createForm(@Valid @RequestBody FormCreateRequest request) {
        //create and return the form
        Map<String, FormSection> json = request.json();
        FormDTO form = new FormDTO("1", request.name(), 1, json);
        return Response.<FormDTO>builder()
                .status(ResponseStatus.ALL_OK)
                .data(form)
                .build();
    }

    @PostMapping("/update")
    public Response<FormDTO> updateForm(@Valid @RequestBody FormUpdateRequest request) {
        //update and return the form
        Map<String, FormSection> json = request.json();
        FormDTO form = new FormDTO("1", request.name(), request.bumpVersion() != null && request.bumpVersion() ? 2 : 1, json);
        return Response.<FormDTO>builder()
                .status(ResponseStatus.ALL_OK)
                .data(form)
                .build();
    }

    @DeleteMapping("/{formId}")
    public Response<Void> deleteForm(@PathVariable String formId) {
        //delete the form
        return Response.<Void>builder()
                .status(ResponseStatus.ALL_OK)
                .build();
    }
    
    @GetMapping("/{formId}")
    public Response<FormDTO> getForm(@PathVariable String formId) {
        //get and return a specific form
        Map<String, FormSection> json = new HashMap<>();
        FormDTO form = new FormDTO("1", "Sample Form", 1, json);
        return Response.<FormDTO>builder()
                .status(ResponseStatus.ALL_OK)
                .data(form)
                .build();
    }
    
    @PostMapping("/{formId}/validate")
    public Response<Map<String, Object>> validateForm(
            @PathVariable String formId, 
            @RequestBody Map<String, Object> formData) {
        // In a real implementation, you would retrieve the form by ID
        // For this dummy implementation, we'll just return success
        Map<String, Object> result = new HashMap<>();
        result.put("valid", true);
        result.put("errors", new ArrayList<>());
        return Response.<Map<String, Object>>builder()
                .status(ResponseStatus.ALL_OK)
                .data(result)
                .build();
    }
}
