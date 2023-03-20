
package com.cygnus.jett.controller.request;

import com.cygnus.jett.constants.RankTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RateInstructorRequest {

    @JsonProperty("rank_type")
    private RankTypeEnum rank_type;
    @JsonProperty("instructor_id")
    private String instructor_id;
    @JsonProperty("reservation_date")
    private Date reservation_date;
    @JsonProperty("google_rate")
    private Integer google_rate;
    @JsonProperty("clock_in_on_time")
    private Boolean clockInOnTime;
    @JsonProperty("clock_out_on_time")
    private Boolean clockOutOnTime;
}
