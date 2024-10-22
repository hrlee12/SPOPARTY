package com.spoparty.batch.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeasonLeague extends FootballBaseEntity {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="season_league_id")
	private long id;

	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime seasonStartDate;

	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime seasonEndDate;



	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "season_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Season season;


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "league_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private League league;

	@OneToMany(mappedBy = "seasonLeague")
	private List<SeasonLeagueTeam> seasonLeagueTeams = new ArrayList<>();

	@OneToMany(mappedBy = "seasonLeague")
	private List<Fixture> fixtures = new ArrayList<>();


	@Builder
	public SeasonLeague(Long id, LocalDateTime seasonStartDate, LocalDateTime seasonEndDate, Season season, League league) {
		this.id = id;
		this.seasonStartDate = seasonStartDate;
		this.seasonEndDate = seasonEndDate;
		this.season = season;
		this.league = league;
	}
}
