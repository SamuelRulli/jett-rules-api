
package com.cygnus.jett.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorsRankRequest {

    @JsonProperty("reservation_date")
    private String reservation_date;
    @JsonProperty("school_id")
    private String school_id;
}
