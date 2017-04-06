package com.thea.admingreencheck3.ViewIndiv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;

import org.w3c.dom.Text;

public class ViewCourseOfferingActivity extends AppCompatActivity {
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
    TextView et_Section;
    Button btn_M, btn_T, btn_W, btn_H, btn_F, btn_S;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase, mDatabaseC, mDatabaseT, mDatabaseF, mDatabaseR;
    private int currProcess;
    private String id, c_id, c_name, t_id, t_name, f_id, f_name, s_time, e_time, r_id, b_id;
    Boolean m, t, w, h, f, s;
    static int startHour;
    static int startMin;
    static int endHour;
    static int endMin;
    String days;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_offering);

        et_Section = (TextView) findViewById(R.id.tv_Section);

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

        id = getIntent().getExtras().getString(CourseOffering.COL_CO_ID);


        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c_id = (String) dataSnapshot.child(CourseOffering.COL_C_ID).getValue();
                f_id = (String) dataSnapshot.child(CourseOffering.COL_F_ID).getValue();
                r_id = (String) dataSnapshot.child(CourseOffering.COL_R_ID).getValue();
                b_id = (String) dataSnapshot.child(CourseOffering.COL_B_ID).getValue();
                days = (String) dataSnapshot.child(CourseOffering.COL_DAYS).getValue();

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

                    Log.i("huh", "room id " + r_id);


                    mDatabaseR.child(r_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String rm = (String) dataSnapshot.child(Room.COL_NAME).getValue();
                            Log.i("huh", "room " + rm);
                            tv_room.setText(rm);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    et_Section.setText((String) dataSnapshot.child(CourseOffering.COL_SECTION).getValue());
                    tv_StartTime.setText(dataSnapshot.child(CourseOffering.COL_START_HOUR).getValue() + " : " + dataSnapshot.child(CourseOffering.COL_START_MIN).getValue());
                    tv_EndTime.setText(dataSnapshot.child(CourseOffering.COL_END_HOUR).getValue() + " : " + dataSnapshot.child(CourseOffering.COL_END_MIN).getValue());


                    if (days.indexOf('M') >= 0) {
                        btn_M.setPressed(true);
                        btn_M.setBackgroundResource(R.drawable.round_button_green);
                        btn_M.setTextColor(Color.parseColor("#ffffff"));
                        m = true;
                    }

                    if (days.indexOf('T') >= 0) {
                        btn_T.setPressed(true);
                        btn_T.setBackgroundResource(R.drawable.round_button_green);
                        btn_T.setTextColor(Color.parseColor("#ffffff"));
                        t = true;
                    }

                    if (days.indexOf('W') >= 0) {
                        btn_W.setPressed(true);
                        btn_W.setBackgroundResource(R.drawable.round_button_green);
                        btn_W.setTextColor(Color.parseColor("#ffffff"));
                        w = true;
                    }

                    if (days.indexOf('H') >= 0) {
                        btn_H.setPressed(true);
                        btn_H.setBackgroundResource(R.drawable.round_button_green);
                        btn_H.setTextColor(Color.parseColor("#ffffff"));
                        h = true;
                    }

                    if (days.indexOf('F') >= 0) {
                        btn_F.setPressed(true);
                        btn_F.setBackgroundResource(R.drawable.round_button_green);
                        btn_F.setTextColor(Color.parseColor("#ffffff"));
                        f = true;
                    }

                    if (days.indexOf('S') >= 0) {
                        btn_S.setPressed(true);
                        btn_S.setBackgroundResource(R.drawable.round_button_green);
                        btn_S.setTextColor(Color.parseColor("#ffffff"));
                        s = true;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        if (itemid == R.id.action_edit) {
            Intent i = new Intent(getBaseContext(), AddEditCourseOffering.class);
            //add = 0
            //edit = 1
            i.putExtra("currProcess", 1 );
            i.putExtra(CourseOffering.COL_CO_ID, id);
            startActivity(i);

        } else if (itemid == R.id.action_delete) {
            mDatabaseC.child(c_id).child(CourseOffering.TABLE_NAME).child(id).removeValue();

            mDatabaseF.child(f_id).child(CourseOffering.TABLE_NAME).child(id).removeValue();

            mDatabaseR.child(r_id).child(CourseOffering.TABLE_NAME).child(id).removeValue();

            mDatabase.child(id).removeValue();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
