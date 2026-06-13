package com.caretrack.cases.model;

/**
 * Case lifecycle statuses for medical assistance operations.
 */
public enum CaseStatus {
    OPEN,          // Newly created
    ASSIGNED,      // Assigned to an operations executive
    IN_PROGRESS,   // Work ongoing
    ON_HOLD,       // Waiting for external info/approval
    CLOSED,        // Successfully closed
    CANCELLED      // Cancelled due to conditions
}
