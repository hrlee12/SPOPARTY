package com.spoparty.api.club.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InviteRequestDto {
	@NotBlank(message = "memberId가 없습니다.")
	private long memberId;

	@NotBlank(message = "초대 URL이 없습니다.")
	private String inviteUrl;
}
