package org.kratos.backend.dashboard.dto;


public record DashboardStatsResponse(
    Data data,
    String status
) {
  public record Data(
      int totalApplications,
      int approvedApplications,
      int rejectedApplications,
      int pendingApplications,
      SlaMetrics slaMetrics
  ) {}

  public record SlaMetrics(
      int ontime,
      int due,
      int overdue,
      int completed
  ) {}
}