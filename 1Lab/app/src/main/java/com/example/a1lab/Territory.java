package com.example.a1lab;

import com.google.gson.annotations.SerializedName;

public class Territory {

    @SerializedName("name")
    private String name;
    @SerializedName("square")
    private Integer square;
    @SerializedName("detour_time")
    private Integer detourTime;
    @SerializedName("territory_picture")
    private String territoryPicture;

    public Territory(String name, Integer square, Integer detourTime, String territoryPicture) {
        this.name = name;
        this.square = square;
        this.detourTime = detourTime;
        this.territoryPicture = territoryPicture;
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

    public String getTerritoryPicture() {
        return territoryPicture;
    }

}
