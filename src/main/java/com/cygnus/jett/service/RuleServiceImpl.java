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
    private final JettBookingIntegrationClient jettBookingIntegrationClient;

    @Autowired
    private final Rules rules;

    public RuleServiceImpl(JettBookingIntegrationClient jettBookingIntegrationClient, Rules rules) {
        this.jettBookingIntegrationClient = jettBookingIntegrationClient;
        this.rules = rules;
    }

    @Override
    public InstructorsJettBookingResponse checkInstructorRank(InstructorsRankRequest request) {
        LOGGER.info("stage=init method=createScore request={}", request);
        Integer score = null;
        InstructorsJettBookingResponse instructorsJettBookingResponse = null;
        ReservationJettBookingResponse reservationJettBookingResponse = null;

        try {
            AuthorizationJettIntegration jwtToken =
                    this.jettBookingIntegrationClient.getAuthorizationToken();

            instructorsJettBookingResponse =
                    this.jettBookingIntegrationClient.getInstructors(jwtToken.getData().getToken());

            reservationJettBookingResponse =
                    this.jettBookingIntegrationClient.getReservations(jwtToken.getData().getToken(), request.getReservation_date());

            LOGGER.info("stage=response response={} ", reservationJettBookingResponse.getStatus());

            score = this.rules.execute(instructorsJettBookingResponse, reservationJettBookingResponse);

        } catch (Exception e) {
            LOGGER.error("stage=error method=createScore Error to execute rule engine! " + e);
            throw new RuntimeException(e);
        }
        LOGGER.info("stage=end method=createScore score={}", score);

        return instructorsJettBookingResponse;
    }
}
