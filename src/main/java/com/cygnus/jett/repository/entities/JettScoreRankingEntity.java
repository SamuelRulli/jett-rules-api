package com.cygnus.jett.repository.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "jt_score_ranking")
public class JettScoreRankingEntity implements Serializable {
        @Id
        @Column(name = "userid")
        private String UserId;
        @Column(name = "google_rate")
        private Integer GoogleRate;
        @Column(name = "clock_in_on_time")
        private Boolean ClockInOnTime;
        @Column(name = "clock_out_on_time")
        private Boolean ClockOutOnTime;
        @Column(name = "booking_date")
        private Date BookingDate;
}
