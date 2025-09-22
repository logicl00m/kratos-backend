package org.kratos.backend.workflowconfig.presenter.rest;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.dtos.PaginationResponse;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.workflowconfig.dto.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Workflow Config CRUD (dummy stubs).
 * Base path: /api/v0/client/private/workflow-config
 */
@RestController
@RequestMapping("/api/v0/client/private/workflow-config")
@RequiredArgsConstructor
public class WorkflowConfigDummyResource {

    // POST / -> create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public WorkflowConfiguration createWorkflowConfig(
            @Valid @RequestBody WorkflowConfigurationRequest request
    ) {
        WorkflowConfiguration incoming = request.configuration();
        String id = incoming != null && incoming.id() != null && !incoming.id().isBlank()
                ? incoming.id() : UUID.randomUUID().toString();
        String version = incoming != null && incoming.version() != null ? incoming.version() : "1";

        return sampleConfiguration(id, version);
    }

    // GET / -> list
    @GetMapping
    public PaginatedResponse<List<WorkflowConfiguration>> listWorkflowConfigs(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        List<WorkflowConfiguration> all = List.of(
                sampleConfiguration("wf-1", "1"),
                sampleConfiguration("wf-2", "1"),
                sampleConfiguration("wf-3", "2")
        );

        if (q != null && !q.isBlank()) {
            String term = q.toLowerCase();
            all = all.stream()
                    .filter(w -> (w.id() != null && w.id().toLowerCase().contains(term)) ||
                            (w.version() != null && w.version().toLowerCase().contains(term)))
                    .collect(Collectors.toList());
        }

        int safeLimit = limit != null && limit > 0 ? limit : 10;
        int safeOffset = offset != null && offset >= 0 ? offset : 0;

        int from = Math.min(safeOffset, all.size());
        int to = Math.min(from + safeLimit, all.size());
        List<WorkflowConfiguration> page = all.subList(from, to);

        PaginationResponse pagination = new PaginationResponse(0, safeLimit, 1, (long) all.size());
        return new PaginatedResponse<>(page, pagination);
    }

    // GET /{id} -> get single
    @GetMapping("/{id}")
    public WorkflowConfiguration getWorkflowConfig(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Workflow ID cannot be blank");
        }
        return sampleConfiguration(id, "1");
    }

    // PATCH /{id} -> partial update
    @PatchMapping("/{id}")
    public WorkflowConfiguration patchWorkflowConfig(@PathVariable String id,
                                                     @RequestBody(required = false) Map<String, Object> patch) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Workflow ID cannot be blank");
        }
        if (patch == null || patch.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patch body cannot be empty");
        }

        return sampleConfiguration(id, String.valueOf(System.currentTimeMillis()));
    }

    // PUT /{id} -> full replace
    @PutMapping("/{id}")
    public WorkflowConfiguration putWorkflowConfig(@PathVariable String id,
                                                    @Valid @RequestBody WorkflowConfigurationRequest request) {
        WorkflowConfiguration incoming = request.configuration();
        if (incoming == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Configuration cannot be null");
        }
        String useId = (incoming.id() != null && !incoming.id().isBlank()) ? incoming.id() : id;
        String version = incoming.version() != null ? incoming.version() : "1";
        return sampleConfiguration(useId, version);
    }

    // DELETE /{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteWorkflowConfig(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Workflow ID cannot be blank");
        }
        // no-op for dummy
    }

    // ---------- Helpers ----------
    private WorkflowConfiguration sampleConfiguration(String id, String version) {
        Field nameField = new Field("applicantName", "", List.of("save", "validate"), "text", new TextFieldSpec("Applicant name"));
        Form form = new Form(Map.of("applicantName", nameField));
        Map<String, Form> forms = Map.of("applicationCore", form);

        SimpleStateSpec draftSpec = new SimpleStateSpec(List.of(new FormRef("applicationCore")), Map.of(
                "submit", new Action("submit", "Screening", null, "submitOperation")
        ));
        SimpleStateSpec screeningSpec = new SimpleStateSpec(List.of(new FormRef("applicationCore")), Map.of(
                "approve", new Action("approve", "Approved", null, "approveOperation"),
                "reject", new Action("reject", "Rejected", null, "rejectOperation")
        ));

        Map<String, State> defs = new LinkedHashMap<>();
        defs.put("Draft", new State("Draft", "simple", draftSpec));
        defs.put("Screening", new State("Screening", "simple", screeningSpec));
        defs.put("Approved", new State("Approved", "simple", null));
        defs.put("Rejected", new State("Rejected", "simple", null));

        States states = new States("Draft", defs);
        Map<String, Script> scripts = Map.of("onEnterScreening", new Script(Map.of("type", "log", "msg", "entered screening")));

        return new WorkflowConfiguration(id, version, forms, states, scripts);
    }

}
