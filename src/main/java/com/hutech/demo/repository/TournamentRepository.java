package com.hutech.demo.repository;

import com.hutech.demo.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament,Long> {

}
