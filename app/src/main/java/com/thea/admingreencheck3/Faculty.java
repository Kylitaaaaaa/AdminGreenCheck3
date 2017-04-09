package com.thea.admingreencheck3;

/**
 * Created by avggo on 10/9/2016.
 */

public class Faculty {
    public static final String TABLE_NAME = "Faculty";
    public static final String COL_ID = "id";
    public static final String COL_FNAME = "first_name";
    public static final String COL_MNAME = "middle_name";
    public static final String COL_LNAME = "last_name";
    public static final String COL_COLLEGE = "college";
    public static final String COL_EMAIL = "email";
    public static final String COL_PIC = "pic";
    public static final String COL_MOBNUM = "mobile_number";
    public static final String COL_DEPT = "department";
    public static final String COL_IS_ENABLED = "isEnabled";

    private String first_name, middle_name, last_name, college, email, pic, mobile_number, department, id;
    private Boolean isEnabled;

    public Faculty() {
    }

    public Faculty(String first_name, String middle_name, String last_name, String college, String email, String pic, String mobile_number, String department) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.college = college;
        this.email = email;
        this.pic = pic;
        this.mobile_number = mobile_number;
        this.department = department;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
