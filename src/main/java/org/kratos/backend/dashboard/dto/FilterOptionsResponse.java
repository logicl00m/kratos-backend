package org.kratos.backend.dashboard.dto;

import java.util.List;

public record FilterOptionsResponse(
    Data data,
    String status
) {
  public record Data(
      List<String> stages,
      List<String> products,
      List<Assignee> assignees
  ) {}

  public record Assignee(
      String id,
      String name
  ) {}
}