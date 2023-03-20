package com.cygnus.jett.schedules;

import com.cygnus.jett.repository.JettScoreRankingRepository;
import com.cygnus.jett.repository.entities.JettScoreRankingEntity;
import com.cygnus.jett.rules.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JettScoreRankingSchedule {
    private final static Logger LOGGER = LoggerFactory.getLogger(JettScoreRankingSchedule.class);
    @Autowired
    private final Rules rules;
    @Autowired
    private final JettScoreRankingRepository jettScoreRankingRepository;

    public JettScoreRankingSchedule(Rules rules, JettScoreRankingRepository jettScoreRankingRepository) {
        this.rules = rules;
        this.jettScoreRankingRepository = jettScoreRankingRepository;
    }
    @Async
    @Scheduled(fixedRate = 1000)
    void run(){
        LOGGER.info("stage=init method=run");

        List<JettScoreRankingEntity> jettScoreRanking = this.jettScoreRankingRepository.findAll();

        this.rules.executeRateInstructor(null, null);
        LOGGER.info("stage=end method=run");
    }

}
