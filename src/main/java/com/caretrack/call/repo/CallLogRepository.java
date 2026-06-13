package com.caretrack.call.repo;

import com.caretrack.call.model.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallLogRepository extends JpaRepository<CallLog, Long> {
    long countByCreatedAtAfter(java.time.Instant instant);
}
