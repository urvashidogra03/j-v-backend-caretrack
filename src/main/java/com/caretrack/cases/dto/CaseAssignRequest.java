package com.caretrack.cases.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Request for assigning a case to a user.
 */
public class CaseAssignRequest {

    @NotNull
    private Long assigneeUserId;

    public CaseAssignRequest() {}

    public CaseAssignRequest(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }
}
