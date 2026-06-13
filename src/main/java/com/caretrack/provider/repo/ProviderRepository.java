package com.caretrack.provider.repo;

import com.caretrack.provider.model.Provider;
import com.caretrack.provider.model.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findAllByActiveTrue();
    List<Provider> findAllByActiveTrueAndType(ProviderType type);
}
