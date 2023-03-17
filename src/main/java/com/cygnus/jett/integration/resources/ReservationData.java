
package com.cygnus.jett.integration.resources;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationData implements Serializable
{
    private final static long serialVersionUID = 7224427381580830821L;

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("abbreviation")
    private String abbreviation;
    @SerializedName("reservations")
    private List<Object> reservations;
}
