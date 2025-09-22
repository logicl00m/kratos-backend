package org.kratos.backend.dashboard.dto;



import java.util.List;

public record DashboardApplicationsRequest(
    Integer limit,
    Integer offset,
    String search,
    Filters filters
) {
  public record Filters(
      List<String> stage,
      List<String> status,
      List<String> product,
      List<String> assignee
  ) {}
}

