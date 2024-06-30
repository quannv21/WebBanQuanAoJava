package com.hutech.demo.repository;

import com.hutech.demo.model.TournamentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentCategoryRepository extends JpaRepository<TournamentCategory,Long> {
}
