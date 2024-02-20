package com.spoparty.batch.repository;

import java.util.Optional;

import com.spoparty.batch.entity.SeasonLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.entity.Team;

@Repository
public interface SeasonLeagueTeamRepository extends JpaRepository<SeasonLeagueTeam, Long> {
	SeasonLeagueTeam findByTeam(Team team);

	SeasonLeagueTeam findByTeam_Id(Long id);

	SeasonLeagueTeam findByTeam_IdAndSeasonLeague(Long id, SeasonLeague seasonLeague);
}
