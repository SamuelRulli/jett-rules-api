package com.cygnus.jett.bussiness;

import com.cygnus.jett.controller.request.RateInstructorRequest;
import com.cygnus.jett.repository.JettScoreRankingRepository;
import com.cygnus.jett.repository.entities.JettScoreRankingEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class JettScoreRankingImpl implements JettScoreRankingService{

    private final ApplicationContext appContext;

    private JettScoreRankingEntity jettScoreRanking;

    public JettScoreRankingImpl(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public void init(final RateInstructorRequest request){

        switch (request.getRank_type()){
            case GOOGLE_RATE:
                saveGoogleRate(request);
                break;
            case CLOCK_IN:
                saveClockInOnTime(request);
                break;
            case CLOCK_OUT:
                saveClockOutOnTime(request);
                break;
        }

    }

    private void saveGoogleRate(final RateInstructorRequest request){
        jettScoreRanking = new JettScoreRankingEntity();

        jettScoreRanking.setGoogleRate(request.getGoogle_rate());
        jettScoreRanking.setBookingDate(request.getReservation_date());
        jettScoreRanking.setUserId(request.getInstructor_id());

        this.appContext.getBean(JettScoreRankingRepository.class).save(jettScoreRanking);
    }

    private void saveClockInOnTime(final RateInstructorRequest request){
        jettScoreRanking = new JettScoreRankingEntity();

        jettScoreRanking.setBookingDate(request.getReservation_date());
        jettScoreRanking.setUserId(request.getInstructor_id());
        jettScoreRanking.setClockInOnTime(request.getClockInOnTime()!=Boolean.TRUE?Boolean.FALSE:Boolean.TRUE);

        this.appContext.getBean(JettScoreRankingRepository.class).save(jettScoreRanking);
    }

    private void saveClockOutOnTime(final RateInstructorRequest request){
        jettScoreRanking = new JettScoreRankingEntity();

        jettScoreRanking.setBookingDate(request.getReservation_date());
        jettScoreRanking.setUserId(request.getInstructor_id());
        jettScoreRanking.setClockOutOnTime(request.getClockOutOnTime()!=Boolean.TRUE?Boolean.FALSE:Boolean.TRUE);

        this.appContext.getBean(JettScoreRankingRepository.class).save(jettScoreRanking);
    }


}
