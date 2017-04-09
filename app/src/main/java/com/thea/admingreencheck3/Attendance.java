package com.thea.admingreencheck3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thea on 05/04/2017.
 */

public class Attendance {

    public static final String TABLE_NAME = "Attendance";
    public static final String TABLE_NAME_ADMIN = "AdminAttendance";

    public static final String COL_facultyName = "facultyName";
    public static final String COL_college = "college";
    public static final String COL_email = "email";
    public static final String COL_pic = "pic";

    public static final String COL_courseCode = "courseCode";
    public static final String COL_courseName = "courseName";

    public static final String COL_building = "building";
    public static final String COL_room = "room";
    public static final String COL_startTime = "startTime";
    public static final String COL_endTime = "endTime";

    public static final String COL_startHour = "startHour";
    public static final String COL_startMin = "startMin";

    public static final String COL_endHour = "endHour";
    public static final String COL_endMin = "endMin";

    public static final String COL_code = "code";
    public static final String COL_status = "status"; //SET TO PENDING
    public static final String COL_remarks = "remarks";

    public static final String COL_newRoom = "newRoom"; //SAME
    public static final String COL_subName = "subName";
    public static final String COL_subPic = "subPic";

    public static final String COL_date = "date";

    public static final String COL_rotationId = "rotationId";

    public static final String COL_startTimeFilter = "startTimeFilter";

    public static final String COL_days = "days";

    public static final String COL_attendance_template_id = "attendance_template_id";

    public static final String COL_c_id = "c_id";
    public static final String COL_f_id = "f_id";
    public static final String COL_r_id = "r_id";
    public static final String COL_b_id = "b_id";
    public static final String COL_co_id = "co_id";

    private String adminId;
    private HashMap<String, String> ids;

    private boolean isDone = false;
    //needed for firebase
    private List<String> combinationFilters;



    private String c_id;
    private String f_id;
    private String r_id;
    private String b_id;
    private String co_id;

    private String facultyName;
    private String room;
    private String courseCode;
    private String courseName;

    private int startHour;
    private int startMin;

    private int endHour;
    private int endMin;

    private String code;
    private String email;

    private String remarks;
    private String college;
    private String reason;
    public String subName;
    private String newRoom;
    private String subPic;
    private String pic;
    private String date;

    private String days;

    private String attendance_template_id;


    //filters
    private String rotationId;
    private String status;
    private String building;
    private long startTimeFilter;

    private long startTime;
    private long endTime;

    public Attendance() {
    }

    public Attendance(String facultyName) {
        this.facultyName = facultyName;
    }

    //hashmap is used to ensure no duplicates of this attendance in a table
    public void addId(String tableName, String id){
        if(ids == null)
            ids = new HashMap<String, String>();
        ids.put(tableName, id);
    }

    public boolean hasTable(String tableName){
        boolean hasTable = false;

        if(ids == null)
            ids = new HashMap<String, String>();

        for (Map.Entry<String, String> entry : ids.entrySet()) {
            String t = entry.getKey();
            if(t.equals(tableName))
                hasTable = true;
        }
        return hasTable;
    }

    public boolean sameAs(Attendance a){
        return adminId.equals(a.getAdminId());
    }

    public List<String> generateFilters() {
        combinationFilters = AttendanceUtils.getCombinationFilters(this);
        return combinationFilters;
    }

    public void getAllFilters(){
        String f1 = (rotationId == null) ? "_" : rotationId;//if (rotationid == null) return "_" else return rotationid;
        String f2 = (status == null) ? "_" : status;
        String f3 = (building == null) ? "_" : building;
        //String f4 = (startTimeFilter == null) ? "_" : Long.toString(startTimeFilter);

        //return f1 + "-" + f2 + "-" + f3 + "-" + f4;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public HashMap<String, String> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, String> ids) {
        this.ids = ids;
    }

    public String getName() {
        return facultyName;
    }

    public void setName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNewRoom() {
        return newRoom;
    }

    public void setNewRoom(String newRoom) {
        this.newRoom = newRoom;
    }

    public String getSubPic() {
        return subPic;
    }

    public void setSubPic(String subPic) {
        this.subPic = subPic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRotationId() {
        return rotationId;
    }

    public void setRotationId(String rotationId) {
        this.rotationId = rotationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public long getStartTimeFilter() {
        return startTimeFilter;
    }

    public void setStartTimeFilter(long startTimeFilter) {
        this.startTimeFilter = startTimeFilter;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAttendance_template_id() {
        return attendance_template_id;
    }

    public void setAttendance_template_id(String attendance_template_id) {
        this.attendance_template_id = attendance_template_id;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getCo_id() {
        return co_id;
    }

    public void setCo_id(String co_id) {
        this.co_id = co_id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public List<String> getCombinationFilters() {
        return combinationFilters;
    }

    public void setCombinationFilters(List<String> combinationFilters) {
        this.combinationFilters = combinationFilters;
    }
}
