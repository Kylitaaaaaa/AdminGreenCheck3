package com.thea.admingreencheck3;

/**
 * Created by Thea on 05/04/2017.
 */

public class Checker {
    public static final String TABLE_NAME = "Users";
    public static final String COL_NAME = "name";
    public static final String COL_ROT_ID = "rotationId";
    public static final String COL_C_ID = "checker_id";
    public static final String COL_PIC = "image";


    private String name;
    private String rotationId;
    private String checker_id;
    private String image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRotationId() {
        return rotationId;
    }

    public void setRotationId(String rotationId) {
        this.rotationId = rotationId;
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
