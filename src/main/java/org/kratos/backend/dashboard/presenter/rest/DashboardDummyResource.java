package org.kratos.backend.dashboard.presenter.rest;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.dashboard.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.UUID; // added to accept X-Subject header

@RestController
@RequestMapping("/api/v0/client/private/dashboard")
@RequiredArgsConstructor
public class DashboardDummyResource {
    
    @GetMapping("/applications")
    public DashboardApplicationsResponse getApplications(
        @RequestHeader("X-Subject") UUID wfConfigId,
        @RequestParam(required = false) Integer limit,
        @RequestParam(required = false) Integer offset,
        @RequestParam(required = false) String search,
        @RequestParam(name = "filters.stage", required = false) List<String> filtersStage,
        @RequestParam(name = "filters.status", required = false) List<String> filtersStatus,
        @RequestParam(name = "filters.product", required = false) List<String> filtersProduct,
        @RequestParam(name = "filters.assignee", required = false) List<String> filtersAssignee
    ) {
        // Build the request DTO from query params (handy if you later pass to a service)
        DashboardApplicationsRequest request = new DashboardApplicationsRequest(
            limit, offset, search,
            new DashboardApplicationsRequest.Filters(filtersStage, filtersStatus, filtersProduct, filtersAssignee)
        );

        // Sample dataset (in real life, fetch using `request`)
        List<DashboardApplicationsResponse.Item> all = sampleApplications();

        // Optional search filter (basic contains)
        if (request.search() != null && !request.search().isBlank()) {
            String term = request.search().toLowerCase();
            all = all.stream()
                .filter(it ->
                    it.applicantName().toLowerCase().contains(term) ||
                    it.productType().toLowerCase().contains(term) ||
                    it.currentState().toLowerCase().contains(term))
                .collect(Collectors.toList());
        }

        // Optional stage filter demo
        if (request.filters() != null && request.filters().stage() != null && !request.filters().stage().isEmpty()) {
            List<String> allow = request.filters().stage().stream().map(String::toLowerCase).toList();
            all = all.stream()
                .filter(it -> allow.contains(it.currentState().toLowerCase()))
                .collect(Collectors.toList());
        }

        int safeLimit = request.limit() != null ? request.limit() : 10;
        int safeOffset = request.offset() != null ? request.offset() : 0;

        int from = Math.min(safeOffset, all.size());
        int to = Math.min(from + safeLimit, all.size());
        List<DashboardApplicationsResponse.Item> page = all.subList(from, to);

        DashboardApplicationsResponse.Pagination pagination = new DashboardApplicationsResponse.Pagination(all.size(), safeLimit, safeOffset);
        return new DashboardApplicationsResponse(page, pagination, "success");
    }

    // 2) GET /api/dashboard/statistics
    @GetMapping("/statistics")
    public DashboardStatsResponse getStatistics(
        @RequestHeader("X-Subject") UUID wfConfigId,
        @RequestParam(name = "filters.stage", required = false) List<String> filtersStage,
        @RequestParam(name = "filters.status", required = false) List<String> filtersStatus,
        @RequestParam(name = "filters.product", required = false) List<String> filtersProduct,
        @RequestParam(name = "filters.assignee", required = false) List<String> filtersAssignee
    ) {
        DashboardStatsRequest request = new DashboardStatsRequest(
            new DashboardStatsRequest.Filters(filtersStage, filtersStatus, filtersProduct, filtersAssignee)
        );

        // Start from the same sample set, then apply basic stage filter (as demo)
        List<DashboardApplicationsResponse.Item> items = sampleApplications();
        if (request.filters() != null && request.filters().stage() != null && !request.filters().stage().isEmpty()) {
            List<String> allow = request.filters().stage().stream().map(String::toLowerCase).toList();
            items = items.stream()
                .filter(it -> allow.contains(it.currentState().toLowerCase()))
                .collect(Collectors.toList());
        }

        // Fake status rollups (you can compute these from real data later)
        int total = items.size();
        int approved = (int) items.stream().filter(i -> "Approved".equalsIgnoreCase(i.currentState())).count();
        int rejected = (int) items.stream().filter(i -> "Rejected".equalsIgnoreCase(i.currentState())).count();
        int pending = total - approved - rejected;

        int ontime = (int) items.stream().filter(i -> i.sla().status().equals("ontime")).count();
        int due = (int) items.stream().filter(i -> i.sla().status().equals("due")).count();
        int overdue = (int) items.stream().filter(i -> i.sla().status().equals("overdue")).count();
        int completed = (int) items.stream().filter(i -> i.sla().status().equals("completed")).count();

        DashboardStatsResponse.SlaMetrics sla = new DashboardStatsResponse.SlaMetrics(ontime, due, overdue, completed);
        DashboardStatsResponse.Data data = new DashboardStatsResponse.Data(total, approved, rejected, pending, sla);
        return new DashboardStatsResponse(data, "success");
    }

