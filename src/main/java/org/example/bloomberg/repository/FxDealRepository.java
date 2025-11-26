package org.example.bloomberg.repository;

import org.example.bloomberg.entity.FxDeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FxDealRepository extends JpaRepository<FxDeal, String> {
    boolean existsByDealUniqueId(String dealUniqueId);
}
