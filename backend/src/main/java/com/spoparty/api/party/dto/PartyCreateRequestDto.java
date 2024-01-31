package com.spoparty.api.party.dto;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PartyCreateRequestDto {
	@NotNull(message = "clubId가 없습니다.")
	private Long clubId;

	@NotNull(message = "memberId가 없습니다.")
	private Long memberId;

	@NotNull(message = "파티명이 없습니다.")
	@Size(min = 1, max = 30, message = "파티명은 1 ~ 30자 이여야 합니다.")
	private String title;

	@NotNull(message = "경기 URL이 없습니다.")
	private String fixtureUrl;

	@NotNull(message = "fixtureId가 없습니다.")
	private Long fixtureId;

	@NotNull(message = "openViduSessionInfo가 없습니다.")
	private Map<String, Object> openViduSessionInfo;
}