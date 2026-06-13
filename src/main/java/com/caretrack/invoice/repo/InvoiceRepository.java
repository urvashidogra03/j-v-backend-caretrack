package com.caretrack.invoice.repo;

import com.caretrack.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    long countByStatus(Invoice.Status status);

    @Query("select coalesce(sum(i.amount), 0) from Invoice i")
    BigDecimal sumAmounts();
}
