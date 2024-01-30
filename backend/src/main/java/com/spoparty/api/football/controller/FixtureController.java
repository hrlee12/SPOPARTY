package com.spoparty.api.football.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.service.FixtureServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/fixtures")
@RequiredArgsConstructor
public class FixtureController {
	private final Common common;
	private final FixtureServiceImpl footballService;

	// 메인에 띄우는 다가올 가장 이른 경기 6개
	@GetMapping(params = {"next"})
	ResponseEntity<ResponseDTO> findFixtureByNext(@RequestParam(value = "next") int next) {

		ResponseDTO responseDTO = footballService.findFixtureByNext(next);

		HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, code);
	}

	// 날짜별로 경기 일정 조회
	@GetMapping(params = {"date"})
	ResponseEntity<ResponseDTO> findFixtureByDate(@RequestParam(value = "date") String date) {

		ResponseDTO responseDTO = footballService.findFixtureByDate(date);

		// HttpStatusCode code = isContentExist(responseDTO);
		HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, code);
	}

	// 리그/팀으로 경기 일정 조회
	@GetMapping(params = {"type", "keyword"})
	ResponseEntity<ResponseDTO> findFixtureByKeyword(@RequestParam(value = "type") String type,
		@RequestParam(value = "keyword") String keyword) throws
		UnsupportedEncodingException {

		ResponseDTO responseDTO = footballService.findFixtureByKeyWord(type, keyword);

		HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}

	// 파티에서 시청가능한 경기 조회
	@GetMapping(params = {"startDate", "endDate"})
	ResponseEntity<ResponseDTO> findFixtureByStartEndDate(@RequestParam(value = "startDate") String startDate,
		@RequestParam(value = "endDate") String endDate) {

		ResponseDTO responseDTO = footballService.findFixtureByStartEndDate(startDate, endDate);

		HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, code);

	}

	// 경기 일정을 키워드로 검색시, 자동 완성 기능
	@GetMapping("/search-example")
	ResponseEntity<ResponseDTO> findTeamOrLeagueyAsSearchExample(String keyword, String type) {

		ResponseDTO responseDTO = null;

		if (type.equals("팀")) {
			responseDTO = footballService.findTeamByKeyword(keyword);
		} else if (type.equals("리그")) {
			responseDTO = footballService.findLeagueByKeyword(keyword);
		} else {
			responseDTO = ResponseDTO.toDTO(null, "타입 입력이 잘못 되었습니다.");
			return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(400));
		}

		HttpStatusCode code = common.getStatusByContent(responseDTO);

		// if (responseDTO.getData() == null) {
		// 	return new ResponseEntity<>(responseDTO, HttpStatusCode.vlaueOf(404))
		// }

		return new ResponseEntity<>(responseDTO, code);
	}

}