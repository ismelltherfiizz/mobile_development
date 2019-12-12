package com.example.a1lab;

import com.google.gson.annotations.SerializedName;

public class Territory {

    @SerializedName("name")
    private String name;
    @SerializedName("square")
    private Integer square;
    @SerializedName("detour_time")
    private Integer detourTime;

    public Territory(String name, Integer square, Integer detourTime) {
        this.name = name;
        this.square = square;
        this.detourTime = detourTime;
    }

    public String getName() {

        return name;
    }

    public Integer getSquare() {

        return square;
    }

    public Integer getDetourTime() {
        return detourTime;
    }

}
