package com.thea.admingreencheck3;

/**
 * Created by Thea on 30/03/2017.
 */

public class Course {
    private String id;
    private String code;
    private String name;
    private Boolean isEnabled;

    public static final String TABLE_NAME = "Course";
    public static final String COL_ID = "id";
    public static final String COL_CODE = "code";
    public static final String COL_NAME = "name";
    public static final String COL_IS_ENABLED = "isEnabled";

    public Course(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
