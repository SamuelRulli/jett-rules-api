
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
public class InstructorsJettBookingResponse implements Serializable
{
    private final static long serialVersionUID = -1022020831345131025L;

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<InstructorsData> data;
    @SerializedName("meta")
    private List<Object> meta;
}
