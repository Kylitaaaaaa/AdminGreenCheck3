package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Attendance;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Checker;
import com.thea.admingreencheck3.GenListActivity;
import com.thea.admingreencheck3.IDClass;
import com.thea.admingreencheck3.Joint;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditAttendanceActivity extends AppCompatActivity {

    String id;
    private DatabaseReference mDatabase;

    private ImageView imgFac;
    private TextView facultyName, facultyCourse, facultyRoom, classTime, remarkField, tvDate;
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
    String rotid;
    int currProcess;
    String code;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_attendance);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Attendance.TABLE_NAME_ADMIN);

        id = getIntent().getExtras().getString(Attendance.COL_attendance_template_id);
        Log.i("huh", "id: " + id);

        imgFac = (ImageView) findViewById(R.id.facultyImage);

        facultyName = (TextView) findViewById(R.id.facultyName);
        facultyCourse = (TextView) findViewById(R.id.facultyCourse);
        facultyRoom = (TextView) findViewById(R.id.facultyRoom);
        classTime = (TextView) findViewById(R.id.classTime);
        remarkField = (TextView) findViewById(R.id.remarkField);
        tvDate = (TextView) findViewById(R.id.tv_date);


        abBtn = (Button) findViewById(R.id.abBtn);
        edBtn = (Button) findViewById(R.id.edBtn);
        laBtn = (Button) findViewById(R.id.laBtn);
        prBtn = (Button) findViewById(R.id.prBtn);
        sbBtn = (Button) findViewById(R.id.sbBtn);
        swBtn = (Button) findViewById(R.id.swBtn);
        usBtn = (Button) findViewById(R.id.usBtn);
        vrBtn = (Button) findViewById(R.id.vrBtn);
        ceBtn = (Button) findViewById(R.id.ceBtn);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 1 ) {
            getSupportActionBar().setTitle("Edit Attendance");


            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child(Attendance.COL_facultyName).getValue();
                    String ccode = (String) dataSnapshot.child(Attendance.COL_courseCode).getValue();
                    String room = (String) dataSnapshot.child(Attendance.COL_room).getValue();
                    String remarks = (String) dataSnapshot.child(Attendance.COL_remarks).getValue();
                    String code = (String) dataSnapshot.child(Attendance.COL_code).getValue();
                    String pic = (String) dataSnapshot.child(Attendance.COL_pic).getValue();
                    long starttime = (long) dataSnapshot.child(Attendance.COL_startTime).getValue();
                    long endtime = (long) dataSnapshot.child(Attendance.COL_endTime).getValue();

                    if(name != null &&
                            ccode != null &&
                            code != null &&
                            room != null &&
                            pic != null) {


                        facultyName.setText(name);
                        facultyCourse.setText(ccode);
                        facultyRoom.setText(room);

                        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
                        Date dstart = new Date(starttime);
                        String startt = formatter.format(dstart);

                        Date dend = new Date(endtime);
                        String endt = formatter.format(dend);

                        classTime.setText(startt + " - " + endt);

                        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
                        String datetv = formatter2.format(dstart);

                        tvDate.setText(datetv);

                        if(remarks == "")
                            remarkField.setText("No remarks");
                        else
                            remarkField.setText(remarks);

                        if(code.equals("Absent")) {
                            abBtn.setBackgroundColor(Color.parseColor("#087830"));
                            abBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            ab = true;
                        }
                        else if(code.equals("Early Dismissal")){
                            edBtn.setBackgroundColor(Color.parseColor("#087830"));
                            edBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            ed = true;
                        }
                        else if(code.equals("Late")){
                            laBtn.setBackgroundColor(Color.parseColor("#087830"));
                            laBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            la = true;
                        }
                        else if(code.equals("Present")){
                            prBtn.setBackgroundColor(Color.parseColor("#087830"));
                            prBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            pr = true;
                        }
                        else if(code.equals("Substitute")){
                            sbBtn.setBackgroundColor(Color.parseColor("#087830"));
                            sbBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            sb = true;
                        }
                        else if(code.equals("Seatwork")){
                            swBtn.setBackgroundColor(Color.parseColor("#087830"));
                            swBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            sw = true;
                        }
                        else if(code.equals("Unscheduled")){
                            usBtn.setBackgroundColor(Color.parseColor("#087830"));
                            usBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            us = true;
                        }
                        else if(code.equals("Vacant Room")){
                            vrBtn.setBackgroundColor(Color.parseColor("#087830"));
                            vrBtn.setTextColor(Color.parseColor("#FFFFFF"));
                            vr = true;
                        }
                        else if(code.equals("CHECKERERROR")){
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
        else{
            getSupportActionBar().setTitle("Add Attendance");
        }



        abBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    abBtn.setBackgroundColor(Color.parseColor("#087830"));
                    abBtn.setTextColor(Color.parseColor("#ffffff"));
                    ab = true;
                    unpressOthers("AB");
            }
        });

        edBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    edBtn.setBackgroundColor(Color.parseColor("#087830"));
                    edBtn.setTextColor(Color.parseColor("#ffffff"));

                    ed = true;
                    unpressOthers("ED");
            }
        });

        laBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    laBtn.setBackgroundColor(Color.parseColor("#087830"));
                    laBtn.setTextColor(Color.parseColor("#ffffff"));

                    la = true;
                    unpressOthers("LA");
            }
        });


        prBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    prBtn.setBackgroundColor(Color.parseColor("#087830"));
                    prBtn.setTextColor(Color.parseColor("#ffffff"));

                    pr = true;
                    unpressOthers("PR");
            }
        });

        sbBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               sbBtn.setBackgroundColor(Color.parseColor("#087830"));
                    sbBtn.setTextColor(Color.parseColor("#ffffff"));

                    sb = true;
                    unpressOthers("SB");
            }
        });

        swBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                swBtn.setBackgroundColor(Color.parseColor("#087830"));
                    swBtn.setTextColor(Color.parseColor("#ffffff"));

                    sw = true;
                    unpressOthers("SW");
            }
        });

        usBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                usBtn.setBackgroundColor(Color.parseColor("#087830"));
                    usBtn.setTextColor(Color.parseColor("#ffffff"));

                    us = true;
                    unpressOthers("US");
            }
        });

        vrBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               vrBtn.setBackgroundColor(Color.parseColor("#087830"));
                    vrBtn.setTextColor(Color.parseColor("#ffffff"));

                    vr = true;
                    unpressOthers("VR");
            }
        });

        ceBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               ceBtn.setBackgroundColor(Color.parseColor("#087830"));
                    ceBtn.setTextColor(Color.parseColor("#ffffff"));

                    ce = true;
                    unpressOthers("CE");
            }
        });

    }

    public void unpressOthers(String s){
        if(s.equals("AB")){
            abBtn.setBackgroundColor(Color.parseColor("#087830"));
            abBtn.setTextColor(Color.parseColor("#ffffff"));
            ab = true;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }
        else if(s.equals("ED")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#087830"));
            edBtn.setTextColor(Color.parseColor("#ffffff"));
            ed = true;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }
        else if(s.equals("LA")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#087830"));
            laBtn.setTextColor(Color.parseColor("#ffffff"));
            la = true;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }
        else if(s.equals("PR")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#087830"));
            prBtn.setTextColor(Color.parseColor("#ffffff"));
            pr = true;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }
        else if(s.equals("SB")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#087830"));
            sbBtn.setTextColor(Color.parseColor("#ffffff"));
            sb = true;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }

        else if(s.equals("SW")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#087830"));
            swBtn.setTextColor(Color.parseColor("#ffffff"));
            sw = true;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }
        else if(s.equals("US")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#087830"));
            usBtn.setTextColor(Color.parseColor("#ffffff"));
            us = true;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }
        else if(s.equals("VR")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#087830"));
            vrBtn.setTextColor(Color.parseColor("#ffffff"));
            vr = true;

            ceBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            ceBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ce = false;
        }
        else if(s.equals("CE")){
            abBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            abBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ab = false;

            edBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            edBtn.setTextColor(Color.parseColor("#0f0f0f"));
            ed = false;

            laBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            laBtn.setTextColor(Color.parseColor("#0f0f0f"));
            la = false;

            prBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            prBtn.setTextColor(Color.parseColor("#0f0f0f"));
            pr = false;

            sbBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            sbBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sb = false;

            swBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            swBtn.setTextColor(Color.parseColor("#0f0f0f"));
            sw = false;

            usBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            usBtn.setTextColor(Color.parseColor("#0f0f0f"));
            us = false;

            vrBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            vrBtn.setTextColor(Color.parseColor("#0f0f0f"));
            vr = false;

            ceBtn.setBackgroundColor(Color.parseColor("#087830"));
            ceBtn.setTextColor(Color.parseColor("#ffffff"));
            ce = true;
        }

    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);


        String remark = remarkField.getText().toString();
        if(remark == null)
            remark = "";

            if(ab)
                code = "Absent";
            else if(ed)
                code = "Early Dismissal";
            else if(la)
                code = "Late";
            else if(pr)
                code = "Present";
            else if(sb)
                code = "Substitute";
            else if(sw)
                code = "Seatwork";
            else if(us)
                code = "Unscheduled";
            else if(vr)
                code = "Vacant Room";
            else if(ce)
                code = "CHECKERERROR";

        if(code == "")
            Toast.makeText(getBaseContext(), "Please choose a code", Toast.LENGTH_LONG).show();
        else{
            progress.setMessage("Saving Changes");
            progress.show();

            DatabaseReference newRoom = mDatabase.child(id);
            newRoom.child(Attendance.COL_remarks).setValue(remark);
            Log.i("huh", "ab" + ab);
            newRoom.child(Attendance.COL_code).setValue(code);

            progress.dismiss();
            Toast.makeText(getBaseContext(), "Changes Saved!", Toast.LENGTH_LONG).show();
            finish();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id2 = item.getItemId();

        if (id2 == R.id.action_check)
                startEditing();
        return super.onOptionsItemSelected(item);
    }

}

