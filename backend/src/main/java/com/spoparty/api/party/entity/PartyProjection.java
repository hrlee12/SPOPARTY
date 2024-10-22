package com.spoparty.api.party.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PartyProjection {
	Long getId();

	String getOpenviduSessionId();

	String getTitle();

	Integer getMaxParticipants();

	Integer getCurrentParticipants();

	String getFixtureUrl();

	@JsonProperty("fixtureId")
	Long getFixture_id(); // fixture 정보 추가 필요

	@JsonProperty("hostId")
	Long getMember_id();

	@JsonProperty("hostNickname")
	String getMember_nickname();

	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// LocalDateTime getCreatedTime();
	//
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// LocalDateTime getUpdatedTime();

	// @JsonProperty("clubId")
	// Long getClub_id();
}