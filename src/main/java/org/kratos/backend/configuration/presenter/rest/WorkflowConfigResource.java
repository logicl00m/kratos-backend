package org.kratos.backend.configuration.presenter.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.core.domain.request.CreateWorkflowConfigRequest;
import org.kratos.backend.core.domain.request.UpdateWorkflowConfigRequest;
import org.kratos.backend.core.domain.request.GetWorkflowConfigRequest;
import org.kratos.backend.core.domain.request.GetAllWorkflowConfigsRequest;
import org.kratos.backend.core.domain.request.DeleteWorkflowConfigRequest;
import org.kratos.backend.core.domain.response.CreateWorkflowConfigResponse;
import org.kratos.backend.core.domain.response.UpdateWorkflowConfigResponse;
import org.kratos.backend.core.domain.response.GetWorkflowConfigResponse;
import org.kratos.backend.core.domain.response.GetAllWorkflowConfigsResponse;
import org.kratos.backend.core.domain.response.DeleteWorkflowConfigResponse;
import org.kratos.backend.core.service.WorkflowConfigService;
import org.springframework.web.bind.annotation.*;

// todo: Abir
@RestController
@RequestMapping("/api/v1/client/private/configuration")
@RequiredArgsConstructor
public class WorkflowConfigResource {
    private final WorkflowConfigService workflowConfigService;

    @PostMapping("/create")
    public Response<CreateWorkflowConfigResponse> createConfiguration(@RequestHeader("X-Subject") String userId,
                                                                      @RequestBody @Valid CreateWorkflowConfigRequest request) {
        return Response.<CreateWorkflowConfigResponse>builder()
                .status(ResponseStatus.ALL_OK)
                .data(workflowConfigService.createWorkflowConfig(request))
                .build();
    }

    @PostMapping("/update")
    public Response<UpdateWorkflowConfigResponse> updateConfiguration(@RequestHeader("X-Subject") String workflowConfigId,
                                                                       @RequestBody @Valid UpdateWorkflowConfigRequest request) {
        return Response.<UpdateWorkflowConfigResponse>builder()
                .status(ResponseStatus.ALL_OK)
                .data(workflowConfigService.updateWorkflowConfig(request))
                .build();
    }

    @PostMapping("/get")
    public Response<GetWorkflowConfigResponse> getConfiguration(@RequestHeader("X-Subject") String workflowConfigId) {
        return Response.<GetWorkflowConfigResponse>builder()
                .status(ResponseStatus.ALL_OK)
                .data(workflowConfigService.getWorkflowConfig(workflowConfigId))
                .build();
    }

    @PostMapping("/get/all")
    public Response<GetAllWorkflowConfigsResponse> getAllConfigurations(@RequestHeader("X-Subject") String userId) {
        return Response.<GetAllWorkflowConfigsResponse>builder()
                .status(ResponseStatus.ALL_OK)
                .data(workflowConfigService.getAllWorkflowConfigs())
                .build();
    }

    @PostMapping("/delete")
    public Response<DeleteWorkflowConfigResponse> deleteConfiguration(@RequestHeader("X-Subject") String workflowConfigId) {
        return Response.<DeleteWorkflowConfigResponse>builder()
                .status(ResponseStatus.ALL_OK)
                .data(workflowConfigService.deleteWorkflowConfig(workflowConfigId))
                .build();
    }

}
