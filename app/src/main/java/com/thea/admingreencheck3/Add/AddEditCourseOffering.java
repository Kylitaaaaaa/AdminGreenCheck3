package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thea.admingreencheck3.AddCOCourse;
import com.thea.admingreencheck3.AddCOFaculty;
import com.thea.admingreencheck3.AddCOTerm;
import com.thea.admingreencheck3.AddCOTimePicker;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Term;

public class AddEditCourseOffering extends AppCompatActivity {
    public  final static int RESULT_CO = 2;
    public  final static int RESULT_T = 3;
    public  final static int RESULT_F = 4;
    public  final static int RESULT_ST = 5;
    public  final static int RESULT_ET = 6;

    TextView tv_ccode, tv_Term, tv_Faculty, tv_StartTime, tv_EndTime;
    EditText et_Section;
    Button btn_M, btn_T, btn_W, btn_H, btn_F, btn_S, btn_add, btn_edit;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase, mDatabaseC, mDatabaseT, mDatabaseF;
    private int currProcess;
    private String id, c_id, c_name, t_id, t_name, f_id, f_name, s_time, e_time;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course_offering);

        et_Section = (EditText) findViewById(R.id.tv_Section);

        tv_ccode = (TextView) findViewById(R.id.tv_ccode);
        tv_Term = (TextView) findViewById(R.id.tv_Term);
        tv_Faculty = (TextView) findViewById(R.id.tv_Faculty);
        tv_StartTime = (TextView) findViewById(R.id.tv_StartTime);
        tv_EndTime = (TextView) findViewById(R.id.tv_EndTime);

        btn_M = (Button) findViewById(R.id.btn_M);
        btn_T = (Button) findViewById(R.id.btn_T);
        btn_W = (Button) findViewById(R.id.btn_W);
        btn_H = (Button) findViewById(R.id.btn_H);
        btn_F = (Button) findViewById(R.id.btn_F);
        btn_S = (Button) findViewById(R.id.btn_S);

        btn_add = (Button) findViewById(R.id.btn_Add);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseC = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
        mDatabaseT = FirebaseDatabase.getInstance().getReference().child(Term.TABLE_NAME);
        mDatabaseF = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 0 ) {
            //add
            btn_add.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
        }
        else{
            btn_add.setVisibility(View.GONE);
            btn_edit.setVisibility(View.VISIBLE);

            id = getIntent().getExtras().getString(CourseOffering.COL_COURSEOFFERING_ID);
            //private String id, c_id, c_name, t_id, t_name, f_id, f_name, s_time, e_time;


            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tv_ccode.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_COURSECODE).getValue());
                    c_id = (String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_ID).getValue();
                    c_name = (String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_COURSECODE).getValue();

                    mDatabase.child(id).child(Term.TABLE_NAME).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String start = (String) dataSnapshot.child(Term.COL_ACAD_START).getValue();
                            String end = (String) dataSnapshot.child(Term.COL_ACAD_END).getValue();
                            String num = (String) dataSnapshot.child(Term.COL_TERM_NUM).getValue();
                            t_id = (String) dataSnapshot.child(Term.COL_TERM_ID).getValue();
                            t_name = start + " - " + end + " : Term " +  num;

                            tv_Term.setText(t_name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabase.child(id).child(Faculty.TABLE_NAME).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String first = (String) dataSnapshot.child(Faculty.COL_FNAME).getValue();
                            String last = (String) dataSnapshot.child(Faculty.COL_LNAME).getValue();
                            f_id = (String) dataSnapshot.child(Faculty.COL_ID).getValue();
                            f_name = first + " " + last;

                            tv_Faculty.setText(f_name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    tv_ccode.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_COURSECODE).getValue());
                    et_Section.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_SECTION).getValue());
                    tv_StartTime.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_TIME_START).getValue());
                    tv_EndTime.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_TIME_END).getValue());

                    s_time = (String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_TIME_START).getValue();
                    e_time = (String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_TIME_END).getValue();

                    if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_M).getValue()) {
                        Log.i("huh", "true");
                        btn_M.setPressed(true);
                    }

                    if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_T).getValue())
                        btn_T.setPressed(true);

                    if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_W).getValue())
                        btn_W.setPressed(true);

                    if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_H).getValue())
                        btn_H.setPressed(true);

                    if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_F).getValue())
                        btn_F.setPressed(true);

                    if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_S).getValue())
                        btn_S.setPressed(true);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdding();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditing();

            }
        });

        tv_ccode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCOCourse.class);
                i.putExtra("currProcess", 1);
                startActivityForResult(i, RESULT_CO);
            }
        });

        tv_Term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCOTerm.class);
                i.putExtra("currProcess", 1);
                startActivityForResult(i, RESULT_T);
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

        tv_StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCOTimePicker.class);
                startActivityForResult(i, RESULT_ST);
            }
        });

        tv_EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCOTimePicker.class);
                startActivityForResult(i, RESULT_ET);
            }
        });

        btn_M.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_M.isPressed())
                    btn_M.setPressed(false);
                else
                    btn_M.setPressed(true);
                return true;
            }
        });

        btn_T.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_T.isPressed())
                    btn_T.setPressed(false);
                else
                    btn_T.setPressed(true);
                return true;
            }
        });

        btn_W.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_W.isPressed())
                    btn_W.setPressed(false);
                else
                    btn_M.setPressed(true);
                return true;
            }
        });

        btn_H.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_H.isPressed())
                    btn_H.setPressed(false);
                else
                    btn_H.setPressed(true);
                return true;
            }
        });

        btn_F.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_F.isPressed())
                    btn_F.setPressed(false);
                else
                    btn_F.setPressed(true);
                return true;
            }
        });

        btn_S.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_S.isPressed())
                    btn_S.setPressed(false);
                else
                    btn_S.setPressed(true);
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
                            tv_Term.setText(start + " - " + end + ": " + t_name);
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
                    s_time = data.getStringExtra("time");

                    tv_StartTime.setText(s_time);
                }

                else if(requestCode == RESULT_ET ){
                    e_time = data.getStringExtra("time");

                    tv_EndTime.setText(e_time);
                }

            }
    }

    public void startAdding(){

        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Adding Course Offering");
        progress.show();

        final String section = et_Section.getText().toString();
        final String start = tv_StartTime.getText().toString();
        final String end = tv_EndTime.getText().toString();

        if(!TextUtils.isEmpty(section) &&
                !TextUtils.isEmpty(start) &&
                !TextUtils.isEmpty(end) &&
                !TextUtils.isEmpty(c_id) &&
                !TextUtils.isEmpty(t_id) &&
                !TextUtils.isEmpty(f_id)){

            final DatabaseReference newThing = mDatabase.push();

            newThing.child(CourseOffering.COL_COURSEOFFERING_SECTION).setValue(section);
            newThing.child(CourseOffering.COL_COURSEOFFERING_TIME_START).setValue(start);
            newThing.child(CourseOffering.COL_COURSEOFFERING_TIME_END).setValue(end);

            if(btn_M.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_M).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_M).setValue(false);

            if(btn_T.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_T).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_T).setValue(false);

            if(btn_W.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_W).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_W).setValue(false);

            if(btn_H.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_H).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_H).setValue(false);

            if(btn_F.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_F).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_F).setValue(false);

            if(btn_S.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_S).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_S).setValue(false);


            mDatabaseC.child(c_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newThing.child(Course.TABLE_NAME).child(Course.COL_ID).setValue(c_id);
                    newThing.child(Course.TABLE_NAME).child(Course.COL_NAME).setValue((String) dataSnapshot.child(Course.COL_NAME).getValue());
                    newThing.child(Course.TABLE_NAME).child(Course.COL_CODE).setValue((String) dataSnapshot.child(Course.COL_CODE).getValue());
                    newThing.child(CourseOffering.COL_COURSEOFFERING_COURSECODE).setValue((String) dataSnapshot.child(Course.COL_CODE).getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            mDatabaseT.child(t_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newThing.child(Term.TABLE_NAME).child(Term.COL_TERM_ID).setValue(t_id);
                    newThing.child(Term.TABLE_NAME).child(Term.COL_ACAD_START).setValue((String) dataSnapshot.child(Term.COL_ACAD_START).getValue());
                    newThing.child(Term.TABLE_NAME).child(Term.COL_ACAD_END).setValue((String) dataSnapshot.child(Term.COL_ACAD_END).getValue());
                    newThing.child(Term.TABLE_NAME).child(Term.COL_TERM_NUM).setValue((String) dataSnapshot.child(Term.COL_TERM_NUM).getValue());
                    newThing.child(Term.TABLE_NAME).child(Term.COL_ACAD_ID).setValue((String) dataSnapshot.child(Term.COL_ACAD_ID).getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            mDatabaseF.child(f_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_ID).setValue(c_id);
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_FNAME).setValue((String) dataSnapshot.child(Faculty.COL_FNAME).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_MNAME).setValue((String) dataSnapshot.child(Faculty.COL_MNAME).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_LNAME).setValue((String) dataSnapshot.child(Faculty.COL_LNAME).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_DEPT).setValue((String) dataSnapshot.child(Faculty.COL_DEPT).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_MOBNUM).setValue((String) dataSnapshot.child(Faculty.COL_MOBNUM).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_PIC).setValue((String) dataSnapshot.child(Faculty.COL_PIC).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_EMAIL).setValue((String) dataSnapshot.child(Faculty.COL_EMAIL).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_COLLEGE).setValue((String) dataSnapshot.child(Faculty.COL_COLLEGE).getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });




        }

        progress.dismiss();
        //Fragment fragment = new AcademicYearActivity();
        //startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();


    }

    public void startEditing(){
        Log.i("huh", "editing");

        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Saving Changes");
        progress.show();

        final String section = et_Section.getText().toString();
        final String start = tv_StartTime.getText().toString();
        final String end = tv_EndTime.getText().toString();

        if(!TextUtils.isEmpty(section) &&
                !TextUtils.isEmpty(start) &&
                !TextUtils.isEmpty(end) &&
                !TextUtils.isEmpty(c_id) &&
                !TextUtils.isEmpty(t_id) &&
                !TextUtils.isEmpty(f_id)){

            final DatabaseReference newThing = mDatabase.child(id);

            newThing.child(CourseOffering.COL_COURSEOFFERING_SECTION).setValue(section);
            newThing.child(CourseOffering.COL_COURSEOFFERING_TIME_START).setValue(start);
            newThing.child(CourseOffering.COL_COURSEOFFERING_TIME_END).setValue(end);

            if(btn_M.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_M).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_M).setValue(false);

            if(btn_T.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_T).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_T).setValue(false);

            if(btn_W.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_W).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_W).setValue(false);

            if(btn_H.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_H).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_H).setValue(false);

            if(btn_F.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_F).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_F).setValue(false);

            if(btn_S.isPressed())
                newThing.child(CourseOffering.COL_COURSEOFFERING_S).setValue(true);
            else
                newThing.child(CourseOffering.COL_COURSEOFFERING_S).setValue(false);


            mDatabaseC.child(c_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newThing.child(Course.TABLE_NAME).child(Course.COL_ID).setValue(c_id);
                    newThing.child(Course.TABLE_NAME).child(Course.COL_NAME).setValue((String) dataSnapshot.child(Course.COL_NAME).getValue());
                    newThing.child(Course.TABLE_NAME).child(Course.COL_CODE).setValue((String) dataSnapshot.child(Course.COL_CODE).getValue());
                    newThing.child(CourseOffering.COL_COURSEOFFERING_COURSECODE).setValue((String) dataSnapshot.child(Course.COL_CODE).getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            mDatabaseT.child(t_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newThing.child(Term.TABLE_NAME).child(Term.COL_TERM_ID).setValue(t_id);
                    newThing.child(Term.TABLE_NAME).child(Term.COL_ACAD_START).setValue((String) dataSnapshot.child(Term.COL_ACAD_START).getValue());
                    newThing.child(Term.TABLE_NAME).child(Term.COL_ACAD_END).setValue((String) dataSnapshot.child(Term.COL_ACAD_END).getValue());
                    newThing.child(Term.TABLE_NAME).child(Term.COL_TERM_NUM).setValue((String) dataSnapshot.child(Term.COL_TERM_NUM).getValue());
                    newThing.child(Term.TABLE_NAME).child(Term.COL_ACAD_ID).setValue((String) dataSnapshot.child(Term.COL_ACAD_ID).getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            mDatabaseF.child(f_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_ID).setValue(c_id);
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_FNAME).setValue((String) dataSnapshot.child(Faculty.COL_FNAME).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_MNAME).setValue((String) dataSnapshot.child(Faculty.COL_MNAME).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_LNAME).setValue((String) dataSnapshot.child(Faculty.COL_LNAME).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_DEPT).setValue((String) dataSnapshot.child(Faculty.COL_DEPT).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_MOBNUM).setValue((String) dataSnapshot.child(Faculty.COL_MOBNUM).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_PIC).setValue((String) dataSnapshot.child(Faculty.COL_PIC).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_EMAIL).setValue((String) dataSnapshot.child(Faculty.COL_EMAIL).getValue());
                    newThing.child(Faculty.TABLE_NAME).child(Faculty.COL_COLLEGE).setValue((String) dataSnapshot.child(Faculty.COL_COLLEGE).getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });




        }
        progress.dismiss();
        //Fragment fragment = new AcademicYearActivity();
        finish();


    }

}

