package com.thea.admingreencheck3;

/**
 * Created by Thea on 01/04/2017.
 */

public class AcademicYear {
    public static final String TABLE_NAME = "AcademicYear";
    public static final String COL_ACAD_ID = "acad_id";
    public static final String COL_ACAD_START = "acad_start";
    public static final String COL_ACAD_END = "acad_end";
    public static final String COL_TERM = "term";

    private String acad_id, acad_start, acad_end;

    public String getAcad_id() {
        return acad_id;
    }

    public void setAcad_id(String acad_id) {
        this.acad_id = acad_id;
    }

    public String getAcad_start() {
        return acad_start;
    }

    public void setAcad_start(String acad_start) {
        this.acad_start = acad_start;
    }

    public String getAcad_end() {
        return acad_end;
    }

    public void setAcad_end(String acad_end) {
        this.acad_end = acad_end;
    }
}
