package com.example.vet;

import com.google.gson.annotations.SerializedName;

public class ImageClass {

    @SerializedName("vetid")
    String Vetid;
    @SerializedName("image")
    String Image;


    @SerializedName("response")
    String Response;

    public String getResponse() {
        return Response;
    }
}
