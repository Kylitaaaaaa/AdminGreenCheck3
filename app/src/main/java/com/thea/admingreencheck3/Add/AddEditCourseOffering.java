package com.thea.admingreencheck3.Add;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thea.admingreencheck3.AddCOCourse;
import com.thea.admingreencheck3.AddCOFaculty;
import com.thea.admingreencheck3.AddCORoom;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.IDClass;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;

import java.util.Calendar;

public class AddEditCourseOffering extends AppCompatActivity {
    public  final static int RESULT_CO = 2;
    public  final static int RESULT_T = 3;
    public  final static int RESULT_F = 4;
    public  final static int RESULT_ST = 5;
    public  final static int RESULT_ET = 6;
    public  final static int RESULT_R = 7;

    TextView tv_ccode, tv_room;
    TextView tv_Faculty;
    static TextView tv_EndTime;
    static TextView tv_StartTime;
    EditText et_Section;
    Button btn_M, btn_T, btn_W, btn_H, btn_F, btn_S;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase, mDatabaseC, mDatabaseT, mDatabaseF, mDatabaseR, mDatabaseB;
    private int currProcess;
    private String id, c_id, c_name, t_id, t_name, f_id, f_name, s_time, e_time, r_id, b_id;
    private String orig_c, orig_t, orig_f, orig_b, orig_r;
    Boolean m, t, w, h, f, s;
    static int startHour;
    static int startMin;
    static int endHour;
    static int endMin;
    String days;



    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course_offering);

        et_Section = (EditText) findViewById(R.id.tv_Section);

        tv_ccode = (TextView) findViewById(R.id.tv_ccode);
        tv_Faculty = (TextView) findViewById(R.id.tv_Faculty);
        tv_StartTime = (TextView) findViewById(R.id.tv_StartTime);
        tv_EndTime = (TextView) findViewById(R.id.tv_EndTime);
        tv_room = (TextView) findViewById(R.id.tv_room);

        btn_M = (Button) findViewById(R.id.btn_M);
        btn_T = (Button) findViewById(R.id.btn_T);
        btn_W = (Button) findViewById(R.id.btn_W);
        btn_H = (Button) findViewById(R.id.btn_H);
        btn_F = (Button) findViewById(R.id.btn_F);
        btn_S = (Button) findViewById(R.id.btn_S);

        m = false;
        t = false;
        w = false;
        h = false;
        f = false;
        s = false;


        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseC = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
        mDatabaseT = FirebaseDatabase.getInstance().getReference().child(Term.TABLE_NAME);
        mDatabaseF = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);
        mDatabaseR = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);
        mDatabaseB = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 1 ) {


            id = getIntent().getExtras().getString(CourseOffering.COL_CO_ID);


            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    c_id = (String) dataSnapshot.child(CourseOffering.COL_C_ID).getValue();
                    f_id = (String) dataSnapshot.child(CourseOffering.COL_F_ID).getValue();
                    r_id = (String) dataSnapshot.child(CourseOffering.COL_R_ID).getValue();
                    b_id = (String) dataSnapshot.child(CourseOffering.COL_B_ID).getValue();
                    days = (String) dataSnapshot.child(CourseOffering.COL_DAYS).getValue();

                    orig_c = c_id;
                    orig_f = f_id;
                    orig_b = b_id;
                    orig_r = r_id;
                    orig_b = b_id;

                    if(c_id != null &&
                            f_id != null &&
                            b_id != null &&
                            r_id != null &&
                            days != null) {

                        mDatabaseC.child(c_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                tv_ccode.setText((String) dataSnapshot.child(Course.COL_CODE).getValue());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        mDatabaseF.child(f_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                tv_Faculty.setText((String) dataSnapshot.child(Faculty.COL_FNAME).getValue() + " " + (String) dataSnapshot.child(Faculty.COL_LNAME).getValue());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        mDatabaseR.child(r_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                tv_room.setText((String) dataSnapshot.child(Room.COL_NAME).getValue());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        if(days.indexOf('M') >=0){
                            btn_M.setPressed(true);
                            btn_M.setBackgroundResource(R.drawable.round_button_green);
                            btn_M.setTextColor(Color.parseColor("#ffffff"));
                            m = true;
                        }

                        if(days.indexOf('T') >=0){
                            btn_T.setPressed(true);
                            btn_T.setBackgroundResource(R.drawable.round_button_green);
                            btn_T.setTextColor(Color.parseColor("#ffffff"));
                            t = true;
                        }

                        if(days.indexOf('W') >=0){
                            btn_W.setPressed(true);
                            btn_W.setBackgroundResource(R.drawable.round_button_green);
                            btn_W.setTextColor(Color.parseColor("#ffffff"));
                            w = true;
                        }

                        if(days.indexOf('H') >=0){
                            btn_H.setPressed(true);
                            btn_H.setBackgroundResource(R.drawable.round_button_green);
                            btn_H.setTextColor(Color.parseColor("#ffffff"));
                            h = true;
                        }

                        if(days.indexOf('F') >=0){
                            btn_F.setPressed(true);
                            btn_F.setBackgroundResource(R.drawable.round_button_green);
                            btn_F.setTextColor(Color.parseColor("#ffffff"));
                            f = true;
                        }

                        if(days.indexOf('S') >=0){
                            btn_S.setPressed(true);
                            btn_S.setBackgroundResource(R.drawable.round_button_green);
                            btn_S.setTextColor(Color.parseColor("#ffffff"));
                            s = true;
                        }
                    }

                    et_Section.setText((String) dataSnapshot.child(CourseOffering.COL_SECTION).getValue());
                    String starth = dataSnapshot.child(CourseOffering.COL_START_HOUR).getValue() + "";
                    String startm = dataSnapshot.child(CourseOffering.COL_START_MIN).getValue() + "";
                    tv_StartTime.setText(starth + " : " + startm );

                    String endh = dataSnapshot.child(CourseOffering.COL_END_HOUR).getValue() + "";
                    String endm = dataSnapshot.child(CourseOffering.COL_END_MIN).getValue() + "";

                    tv_EndTime.setText(endh + " : " + endm);




                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


        tv_ccode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCOCourse.class);
                i.putExtra("currProcess", 1);

                startActivityForResult(i, RESULT_CO);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                 //overridePendingTransition(R.anim.fade_in, R.anim.fade_in);

            }
        });

        tv_Faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCOFaculty.class);
                i.putExtra("currProcess", 1);
                startActivityForResult(i, RESULT_F);
            }
        });
        tv_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCORoom.class);
                i.putExtra("currProcess", 1);
                startActivityForResult(i, RESULT_R);
            }
        });

        tv_StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new TimePickerFragment();

                newFragment.show(fm, "timePicker");
            }
        });

        tv_EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new TimePickerFragment2();

                newFragment.show(fm, "timePicker");
            }
        });

        btn_M.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_M.isPressed()) {
                    btn_M.setPressed(false);
                    btn_M.setBackgroundResource(R.drawable.round_button);
                    btn_M.setTextColor(Color.parseColor("#0f0f0f"));
                    m = false;
                }
                else {
                    btn_M.setPressed(true);
                    btn_M.setBackgroundResource(R.drawable.round_button_green);
                    btn_M.setTextColor(Color.parseColor("#ffffff"));

                    m = true;
                    Log.i("huh", "pressed m " + m);
                }
                return true;
            }
        });

        btn_T.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_T.isPressed()) {
                    btn_T.setPressed(false);
                    btn_T.setBackgroundResource(R.drawable.round_button);
                    btn_T.setTextColor(Color.parseColor("#0f0f0f"));
                    t = false;
                }
                else {
                    btn_T.setPressed(true);
                    btn_T.setBackgroundResource(R.drawable.round_button_green);
                    btn_T.setTextColor(Color.parseColor("#ffffff"));
                    t = true;
                }
                return true;
            }
        });

        btn_W.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_W.isPressed()) {
                    btn_W.setPressed(false);
                    btn_W.setBackgroundResource(R.drawable.round_button);
                    btn_W.setTextColor(Color.parseColor("#0f0f0f"));
                    w = false;
                }
                else {
                    btn_W.setPressed(true);
                    btn_W.setBackgroundResource(R.drawable.round_button_green);
                    btn_W.setTextColor(Color.parseColor("#ffffff"));
                    w = true;
                }
                return true;
            }
        });

        btn_H.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_H.isPressed()) {
                    btn_H.setPressed(false);
                    btn_H.setBackgroundResource(R.drawable.round_button);
                    btn_H.setTextColor(Color.parseColor("#0f0f0f"));
                    h = false;
                }
                else {
                    btn_H.setPressed(true);

                    btn_H.setBackgroundResource(R.drawable.round_button_green);
                    btn_H.setTextColor(Color.parseColor("#ffffff"));
                    h= true;
                }
                return true;
            }
        });

        btn_F.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_F.isPressed()) {
                    btn_F.setPressed(false);
                    btn_F.setBackgroundResource(R.drawable.round_button);
                    btn_F.setTextColor(Color.parseColor("#0f0f0f"));
                    f = false;
                }
                else {
                    btn_F.setPressed(true);

                    btn_F.setBackgroundResource(R.drawable.round_button_green);
                    btn_F.setTextColor(Color.parseColor("#ffffff"));
                    f = true;
                }
                return true;
            }
        });

        btn_S.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_S.isPressed()) {
                    btn_S.setPressed(false);
                    btn_S.setBackgroundResource(R.drawable.round_button);
                    btn_S.setTextColor(Color.parseColor("#0f0f0f"));
                    s = false;
                }
                else {
                    btn_S.setPressed(true);

                    btn_S.setBackgroundResource(R.drawable.round_button_green);
                    btn_S.setTextColor(Color.parseColor("#ffffff"));
                    s = true;
                }
                return true;
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK) {
                if(requestCode == RESULT_CO ){
                    c_id = data.getStringExtra(Course.COL_ID);

                    mDatabaseC.child(c_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            c_name = (String) dataSnapshot.child(Course.COL_CODE).getValue();
                            tv_ccode.setText(c_name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                else if(requestCode == RESULT_T ){
                    t_id = data.getStringExtra(Term.COL_TERM_ID);

                    mDatabaseT.child(t_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String start = (String) dataSnapshot.child(Term.COL_ACAD_START).getValue();
                            String end = (String) dataSnapshot.child(Term.COL_ACAD_END).getValue();

                            t_name = (String) dataSnapshot.child(Term.COL_TERM_NUM).getValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                else if(requestCode == RESULT_F ){
                    f_id = data.getStringExtra(Faculty.COL_ID);

                    mDatabaseF.child(f_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String first = (String) dataSnapshot.child(Faculty.COL_FNAME).getValue();
                            String last = (String) dataSnapshot.child(Faculty.COL_LNAME).getValue();
                            f_name = first + " " + last;
                            tv_Faculty.setText(f_name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                else if(requestCode == RESULT_ST ){

                    startHour = Integer.parseInt(data.getStringExtra("hour"));
                    startMin = Integer.parseInt(data.getStringExtra("min"));
                    tv_StartTime.setText(data.getStringExtra("time"));

                }

                else if(requestCode == RESULT_ET ){
                    endHour = Integer.parseInt(data.getStringExtra("hour"));
                    endMin = Integer.parseInt(data.getStringExtra("min"));
                    tv_EndTime.setText(data.getStringExtra("time"));
                }


                else if(requestCode == RESULT_R ){
                    r_id = data.getStringExtra(Room.COL_ROOM_ID);

                    mDatabaseR.child(r_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            b_id = (String) dataSnapshot.child(Room.COL_BUILDING_ID).getValue();
                            tv_room.setText((String) dataSnapshot.child(Room.COL_NAME).getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
    }

    public void startAdding(){

        final ProgressDialog progress = new ProgressDialog(this);

        final String section = et_Section.getText().toString();

        if(!TextUtils.isEmpty(section) &&
                !TextUtils.isEmpty(c_id) &&
                !TextUtils.isEmpty(f_id)){

            progress.setMessage("Adding Course Offering");
            progress.show();

            days = "";


            if(m)
                days = days.concat("M");

            if(t)
                days = days.concat("T");

            if(w)
                days = days.concat("W");

            if(h)
                days = days.concat("H");

            if(f)
                days = days.concat("F");

            if(s)
                days = days.concat("S");

            final DatabaseReference newThing = mDatabase.push();

            newThing.child(CourseOffering.COL_CO_ID).setValue(newThing.getKey().toString());
            newThing.child(CourseOffering.COL_START_HOUR).setValue(startHour);
            newThing.child(CourseOffering.COL_START_MIN).setValue(startMin);
            newThing.child(CourseOffering.COL_END_HOUR).setValue(endHour);
            newThing.child(CourseOffering.COL_END_MIN).setValue(endMin);
            newThing.child(CourseOffering.COL_C_ID).setValue(c_id);
            newThing.child(CourseOffering.COL_F_ID).setValue(f_id);
            newThing.child(CourseOffering.COL_R_ID).setValue(r_id);
            newThing.child(CourseOffering.COL_B_ID).setValue(b_id);
            newThing.child(CourseOffering.COL_DAYS).setValue(days);

            newThing.child(CourseOffering.COL_SECTION).setValue(section);

            //Add to course
            DatabaseReference updateBuilding = mDatabaseC.child(c_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateBuilding.child(IDClass.COL_ID).setValue(newThing.getKey());

            //Add to facname
            DatabaseReference updateFac = mDatabaseF.child(f_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateFac.child(IDClass.COL_ID).setValue(newThing.getKey());

            //Add to room
            DatabaseReference updateR = mDatabaseR.child(r_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateR.child(IDClass.COL_ID).setValue(newThing.getKey());

            //Add to building
            DatabaseReference updateB = mDatabaseB.child(b_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateB.child(IDClass.COL_ID).setValue(newThing.getKey());

            Log.i("huh", "done adding!");
            progress.dismiss();
            finish();
            //startActivity(new Intent(getBaseContext(), MainActivity.class));

        }




    }



    public static void setStartTime(){
        tv_StartTime.setText(startHour + " : " + startMin);
    }

    public static void setEndTime(){
        tv_EndTime.setText(endHour + " : " + endMin);
    }




    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);

        final String section = et_Section.getText().toString();

        if(!TextUtils.isEmpty(section) &&
                !TextUtils.isEmpty(c_id) &&
                !TextUtils.isEmpty(f_id)){

            progress.setMessage("Adding Course Offering");
            progress.show();

            days = "";


            if(m) {
                days = days.concat("M");
            }

            if(t)
                days = days.concat("T");

            if(w)
                days = days.concat("W");

            if(h)
                days = days.concat("H");

            if(f)
                days = days.concat("F");

            if(s)
                days = days.concat("S");

            final DatabaseReference newThing = mDatabase.child(id);

            newThing.child(CourseOffering.COL_CO_ID).setValue(newThing.getKey().toString());
            newThing.child(CourseOffering.COL_START_HOUR).setValue(startHour);
            newThing.child(CourseOffering.COL_START_MIN).setValue(startMin);
            newThing.child(CourseOffering.COL_END_HOUR).setValue(endHour);
            newThing.child(CourseOffering.COL_END_MIN).setValue(endMin);
            newThing.child(CourseOffering.COL_C_ID).setValue(c_id);
            newThing.child(CourseOffering.COL_F_ID).setValue(f_id);
            newThing.child(CourseOffering.COL_R_ID).setValue(r_id);
            newThing.child(CourseOffering.COL_B_ID).setValue(b_id);
            newThing.child(CourseOffering.COL_DAYS).setValue(days);

            //update course
            if(!orig_c.equals(c_id)) {
                DatabaseReference update = mDatabaseC.child(orig_c).child(CourseOffering.TABLE_NAME);
                update.child(id).removeValue();
            }

            DatabaseReference updateBuilding = mDatabaseC.child(c_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateBuilding.child(IDClass.COL_ID).setValue(newThing.getKey());

            //udpate faculty
            if(!orig_f.equals(f_id)) {
                DatabaseReference update = mDatabaseF.child(orig_f).child(CourseOffering.TABLE_NAME);
                update.child(id).removeValue();
            }

            DatabaseReference updateFac = mDatabaseF.child(f_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateFac.child(IDClass.COL_ID).setValue(newThing.getKey());


            //update Room
            if(!orig_r.equals(r_id)) {
                DatabaseReference update = mDatabaseR.child(orig_r).child(CourseOffering.TABLE_NAME);
                update.child(id).removeValue();
            }

            DatabaseReference updateRoom = mDatabaseR.child(r_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateRoom.child(IDClass.COL_ID).setValue(newThing.getKey());

            //update Building
            Log.i("huh", "b_id " + b_id);
            Log.i("huh", "orig_b " + orig_b);
            if(!orig_b.equals(b_id)) {
                Log.i("huh", "orig_bb not equal");
                DatabaseReference update = mDatabaseB.child(orig_b).child(CourseOffering.TABLE_NAME);
                update.child(id).removeValue();
            }

            DatabaseReference updateB = mDatabaseB.child(b_id).child(CourseOffering.TABLE_NAME).child(newThing.getKey());
            updateB.child(IDClass.COL_ID).setValue(newThing.getKey());

            progress.dismiss();
            finish();
        }
    }


    public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        TimePickerDialog.OnTimeSetListener timeListener;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Log.i("huh", "hourofday " + hourOfDay);

            startHour = hourOfDay;
            startMin = minute;
            AddEditCourseOffering.setStartTime();

        }
    }

    public class TimePickerFragment2 extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        TimePickerDialog.OnTimeSetListener timeListener;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Log.i("huh", "hourofday " + hourOfDay);

            endHour = hourOfDay;
            endMin = minute;
            AddEditCourseOffering.setEndTime();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_check) {
            if(currProcess == 0)
                startAdding();
            else
                startEditing();

        }
        return super.onOptionsItemSelected(item);
    }

}

