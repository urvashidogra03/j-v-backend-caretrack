package com.caretrack.report.service;

import com.caretrack.cases.model.CaseStatus;
import com.caretrack.cases.repo.AssistanceCaseRepository;
import com.caretrack.comm.repo.CommunicationLogRepository;
import com.caretrack.call.repo.CallLogRepository;
import com.caretrack.invoice.model.Invoice;
import com.caretrack.invoice.repo.InvoiceRepository;
import com.caretrack.provider.repo.ProviderRepository;
import com.caretrack.report.dto.CasesReportResponse;
import com.caretrack.report.dto.DashboardMetricsResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportsService {

    private final AssistanceCaseRepository caseRepo;
    private final ProviderRepository providerRepo;
    private final CommunicationLogRepository commRepo;
    private final CallLogRepository callRepo;
    private final InvoiceRepository invoiceRepo;

    public ReportsService(AssistanceCaseRepository caseRepo,
                          ProviderRepository providerRepo,
                          CommunicationLogRepository commRepo,
                          CallLogRepository callRepo,
                          InvoiceRepository invoiceRepo) {
        this.caseRepo = caseRepo;
        this.providerRepo = providerRepo;
        this.commRepo = commRepo;
        this.callRepo = callRepo;
        this.invoiceRepo = invoiceRepo;
    }

    public CasesReportResponse getCasesReport() {
        long total = caseRepo.count();
        Instant sevenDaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        long openedLast7Days = caseRepo.countByCreatedAtAfter(sevenDaysAgo);

        Map<String, Long> byStatus = new HashMap<>();
        for (CaseStatus s : CaseStatus.values()) {
            byStatus.put(s.name(), caseRepo.countByStatus(s));
        }

        return new CasesReportResponse(total, openedLast7Days, byStatus);
    }

    public DashboardMetricsResponse getDashboardMetrics() {
        Instant sevenDaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);

        long totalCases = caseRepo.count();
        Map<String, Long> casesByStatus = new HashMap<>();
        for (CaseStatus s : CaseStatus.values()) {
            casesByStatus.put(s.name(), caseRepo.countByStatus(s));
        }

        long totalProviders = providerRepo.count();
        long totalComms7d = commRepo.countByCreatedAtAfter(sevenDaysAgo);
        long totalCalls7d = callRepo.countByCreatedAtAfter(sevenDaysAgo);

        long totalInvoices = invoiceRepo.count();
        long invoicesPaid = invoiceRepo.countByStatus(Invoice.Status.PAID);
        BigDecimal invoicesAmountSum = invoiceRepo.sumAmounts();
        if (invoicesAmountSum == null) invoicesAmountSum = BigDecimal.ZERO;

        return new DashboardMetricsResponse(
                totalCases,
                casesByStatus,
                totalProviders,
                totalComms7d,
                totalCalls7d,
                totalInvoices,
                invoicesPaid,
                invoicesAmountSum
        );
    }
}
