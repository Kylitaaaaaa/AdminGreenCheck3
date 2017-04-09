package com.thea.admingreencheck3;

/**
 * Created by Thea on 01/04/2017.
 */
public class CourseOffering {
    public static final String TABLE_NAME = "CourseOffering";
    public static final String COL_CO_ID = "courseoffering_id";
    public static final String COL_START_HOUR = "start_hour";
    public static final String COL_START_MIN = "start_min";

    public static final String COL_END_HOUR = "end_hour";
    public static final String COL_END_MIN = "end_min";

    public static final String COL_SECTION = "section";
    public static final String COL_C_ID = "course_id";
    public static final String COL_F_ID = "faculty_id";
    public static final String COL_R_ID = "room_id";
    public static final String COL_B_ID = "building_id";
    public static final String COL_DAYS = "days";

    public static final String COL_IS_ENABLED = "isEnabled";
    public static final String COL_IS_ENABLED_INDIV = "isEnabledIndiv";

    private String courseoffering_id;
    private int start_hour;
    private int start_min;
    private int end_hour;
    private int end_min;
    private String course_id;
    private String faculty_id;
    private String room_id;
    private String building_id;
    private String days;
    private String section;
    private Boolean isEnabled;
    private Boolean isEnabledIndiv;

    public String getCourseoffering_id() {
        return courseoffering_id;
    }

    public void setCourseoffering_id(String courseoffering_id) {
        this.courseoffering_id = courseoffering_id;
    }


    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getStart_min() {
        return start_min;
    }

    public void setStart_min(int start_min) {
        this.start_min = start_min;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getEnd_min() {
        return end_min;
    }

    public void setEnd_min(int end_min) {
        this.end_min = end_min;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getEnabledIndiv() {
        return isEnabledIndiv;
    }

    public void setEnabledIndiv(Boolean enabledIndiv) {
        isEnabledIndiv = enabledIndiv;
    }
}
