package com.hutech.demo.repository;

import com.hutech.demo.model.LeagueClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueClubRepository extends JpaRepository<LeagueClub,Long> {
    List<LeagueClub> findByTournamentId(Long tournamentId);
}
