package com.thea.admingreencheck3;

/**
 * Created by Thea on 05/04/2017.
 */

public class Checker {
    public static final String TABLE_NAME = "Users";
    public static final String COL_NAME = "name";
    public static final String COL_ROT_ID = "rotation_id";
    public static final String COL_C_ID = "checker_id";
    public static final String COL_PIC = "image";

    private String name;
    private String rotation_id;
    private String checker_id;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRotation_id() {
        return rotation_id;
    }

    public void setRotation_id(String rotation_id) {
        this.rotation_id = rotation_id;
    }

    public String getChecker_id() {
        return checker_id;
    }

    public void setChecker_id(String checker_id) {
        this.checker_id = checker_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
