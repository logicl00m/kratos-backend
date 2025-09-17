package org.kratos.backend.core.service;

import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowConfigService extends BaseService {

    public CreateWorkflowConfigResponse createWorkflowConfig(CreateWorkflowConfigRequest createWorkflowConfigRequest) {
        //do validation on json

        //add metadata, created by etc,

        //save to db use transactional

        //return the saved configId, and json
        return new CreateWorkflowConfigResponse("Workflow Config Successfully Created",
                "dummyConfig123", "");
    }

    public UpdateWorkflowConfigResponse updateWorkflowConfig(UpdateWorkflowConfigRequest updateWorkflowConfigRequest) {
        //check if config exists

        //validate the update request

        //if valid then update the config

        //save to db

        //return updated json
        return new UpdateWorkflowConfigResponse("dummyConfig123", "");
    }

    public GetWorkflowConfigResponse getWorkflowConfig(String workflowConfigId) {
        //check if config exists

        //check if user has permission to access the config

        //return the config json
        return new GetWorkflowConfigResponse("Workflow Config Successfully Fetched",
                "dummyConfig123", "");
    }

    public GetAllWorkflowConfigsResponse getAllWorkflowConfigs() {
        //fetch permission for user

        //now check with workflow configs and return all with proper permission

        //return response in a list
        return new GetAllWorkflowConfigsResponse(List.of(new GetWorkflowConfigResponse("Workflow Config Successfully Fetched",
                "dummyConfig123", "")));
    }

    public DeleteWorkflowConfigResponse deleteWorkflowConfig(String workflowConfigId) {
        return new DeleteWorkflowConfigResponse("Workflow Successfully Deleted", true);
    }
}
