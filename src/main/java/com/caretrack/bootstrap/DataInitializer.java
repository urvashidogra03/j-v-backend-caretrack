package com.caretrack.bootstrap;

import com.caretrack.user.model.Role;
import com.caretrack.user.model.User;
import com.caretrack.user.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.EnumSet;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${caretrack.seed.admin.email}")
    private String adminEmail;

    @Value("${caretrack.seed.admin.password}")
    private String adminPassword;

    @Value("${caretrack.seed.demo.enabled:true}")
    private boolean demoEnabled;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedAdminUser();
        if (demoEnabled) {
            // Future: seed demo providers, cases, etc.
            log.info("Demo seeding is enabled (additional seeders will run once domains are defined).");
        }
    }

    private void seedAdminUser() {
        if (!userRepository.existsByEmail(adminEmail)) {
            Set<Role> roles = EnumSet.of(Role.ADMIN, Role.MANAGER, Role.OPS);
            User admin = new User(
                    adminEmail,
                    passwordEncoder.encode(adminPassword),
                    "System Administrator",
                    "+10000000000",
                    roles,
                    true
            );
            userRepository.save(admin);
            log.info("Seeded admin user with email: {}", adminEmail);
        } else {
            log.info("Admin user already exists: {}", adminEmail);
        }
    }
}
