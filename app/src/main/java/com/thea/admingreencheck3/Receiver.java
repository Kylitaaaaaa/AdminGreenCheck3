package com.thea.admingreencheck3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Receiver extends BroadcastReceiver
{
    private DatabaseReference mDatabaseTemplateAttendance = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
    private DatabaseReference mDatabaseAdminAttendance =  FirebaseDatabase.getInstance().getReference().child(Attendance.TABLE_NAME_ADMIN);

    private DatabaseReference mDatabaseC = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
    private DatabaseReference mDatabaseF = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);
    private DatabaseReference mDatabaseR = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);
    private DatabaseReference mDatabaseB = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);
    @Override
    public void onReceive(Context context, Intent intent){
        Log.i("huh", "Starting here");

        mDatabaseTemplateAttendance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("huh", "Starting here 1");

                DataSnapshot trysnap = dataSnapshot;
                Iterable<DataSnapshot> tryChildren = trysnap.getChildren();

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                for (DataSnapshot ty : tryChildren) {
                    final CourseOffering huh = ty.getValue(CourseOffering.class);
                    String days = huh.getDays();
                    Boolean isToday = false;

                    if(days != null) {

                        switch (day) {
                            //get for following day
                            case Calendar.MONDAY:
                                if (days.indexOf('M') >= 0) {
                                    isToday = true;
                                }
                                break;
                            case Calendar.TUESDAY:
                                if (days.indexOf('T') >= 0) {
                                    isToday = true;
                                }
                                break;
                            case Calendar.WEDNESDAY:
                                if (days.indexOf('W') >= 0) {
                                    isToday = true;
                                }
                                break;
                            case Calendar.THURSDAY:
                                if (days.indexOf('H') >= 0) {
                                    isToday = true;
                                }
                                break;
                            case Calendar.FRIDAY:
                                if (days.indexOf('F') >= 0) {
                                    isToday = true;
                                }
                                break;
                            case Calendar.SATURDAY:
                                if (days.indexOf('S') >= 0) {
                                    isToday = true;
                                }
                                break;
                        }
                    }

                    if(isToday){
                        final DatabaseReference newThing = mDatabaseAdminAttendance.push();
                        String co_id = huh.getCourseoffering_id();
                        newThing.child(Attendance.COL_co_id).setValue(co_id);

                        String f_id = huh.getFaculty_id();
                        newThing.child(Attendance.COL_f_id).setValue(f_id);

                        mDatabaseF.child(f_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String first_name = (String) dataSnapshot.child(Faculty.COL_FNAME).getValue();
                                String last_name = (String) dataSnapshot.child(Faculty.COL_LNAME).getValue();
                                String college = (String) dataSnapshot.child(Faculty.COL_COLLEGE).getValue();
                                String email = (String) dataSnapshot.child(Faculty.COL_EMAIL).getValue();
                                String pic = (String) dataSnapshot.child(Faculty.COL_PIC).getValue();

                                newThing.child(Attendance.COL_facultyName).setValue(first_name + " " + last_name);
                                newThing.child(Attendance.COL_college).setValue(college);
                                newThing.child(Attendance.COL_email).setValue(email);
                                newThing.child(Attendance.COL_pic).setValue(pic);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                        String c_id = huh.getCourse_id();
                        newThing.child(Attendance.COL_c_id).setValue(c_id);

                        mDatabaseC.child(c_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String ccode = (String) dataSnapshot.child(Course.COL_CODE).getValue();
                                String cname = (String) dataSnapshot.child(Course.COL_NAME).getValue();

                                newThing.child(Attendance.COL_courseCode).setValue(ccode);
                                newThing.child(Attendance.COL_courseName).setValue(cname);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                        String r_id = huh.getRoom_id();
                        newThing.child(Attendance.COL_r_id).setValue(r_id);

                        mDatabaseR.child(r_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String room = (String) dataSnapshot.child(Room.COL_NAME).getValue();
                                String rot_id = (String) dataSnapshot.child(Room.COL_ROT_ID).getValue();

                                newThing.child(Attendance.COL_room).setValue(room);
                                newThing.child(Attendance.COL_rotationId).setValue(rot_id);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                        String b_id = huh.getRoom_id();
                        newThing.child(Attendance.COL_b_id).setValue(b_id);

                        mDatabaseB.child(b_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String bldg = (String) dataSnapshot.child(Building.COL_NAME).getValue();

                                newThing.child(Attendance.COL_building).setValue(bldg);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                        newThing.child(Attendance.COL_startHour).setValue(huh.getStart_hour());
                        newThing.child(Attendance.COL_startMin).setValue(huh.getStart_min());

                        newThing.child(Attendance.COL_endHour).setValue(huh.getEnd_hour());
                        newThing.child(Attendance.COL_endMin).setValue(huh.getEnd_min());

                        newThing.child(Attendance.COL_days).setValue(huh.getDays());

                        String templateID = huh.getCourseoffering_id();

                        newThing.child(Attendance.COL_attendance_template_id).setValue(templateID);

                        //DatabaseReference pushToTemplate = mDatabaseTemplateAttendance.child(templateID).child("Attendance").child(newThing.getKey());

                        newThing.child(Attendance.COL_status).setValue("PENDING");
                        newThing.child(Attendance.COL_remarks).setValue("");
                        newThing.child(Attendance.COL_code).setValue("CHECKERERROR");

                        calendar.set(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                huh.getStart_hour(),
                                huh.getStart_min(),
                                0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        newThing.child(Attendance.COL_startTime).setValue(calendar.getTimeInMillis());

                        calendar.set(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                huh.getEnd_hour(),
                                huh.getEnd_min(),
                                0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        newThing.child(Attendance.COL_endTime).setValue(calendar.getTimeInMillis());
                    }
                }
                Log.i("huh", "done");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

        Toast.makeText(context, "Successfully Created Attendance for Today", Toast.LENGTH_LONG).show();



    }

}
