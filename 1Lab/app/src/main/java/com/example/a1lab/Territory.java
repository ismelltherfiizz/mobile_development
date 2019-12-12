package com.example.a1lab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Territory {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("square")
    @Expose
    private Integer square;
    @SerializedName("detour_time")
    @Expose
    private Integer detourTime;
    @SerializedName("territory_picture")
    @Expose
    private String territoryPicture;

    public Territory(String name, Integer square, Integer detourTime, String territoryPicture) {
        this.name = name;
        this.square = square;
        this.detourTime = detourTime;
        this.territoryPicture = territoryPicture;
    }

    public Territory(String name, Integer square, Integer detourTime ) {
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

    public String getTerritoryPicture() {
        return territoryPicture;
    }

}
