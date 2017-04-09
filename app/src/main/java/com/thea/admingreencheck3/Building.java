package com.thea.admingreencheck3;

/**
 * Created by Thea on 30/03/2017.
 */


public class Building {
    public static final String TABLE_NAME = "Building";
    public static final String COL_ID = "building_id";
    public static final String COL_NAME = "name";
    public static final String  COL_ROOM= "room";
    public static final String COL_IS_ENABLED = "isEnabled";
    private String building_id;
    private String name;
    private Boolean isEnabled;



    public Building(){}

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
