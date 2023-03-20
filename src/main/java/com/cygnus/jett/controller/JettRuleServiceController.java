package com.cygnus.jett.controller;

import com.cygnus.jett.bussiness.JettScoreRankingService;
import com.cygnus.jett.controller.request.InstructorsRankRequest;
import com.cygnus.jett.controller.request.RateInstructorRequest;
import com.cygnus.jett.controller.response.InstructorsAvailableResponse;
import com.cygnus.jett.integration.resources.InstructorsJettBookingResponse;
import com.cygnus.jett.repository.mapper.InstructorsAvailableResponseMapper;
import com.cygnus.jett.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "*", methods = { POST })
@RequestMapping(value = "/v1", produces = APPLICATION_JSON_VALUE)
public class JettRuleServiceController {
    private final RuleService ruleService;
    private final JettScoreRankingService jettScoreRanking;
    public JettRuleServiceController(final RuleService ruleService, final JettScoreRankingService jettScoreRanking) {
        this.ruleService = ruleService;
        this.jettScoreRanking = jettScoreRanking;
    }
    @PostMapping("/rank/instructor")
    public ResponseEntity<List<InstructorsAvailableResponse>> getRankInstructor(@RequestBody InstructorsRankRequest request) {
        InstructorsJettBookingResponse instructorsAvailableResponse = this.ruleService.checkInstructorRank(request);
        List<InstructorsAvailableResponse> response = InstructorsAvailableResponseMapper.INSTANCE.toInstructorsAvailableResponse(instructorsAvailableResponse);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/rate/instructor")
    public ResponseEntity<List<InstructorsAvailableResponse>> rateInstructor(@RequestBody RateInstructorRequest request) {
        this.jettScoreRanking.init(request);
        return ResponseEntity.ok().body(null);
    }

}
