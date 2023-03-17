
package com.cygnus.jett.integration.resources;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorsData implements Serializable
{
    private final static long serialVersionUID = -1817647419893188597L;

    @SerializedName("id")
    private String id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("perHourPrice")
    private Integer perHourPrice;
    @SerializedName("perRequestPrice")
    private Integer perRequestPrice;
    @SerializedName("active")
    private Boolean active;
}
