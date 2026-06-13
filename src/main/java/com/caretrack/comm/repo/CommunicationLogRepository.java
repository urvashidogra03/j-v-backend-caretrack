package com.caretrack.comm.repo;

import com.caretrack.comm.model.CommunicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationLogRepository extends JpaRepository<CommunicationLog, Long> {
    long countByCreatedAtAfter(java.time.Instant instant);
}
