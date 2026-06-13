package com.caretrack.cases.repo;

import com.caretrack.cases.model.AssistanceCase;
import com.caretrack.cases.model.CaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssistanceCaseRepository extends JpaRepository<AssistanceCase, Long> {
    List<AssistanceCase> findAllByStatus(CaseStatus status);
    long countByStatus(CaseStatus status);
    long countByCreatedAtAfter(java.time.Instant instant);
}
