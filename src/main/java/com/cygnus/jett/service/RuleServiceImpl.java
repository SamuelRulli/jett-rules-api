package com.cygnus.jett.service;

import com.cygnus.jett.controller.request.InstructorsRankRequest;
import com.cygnus.jett.integration.api.JettBookingIntegrationClient;
import com.cygnus.jett.integration.resources.AuthorizationJettIntegration;
import com.cygnus.jett.integration.resources.InstructorsJettBookingResponse;
import com.cygnus.jett.integration.resources.ReservationJettBookingResponse;
import com.cygnus.jett.rules.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleServiceImpl implements RuleService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RuleServiceImpl.class);

    @Autowired
    private final Rules rules;

    public RuleServiceImpl(Rules rules) {
        this.rules = rules;
    }

    @Override
    public InstructorsJettBookingResponse checkInstructorRank(InstructorsRankRequest request) {
        LOGGER.info("stage=init method=createScore request={}", request);
        Integer score;
        InstructorsJettBookingResponse instructorsJettBookingResponse;
        ReservationJettBookingResponse reservationJettBookingResponse;

        try {
            AuthorizationJettIntegration jwtToken =
                    JettBookingIntegrationClient.getAuthorizationToken();

            instructorsJettBookingResponse =
                    JettBookingIntegrationClient.getInstructors(jwtToken.getData().getToken());

            reservationJettBookingResponse =
                    JettBookingIntegrationClient.getReservations(jwtToken.getData().getToken(), request.getReservation_date());

            LOGGER.info("stage=response response={} ", reservationJettBookingResponse.getStatus());

            score = this.rules.executeRankInstructor(instructorsJettBookingResponse, reservationJettBookingResponse);

        } catch (Exception e) {
            LOGGER.error("stage=error method=createScore Error to execute rule engine! " + e);
            throw new RuntimeException(e);
        }
        LOGGER.info("stage=end method=createScore score={}", score);

        return instructorsJettBookingResponse;
    }
}