    // 3) GET /api/dashboard/filter-options
    @GetMapping("/filter-options")
    public FilterOptionsResponse getFilterOptions(@RequestHeader("X-Subject") UUID wfConfigId) {
        List<String> stages = List.of("Draft", "Screening", "Credit Review", "Approved", "Rejected");
        List<String> products = List.of("OD", "L/C", "LATR", "Term Loan", "BG");
        List<FilterOptionsResponse.Assignee> assignees = List.of(
            new FilterOptionsResponse.Assignee("u-1001", "Ayesha Rahman"),
            new FilterOptionsResponse.Assignee("u-1002", "Tanvir Ahmed"),
            new FilterOptionsResponse.Assignee("u-1003", "Sadia Nasrin")
        );
        FilterOptionsResponse.Data filterData = new FilterOptionsResponse.Data(stages, products, assignees);
        return new FilterOptionsResponse(filterData, "success");
    }

    // ---------- Sample data helpers ----------

    private static List<DashboardApplicationsResponse.Item> sampleApplications() {
        String now = OffsetDateTime.now().toString();
        List<DashboardApplicationsResponse.Item> list = new ArrayList<>();

        list.add(new DashboardApplicationsResponse.Item(
            "APP-0001",
            "Rahman Textiles Ltd.",
            "Term Loan",
            50_000_000,
            "Screening",
            new DashboardApplicationsResponse.Person("Ayesha Rahman", "Analyst"),
            new DashboardApplicationsResponse.Person("Mahmudul Hasan", "RM"),
            new DashboardApplicationsResponse.Sla("ontime", "2d 4h"),
            12,
            now
        ));

        list.add(new DashboardApplicationsResponse.Item(
            "APP-0002",
            "Delta Plastics",
            "OD",
            10_000_000,
            "Approved",
            new DashboardApplicationsResponse.Person("Tanvir Ahmed", "Credit Manager"),
            new DashboardApplicationsResponse.Person("Mahmudul Hasan", "RM"),
            new DashboardApplicationsResponse.Sla("completed", "0"),
            7,
            now
        ));

        list.add(new DashboardApplicationsResponse.Item(
            "APP-0003",
            "City Foods",
            "BG",
            8_000_000,
            "Credit Review",
            new DashboardApplicationsResponse.Person("Sadia Nasrin", "Senior Analyst"),
            new DashboardApplicationsResponse.Person("System", "System"),
            new DashboardApplicationsResponse.Sla("due", "6h 30m"),
            4,
            now
        ));

        list.add(new DashboardApplicationsResponse.Item(
            "APP-0004",
            "Orbital Electronics",
            "L/C",
            20_000_000,
            "Rejected",
            new DashboardApplicationsResponse.Person("—", "—"),
            new DashboardApplicationsResponse.Person("Mahmudul Hasan", "RM"),
            new DashboardApplicationsResponse.Sla("overdue", "-1d 3h"),
            9,
            now
        ));

        return list;
    }

    @GetMapping("/application/{id}")
    public ApplicationDetailResponse getApplicationDetail(@RequestHeader("X-Subject") UUID wfConfigId, @PathVariable String id) {
        String now = OffsetDateTime.now().toString();

        // Sample "forms" map with fields (you can plug your real form schema later)
        Map<String, ApplicationDetailResponse.Form> forms = Map.of(
            "applicationCore", new ApplicationDetailResponse.Form(
                List.of(
                    new ApplicationDetailResponse.FormField("applicantLegalName", "Applicant Legal Name", "text", "Rahman Textiles Ltd.", List.of(
                        new ApplicationDetailResponse.FieldAction("save"),
                        new ApplicationDetailResponse.FieldAction("validate")
                    )),
                    new ApplicationDetailResponse.FormField("requestedAmount", "Requested Amount", "number", 50000000, List.of(
                        new ApplicationDetailResponse.FieldAction("save")
                    ))
                )
            )
        );

        // Sample states map (assignees, forms attached to states, actions, history)
        Map<String, ApplicationDetailResponse.State> states = Map.of(
            "Screening", new ApplicationDetailResponse.State(
                List.of(new ApplicationDetailResponse.Assignee(
                    "u-1001", "Ayesha Rahman", "Analyst", "ayesha@example.com", true, now
                )),
                List.of(new ApplicationDetailResponse.StateForm(
                    "applicationCore", "visible", Map.of("requestedAmount", Map.of("min", 0))
                )),
                Map.of("requestDocs", Map.of("allowed", true)),
                List.of(
                    new ApplicationDetailResponse.History(
                        "h-1", now,
                        new ApplicationDetailResponse.Actor("u-rm1", "Mahmudul Hasan", "RM"),
                        "submit", "", "Screening", List.of(Map.of("field", "requestedAmount", "from", null, "to", 50000000))
                    )
                )
            ),
            "Approved", new ApplicationDetailResponse.State(
                List.of(new ApplicationDetailResponse.Assignee(
                    "u-1002", "Tanvir Ahmed", "Credit Manager", "tanvir@example.com", true, now
                )),
                List.of(),
                Map.of("issueSanction", Map.of("allowed", true)),
                List.of(
                    new ApplicationDetailResponse.History(
                        "h-2", now,
                        new ApplicationDetailResponse.Actor("u-1002", "Tanvir Ahmed", "Credit Manager"),
                        "approve", "Credit Review", "Approved", List.of()
                    )
                )
            )
        );

        ApplicationDetailResponse.Workflow workflow = new ApplicationDetailResponse.Workflow(
            id,
            3,
            "Draft",
            "Screening",
            now,
            forms,
            states
        );

        return new ApplicationDetailResponse(new ApplicationDetailResponse.Data(workflow), "success");
    }
}