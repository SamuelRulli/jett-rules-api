
package com.cygnus.jett.controller.request;

import com.cygnus.jett.constants.RankTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateInstructorRequest {

    @JsonProperty("rank_type")
    private RankTypeEnum rank_type;
    @JsonProperty("instructor_id")
    private String instructor_id;
    @JsonProperty("reservation_id")
    private String reservation_id;
}
