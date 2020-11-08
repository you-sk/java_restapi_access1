package com.mycompany.mavenproject1.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("estimated_data")
    @Expose
    public EstimatedData estimatedData;

    //DB格納用情報
    public int requestTimestamp;
    public int responseTimestamp;
    public String imagePath;
}
