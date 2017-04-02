package com.thea.admingreencheck3;

/**
 * Created by Thea on 01/04/2017.
 */
public class CourseOffering {
    public static final String TABLE_NAME = "CourseOffering";
    public static final String COL_COURSEOFFERING_ID = "courseoffering_id";
    public static final String COL_COURSEOFFERING_TIME_START = "time_start";
    public static final String COL_COURSEOFFERING_TIME_END = "time_end";
    public static final String COL_COURSEOFFERING_SECTION = "section";
    public static final String COL_COURSEOFFERING_M = "day_M";
    public static final String COL_COURSEOFFERING_T = "day_T";
    public static final String COL_COURSEOFFERING_W = "day_W";
    public static final String COL_COURSEOFFERING_H = "day_H";
    public static final String COL_COURSEOFFERING_F = "day_F";
    public static final String COL_COURSEOFFERING_S = "day_S";
    public static final String COL_COURSEOFFERING_COURSECODE = "course_code";



    public static final String COL_ROOM = Room.TABLE_NAME;
    public static final String COL_FACULTY = Faculty.TABLE_NAME;
    public static final String COL_COURSE = Course.TABLE_NAME;
    public static final String COL_TERM = Term.TABLE_NAME;

    private String courseoffering_id;
    private String time_start;
    private String time_end;
    private String section;
    private String course_code;
    private Boolean day_M;
    private Boolean day_T;
    private Boolean day_W;
    private Boolean day_H;
    private Boolean day_F;
    private Boolean day_S;


    public String getCourseoffering_id() {
        return courseoffering_id;
    }

    public void setCourseoffering_id(String courseoffering_id) {
        this.courseoffering_id = courseoffering_id;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Boolean getDay_M() {
        return day_M;
    }

    public void setDay_M(Boolean day_M) {
        this.day_M = day_M;
    }

    public Boolean getDay_T() {
        return day_T;
    }

    public void setDay_T(Boolean day_T) {
        this.day_T = day_T;
    }

    public Boolean getDay_W() {
        return day_W;
    }

    public void setDay_W(Boolean day_W) {
        this.day_W = day_W;
    }

    public Boolean getDay_H() {
        return day_H;
    }

    public void setDay_H(Boolean day_H) {
        this.day_H = day_H;
    }

    public Boolean getDay_F() {
        return day_F;
    }

    public void setDay_F(Boolean day_F) {
        this.day_F = day_F;
    }

    public Boolean getDay_S() {
        return day_S;
    }

    public void setDay_S(Boolean day_S) {
        this.day_S = day_S;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }
}
