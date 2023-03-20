package com.cygnus.jett.rules;

import com.cygnus.jett.commons.CommonsUtils;
import static com.cygnus.jett.constants.Constants.INSTRUCTOR_FILE_NAME;
import static com.cygnus.jett.constants.Constants.RATE_INSTRUCTOR_FILE_NAME;

import com.cygnus.jett.integration.resources.InstructorsJettBookingResponse;
import com.cygnus.jett.integration.resources.ReservationJettBookingResponse;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.JsonRuleDefinitionReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;

import static com.cygnus.jett.constants.Constants.COMMONS;
import static com.cygnus.jett.constants.Constants.RESERVATION;
import static com.cygnus.jett.constants.Constants.INSTRUCTORS;
@Component
public class Rules {
    private final static Logger LOGGER = LoggerFactory.getLogger(Rules.class);

    private CommonsUtils commons;

    public Integer executeRankInstructor (final InstructorsJettBookingResponse instructors, final ReservationJettBookingResponse reservations){
        LOGGER.info("stage=init method=executeRateInstructor");

        commons =  new CommonsUtils();

        Facts facts = new Facts();

        facts.put(RESERVATION, reservations);
        facts.put(COMMONS, commons);
        facts.put(INSTRUCTORS, instructors);

        executeEngine(INSTRUCTOR_FILE_NAME, facts);

        LOGGER.info("stage=end method=executeRateInstructor commons.result={}", commons.result);
        return commons.result;
    }

    public Integer executeRateInstructor (final InstructorsJettBookingResponse instructors, final ReservationJettBookingResponse reservations){
        LOGGER.info("stage=init method=executeRateInstructor");
        commons =  new CommonsUtils();

        Facts facts = new Facts();

        facts.put(RESERVATION, reservations);
        facts.put(COMMONS, commons);

        executeEngine(RATE_INSTRUCTOR_FILE_NAME, facts);

        LOGGER.info("stage=end method=executeRateInstructor commons.result={}", commons.result);

        return commons.result;
    }

    public void executeEngine(final String ruleFileName, final Facts facts) {
        LOGGER.info("stage=init method=executeEngine ruleFileName={} facts={}", ruleFileName, facts);

        MVELRuleFactory ruleFactory = new MVELRuleFactory(new JsonRuleDefinitionReader());
        org.jeasy.rules.api.Rules rules = null;

        try {
            File file = ResourceUtils.getFile("classpath:rules/".concat(ruleFileName));
            rules = ruleFactory.createRules(new FileReader(file));
        } catch (Exception e) {
            LOGGER.info("stage=error method=executeEngine exception={}", "error=" + e);
        }

        RulesEngineParameters parameters = new RulesEngineParameters()
                //.skipOnFirstAppliedRule(true);
                .skipOnFirstFailedRule(false);
                //.skipOnFirstNonTriggeredRule(true);

        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        rulesEngine.fire(rules, facts);

        LOGGER.info("stage=end method=executeEngine");
    }
}
