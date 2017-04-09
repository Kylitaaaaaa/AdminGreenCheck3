package com.thea.admingreencheck3;

/**
 * Created by Thea on 30/03/2017.
 */

public class Room {
    public static final String TABLE_NAME = "Room";
    public static final String COL_ROOM_ID = "room_id";
    public static final String COL_BUILDING_ID = "building_id";
    public static final String COL_BUILDING_NAME = "building_name";
    public static final String COL_NAME = "name";
    public static final String COL_ROT_ID = "rot_id";
    public static final String COL_IS_ENABLED = "isEnabled";

    private String room_id;
    private String building_id;
    private String building_name;
    private String name;
    private String rot_id;
    private Boolean isEnabled;

    public Room(){}

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRot_id() {
        return rot_id;
    }

    public void setRot_id(String rot_id) {
        this.rot_id = rot_id;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
