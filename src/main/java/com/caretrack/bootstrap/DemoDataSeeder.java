package com.caretrack.bootstrap;

import com.caretrack.cases.model.AssistanceCase;
import com.caretrack.cases.model.CaseStatus;
import com.caretrack.cases.repo.AssistanceCaseRepository;
import com.caretrack.provider.model.Provider;
import com.caretrack.provider.model.ProviderType;
import com.caretrack.provider.repo.ProviderRepository;
import com.caretrack.user.model.Role;
import com.caretrack.user.model.User;
import com.caretrack.user.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 * Seeds demo data (providers and a sample case) when caretrack.seed.demo.enabled=true.
 * This runs AFTER DataInitializer (which creates admin user) on app startup.
 */
@Component
public class DemoDataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoDataSeeder.class);

    @Value("${caretrack.seed.demo.enabled:true}")
    private boolean demoEnabled;

    private final ProviderRepository providerRepository;
    private final AssistanceCaseRepository caseRepository;
    private final UserRepository userRepository;

    public DemoDataSeeder(ProviderRepository providerRepository,
                          AssistanceCaseRepository caseRepository,
                          UserRepository userRepository) {
        this.providerRepository = providerRepository;
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!demoEnabled) {
            return;
        }

        seedProviders();
        seedSampleCase();
    }

    private void seedProviders() {
        if (providerRepository.count() > 0) {
            return;
        }

        Provider p1 = new Provider();
        p1.setName("CityCare Hospital");
        p1.setType(ProviderType.HOSPITAL);
        p1.setCity("Mumbai");
        p1.setState("Maharashtra");
        p1.setCountry("India");
        p1.setLatitude(new BigDecimal("19.0760"));
        p1.setLongitude(new BigDecimal("72.8777"));
        p1.setPhone("+910000000001");
        p1.setEmail("info@citycare.example");
        providerRepository.save(p1);

        Provider p2 = new Provider();
        p2.setName("QuickLab Diagnostics");
        p2.setType(ProviderType.LAB);
        p2.setCity("Pune");
        p2.setState("Maharashtra");
        p2.setCountry("India");
        p2.setLatitude(new BigDecimal("18.5204"));
        p2.setLongitude(new BigDecimal("73.8567"));
        p2.setPhone("+910000000002");
        p2.setEmail("support@quicklab.example");
        providerRepository.save(p2);

        log.info("Seeded demo providers");
    }

    private void seedSampleCase() {
        if (caseRepository.count() > 0) {
            return;
        }

        Optional<User> adminOpt = userRepository.findByEmail("admin@caretrack.health");
        if (adminOpt.isEmpty()) {
            log.warn("Admin user not found for demo case seeding");
            return;
        }

        User admin = adminOpt.get();
        // Ensure admin has roles
        if (admin.getRoles() == null || admin.getRoles().isEmpty()) {
            admin.setRoles(EnumSet.of(Role.ADMIN, Role.MANAGER, Role.OPS));
            userRepository.save(admin);
        }

        Provider anyProvider = providerRepository.findAll().stream().findFirst().orElse(null);

        AssistanceCase c = new AssistanceCase();
        c.setTitle("Demo Case - Patient Coordination");
        c.setPatientName("John Doe");
        c.setStatus(CaseStatus.OPEN);
        c.setCreatedBy(admin);
        c.setProvider(anyProvider);
        c.setNotes("Initial intake created for demo purposes.");
        AssistanceCase saved = caseRepository.save(c);

        log.info("Seeded demo case with id={}, reference={}", saved.getId(), saved.getReference());
    }
}
