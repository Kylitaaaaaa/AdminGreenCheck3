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

    private String room_id;
    private String building_id;
    private String building_name;
    private String name;

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
}
