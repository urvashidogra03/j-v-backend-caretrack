package com.caretrack.user.model;

/**
 * Role-based access control (RBAC) roles for CareTrack users.
 *
 * ADMIN   - Platform administration, user/role management, full access.
 * MANAGER - Operational management, approvals, reporting, oversight.
 * OPS     - Operations/executives handling cases, providers, communications.
 * VIEWER  - Read-only access for compliance/monitoring.
 *
 * Extendable to include FINANCE, AUDIT, etc. if needed.
 */
public enum Role {
    ADMIN,
    MANAGER,
    OPS,
    VIEWER
}
