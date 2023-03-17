package com.cygnus.jett.service;

import com.cygnus.jett.controller.request.InstructorsRankRequest;
import com.cygnus.jett.integration.resources.InstructorsJettBookingResponse;

public interface RuleService {

    InstructorsJettBookingResponse checkInstructorRank(final InstructorsRankRequest request);

}
