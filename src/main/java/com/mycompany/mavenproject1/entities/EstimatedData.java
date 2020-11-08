package com.mycompany.mavenproject1.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class EstimatedData {

    @SerializedName("class")
    @Expose
    public Integer classInt;
    @SerializedName("confidence")
    @Expose
    public BigDecimal confidence;
}
