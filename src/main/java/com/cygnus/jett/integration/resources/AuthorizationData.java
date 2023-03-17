package com.cygnus.jett.integration.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationData {
    @JsonProperty("token")
    private String token;
}
