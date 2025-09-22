package org.kratos.backend.dashboard.dto;


import java.util.List;

public record DashboardApplicationsResponse(
    List<Item> data,
    Pagination pagination,
    String status
) {
  public record Item(
      String id,
      String applicantName,
      String productType,
      Number requestedAmount,
      String currentState,
      Person currentAssignee,
      Person initiatedBy,
      Sla sla,
      int documentCount,
      String lastUpdatedAt
  ) {}

  public record Person(String name, String role) {}

  public record Sla(String status, String remainingTime) {}

  public record Pagination(int total, int limit, int offset) {}
}
