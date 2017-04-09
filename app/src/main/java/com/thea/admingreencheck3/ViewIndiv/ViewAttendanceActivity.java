package com.thea.admingreencheck3.ViewIndiv;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Add.AddEditAttendanceActivity;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Attendance;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.IDClass;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;

public class ViewAttendanceActivity extends AppCompatActivity {
    String huh_id;
    private DatabaseReference mDatabase;

    private ImageView imgFac;
    private TextView facultyName, facultyCourse, facultyRoom, classTime, remarkField;
    private Button abBtn, edBtn, laBtn, prBtn, sbBtn, swBtn, usBtn, vrBtn, ceBtn;

    private Boolean ab = false,
            ed = false,
            la = false,
            pr = false,
            sb = false,
            sw = false,
            us = false,
            vr = false,
            ce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        getSupportActionBar().setTitle("Attendance");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Attendance.TABLE_NAME_ADMIN);

        huh_id = getIntent().getExtras().getString(Attendance.COL_attendance_template_id);
        Log.i("huh", "id: " + huh_id);

        imgFac = (ImageView) findViewById(R.id.facultyImage);

        facultyName = (TextView) findViewById(R.id.facultyName);
        facultyCourse = (TextView) findViewById(R.id.facultyCourse);
        facultyRoom = (TextView) findViewById(R.id.facultyRoom);
        classTime = (TextView) findViewById(R.id.classTime);
        remarkField = (TextView) findViewById(R.id.remarkField);


        abBtn = (Button) findViewById(R.id.abBtn);
        edBtn = (Button) findViewById(R.id.edBtn);
        laBtn = (Button) findViewById(R.id.laBtn);
        prBtn = (Button) findViewById(R.id.prBtn);
        sbBtn = (Button) findViewById(R.id.sbBtn);
        swBtn = (Button) findViewById(R.id.swBtn);
        usBtn = (Button) findViewById(R.id.usBtn);
        vrBtn = (Button) findViewById(R.id.vrBtn);
        ceBtn = (Button) findViewById(R.id.ceBtn);

        mDatabase.child(huh_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = (String) dataSnapshot.child(Attendance.COL_facultyName).getValue();
                String ccode = (String) dataSnapshot.child(Attendance.COL_courseCode).getValue();
                String room = (String) dataSnapshot.child(Attendance.COL_room).getValue();
                String remarks = (String) dataSnapshot.child(Attendance.COL_remarks).getValue();
                String code = (String) dataSnapshot.child(Attendance.COL_code).getValue();
                String pic = (String) dataSnapshot.child(Attendance.COL_pic).getValue();

                if(name != null &&
                        ccode != null &&
                        code != null &&
                        room != null &&
                        pic != null) {


                    facultyName.setText(name);
                    facultyCourse.setText(ccode);
                    facultyRoom.setText(room);
                    classTime.setText(dataSnapshot.child(Attendance.COL_startHour).getValue() + " : " + dataSnapshot.child(Attendance.COL_startMin).getValue()
                            + " - " + dataSnapshot.child(Attendance.COL_endHour).getValue() + " : " + dataSnapshot.child(Attendance.COL_endMin).getValue());
                    if(remarks == "")
                        remarkField.setText("No remarks");
                    else
                        remarkField.setText(remarks);

                    resetButtons();

                    if(code.equals("Absent")) {
                        abBtn.setBackgroundColor(Color.parseColor("#087830"));
                        abBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else if(code.equals("Early Dismissal")){
                        edBtn.setBackgroundColor(Color.parseColor("#087830"));
                        edBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else if(code.equals("Late")){
                        laBtn.setBackgroundColor(Color.parseColor("#087830"));
                        laBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else if(code.equals("Present")){
                        prBtn.setBackgroundColor(Color.parseColor("#087830"));
                        prBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else if(code.equals("Substitute")){
                        sbBtn.setBackgroundColor(Color.parseColor("#087830"));
                        sbBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else if(code.equals("Seatwork")){
                        swBtn.setBackgroundColor(Color.parseColor("#087830"));
                        swBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else if(code.equals("Unscheduled")){
                        usBtn.setBackgroundColor(Color.parseColor("#087830"));
                        usBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else if(code.equals("Vacant Room")){
                        vrBtn.setBackgroundColor(Color.parseColor("#087830"));
                        vrBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    else{
                        ceBtn.setBackgroundColor(Color.parseColor("#087830"));
                        ceBtn.setTextColor(Color.parseColor("#FFFFFF"));
                        ce = true;
                    }


                    Picasso.with(getBaseContext()).load(pic).into(imgFac);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void resetButtons(){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));

        ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
        ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_icon_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id2 = item.getItemId();

        if (id2 == R.id.action_edit) {
            Intent i = new Intent(getBaseContext(), AddEditAttendanceActivity.class);
            //add = 0
            //edit = 1
            i.putExtra("currProcess", 1);
            Log.i("huh", "passing " + huh_id);
            i.putExtra(Attendance.COL_attendance_template_id, huh_id);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
