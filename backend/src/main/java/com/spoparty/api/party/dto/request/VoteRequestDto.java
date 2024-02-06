package com.spoparty.api.party.dto.request;

import java.util.Map;

import com.spoparty.redis.SubscribeType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoteRequestDto {
	SubscribeType type = SubscribeType.BROAD_CAST;

	@NotNull(message = "memberId가 없습니다.")
	Long memberId;

	@NotNull(message = "clubId가 없습니다.")
	String clubId;

	@NotNull(message = "partyId가 없습니다.")
	String partyId;

	@NotNull(message = "content가 없습니다.")
	String content;

	@NotNull(message = "options가 없습니다.")
	Map<Integer, String> options;


	Integer answerIdx; // null이면 정답체크 안 된 것.
}
