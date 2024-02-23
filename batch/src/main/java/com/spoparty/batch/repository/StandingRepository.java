package com.spoparty.batch.repository;

import com.spoparty.batch.entity.SeasonLeagueTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.batch.entity.Standings;

@Repository
public interface StandingRepository extends JpaRepository<Standings, Long> {
    Standings findByGroupAndSeasonLeagueTeam(String Group, SeasonLeagueTeam seasonLeagueTeam);
}
