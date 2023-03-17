package com.cygnus.jett.integration.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationJettIntegration {
    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private AuthorizationData data;
    @JsonProperty("meta")
    private List<String> meta;
}
