package com.thea.admingreencheck3;

/**
 * Created by Thea on 05/04/2017.
 */

public class Checker {
    public static final String TABLE_NAME = "Checker";
    public static final String COL_EMAIL = "email";
    public static final String COL_ROT_ID = "rotation_id";
    public static final String COL_C_ID = "checker_id";

    private String email;
    private String rotation_id;
    private String checker_id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
