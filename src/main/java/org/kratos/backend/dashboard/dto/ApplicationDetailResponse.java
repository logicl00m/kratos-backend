package org.kratos.backend.dashboard.dto;

import java.util.List;
import java.util.Map;

public record ApplicationDetailResponse(
    Data data,
    String status
) {
  public record Data(Workflow workflow) {}

  public record Workflow(
      String id,
      Integer version,
      String initialState,
      String currentState,
      String currentStateEnteredAt,
      Map<String, Form> forms,
      Map<String, State> states
  ) {}

  // forms: Record<string, { fields: Array<...> }>
  public record Form(List<FormField> fields) {}

  public record FormField(
      String id,
      String name,
      String type,
      Object data,
      List<FieldAction> fieldActions
  ) {}

  public record FieldAction(String operation) {}

  // states: Record<string, {...}>
  public record State(
      List<Assignee> assignees,
      List<StateForm> forms,
      Map<String, Object> actions,
      List<History> history
  ) {}

  public record Assignee(
      String subjectId,
      String employeeName,
      String role,
      String email,
      Boolean primary,
      String since
  ) {}

  public record StateForm(
      String formName,
      String visibility,
      Map<String, Object> fieldOverrides
  ) {}

  public record History(
      String id,
      String at,
      Actor byUser,
      String action,
      String stateFrom,
      String stateTo,
      List<Object> changes
  ) {}

  public record Actor(
      String id,
      String name,
      String role
  ) {}
}
