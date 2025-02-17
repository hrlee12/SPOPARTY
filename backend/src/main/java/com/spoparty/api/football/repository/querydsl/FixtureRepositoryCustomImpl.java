package com.spoparty.api.football.repository.querydsl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.QFixture;
import com.spoparty.api.football.entity.QLeague;
import com.spoparty.api.football.entity.QSeasonLeague;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QTeam;
import com.spoparty.api.football.response.HomeOrAway;
import com.spoparty.api.football.response.PartyFixtureDTO;
import com.spoparty.api.football.response.QHomeOrAway;
import com.spoparty.api.football.response.QKeywordSeasonLeagueTeamDTO;
import com.spoparty.api.football.response.QPartyFixtureDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FixtureRepositoryCustomImpl implements FixtureRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	private static final QFixture fixture = QFixture.fixture;
	private static final QSeasonLeague seasonLeague = QSeasonLeague.seasonLeague;

	private static final QSeasonLeagueTeam homeSeasonLeagueTeam = new QSeasonLeagueTeam("homeSeasonLeagueTeam");
	private static final QSeasonLeagueTeam awaySeasonLeagueTeam = new QSeasonLeagueTeam("awaySeasonLeagueTeam");

	private static final QLeague league = QLeague.league;

	private static final QTeam homeTeam = new QTeam("homeTeam");
	private static final QTeam awayTeam = new QTeam("awayTeam");

	@Override
	public List<Fixture> findFixtureByNext(LocalDateTime now, int count) {

		return jpaQueryFactory.select(fixture)
			.from(fixture)
			.join(fixture.seasonLeague, seasonLeague)
			.fetchJoin()
			.join(seasonLeague.league, league)
			.fetchJoin()
			.join(fixture.homeTeam, homeSeasonLeagueTeam)
			.fetchJoin()
			.join(homeSeasonLeagueTeam.team, homeTeam)
			.fetchJoin()
			.join(fixture.awayTeam, awaySeasonLeagueTeam)
			.fetchJoin()
			.join(awaySeasonLeagueTeam.team, awayTeam)
			.fetchJoin()
			.where(fixture.startTime.goe(now))
			.orderBy(fixture.startTime.asc())
			.limit((long)count)
			.fetch();
	}

	public List<Fixture> findFixtureByDate(LocalDateTime start, LocalDateTime end) {

		return jpaQueryFactory.select(fixture)
			.from(fixture)
			.join(fixture.seasonLeague, seasonLeague)
			.fetchJoin()
			.join(seasonLeague.league, league)
			.fetchJoin()
			.join(fixture.homeTeam, homeSeasonLeagueTeam)
			.fetchJoin()
			.join(homeSeasonLeagueTeam.team, homeTeam)
			.fetchJoin()
			.join(fixture.awayTeam, awaySeasonLeagueTeam)
			.fetchJoin()
			.join(awaySeasonLeagueTeam.team, awayTeam)
			.fetchJoin()
			.where(fixture.startTime.between(start, end))
			.orderBy(fixture.startTime.asc())
			.fetch();
	}

	// 리그 이름으로 검색하기
	public List<Fixture> findFixtureByLeague(String keyword) {

		return jpaQueryFactory.select(fixture)
			.from(fixture)
			.join(fixture.seasonLeague, seasonLeague)
			.fetchJoin()
			.join(seasonLeague.league, league)
			.fetchJoin()
			.join(fixture.homeTeam, homeSeasonLeagueTeam)
			.fetchJoin()
			.join(homeSeasonLeagueTeam.team, homeTeam)
			.fetchJoin()
			.join(fixture.awayTeam, awaySeasonLeagueTeam)
			.fetchJoin()
			.join(awaySeasonLeagueTeam.team, awayTeam)
			.fetchJoin()
			.where(league.nameKr.contains(keyword))
			.orderBy(fixture.startTime.asc())
			.fetch();
	}

	// 팀 이름으로 검색하기
	public List<Fixture> findFixtureByTeam(String keyword) {

		return jpaQueryFactory.select(fixture)
			.from(fixture)
			.join(fixture.seasonLeague, seasonLeague)
			.fetchJoin()
			.join(seasonLeague.league, league)
			.fetchJoin()
			.join(fixture.homeTeam, homeSeasonLeagueTeam)
			.fetchJoin()
			.join(homeSeasonLeagueTeam.team, homeTeam)
			.fetchJoin()
			.join(fixture.awayTeam, awaySeasonLeagueTeam)
			.fetchJoin()
			.join(awaySeasonLeagueTeam.team, awayTeam)
			.fetchJoin()
			.where(homeTeam.nameKr.contains(keyword).or(awayTeam.nameKr.contains(keyword)))
			.orderBy(fixture.startTime.asc())
			.fetch();
	}

	@Override
	public PartyFixtureDTO findPartyFixture(long fixtureId) {
		return jpaQueryFactory.select(new QPartyFixtureDTO(
				league.nameKr, fixture.roundKr, fixture.startTime,
				new QKeywordSeasonLeagueTeamDTO(homeSeasonLeagueTeam.id, homeTeam.nameKr),
				new QKeywordSeasonLeagueTeamDTO(awaySeasonLeagueTeam.id, awayTeam.nameKr)
			))
			.from(fixture)
			.join(fixture.seasonLeague, seasonLeague)
			.join(seasonLeague.league, league)
			.join(fixture.homeTeam, homeSeasonLeagueTeam)
			.join(homeSeasonLeagueTeam.team, homeTeam)
			.join(fixture.awayTeam, awaySeasonLeagueTeam)
			.join(awaySeasonLeagueTeam.team, awayTeam)
			.where(fixture.id.eq(fixtureId))
			.fetchOne();
	}


	public HomeOrAway whichTeamCheer(long fixtureId){
		return jpaQueryFactory.select(new QHomeOrAway(homeSeasonLeagueTeam.id, awaySeasonLeagueTeam.id))
			.from(fixture)
			.join(fixture.homeTeam, homeSeasonLeagueTeam)
			.join(fixture.awayTeam, awaySeasonLeagueTeam)
			.where(fixture.id.eq(fixtureId))
			.fetchOne();
	}
}
