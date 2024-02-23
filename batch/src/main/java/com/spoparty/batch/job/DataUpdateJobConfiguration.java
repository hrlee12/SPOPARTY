package com.spoparty.batch.job;

import com.spoparty.batch.step.SeasonLeagueTeamPlayerStepConfiguration;
import com.spoparty.batch.step.StandingStepConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spoparty.batch.step.SeasonLeagueJpaStepConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataUpdateJobConfiguration {


	@Bean
	public Job jpaJob(JobRepository jobRepository, Step seasonLeagueStep, Step seasonLeagueTeamStep, Step seasonLeagueTeamPlayerStep, Step standingStep, Step fixtureStep) {
		return new JobBuilder("jpaJob", jobRepository)
			.start(seasonLeagueStep)
			.next(seasonLeagueTeamStep)
			.next(seasonLeagueTeamPlayerStep)
			.next(standingStep)
			.next(fixtureStep)
				.build();


	}


}
//	.start(conditionalStep1)
// 			.on("FAILED") // exit status가 FAILED 일 경우
// 			.to(conditionalStep3) // Step3으로 이동한다.
// 			.on("*") // step3의 결과 관계 없이
// 			.end() // step3으로 이동하면 flow가 종료된다.
// 		.from(conditionalStep1) // step1로부터
// 			.on("*") // FAILED 외의 모든 경우
// 			.to(conditionalStep2) // step2로 이동한다.
// 			.next(conditionalStep3) // step2가 정상 종료되면 step으로 이동한다.
// 			.on("*") // step3의 결과 관계 없이
// 			.end() // step3으로 이동하면 flow가 종료된다.
// 		.end() // job 종료
// 		.build();