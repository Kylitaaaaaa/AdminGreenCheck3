package com.thea.admingreencheck3.ViewIndiv;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;

public class ViewCourseOfferingActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase, mDatabaseBuilding;

    private TextView tv_ccode, tv_Term, tv_Faculty, tv_Section, tv_StartTime, tv_EndTime;

    private Button btn_M, btn_T, btn_W, btn_H, btn_F, btn_S, btn_edit, btn_delete;
    String bldgID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_offering);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseBuilding = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);

        id = getIntent().getExtras().getString(CourseOffering.COL_COURSEOFFERING_ID);

        tv_ccode = (TextView) findViewById(R.id.tv_ccode);
        tv_Term = (TextView) findViewById(R.id.tv_Term);
        tv_Faculty = (TextView) findViewById(R.id.tv_Faculty);
        tv_Section = (TextView) findViewById(R.id.tv_Section);
        tv_StartTime = (TextView) findViewById(R.id.tv_StartTime);
        tv_EndTime = (TextView) findViewById(R.id.tv_EndTime);

        btn_M = (Button) findViewById(R.id.btn_M);
        btn_T = (Button) findViewById(R.id.btn_T);
        btn_W = (Button) findViewById(R.id.btn_W);
        btn_H = (Button) findViewById(R.id.btn_H);
        btn_F = (Button) findViewById(R.id.btn_F);
        btn_S = (Button) findViewById(R.id.btn_S);

        btn_delete = (Button) findViewById(R.id.btn_Delete);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_ccode.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_COURSECODE).getValue());

                mDatabase.child(id).child(Term.TABLE_NAME).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String start = (String) dataSnapshot.child(Term.COL_ACAD_START).getValue();
                        String end = (String) dataSnapshot.child(Term.COL_ACAD_END).getValue();
                        String num = (String) dataSnapshot.child(Term.COL_TERM_NUM).getValue();

                        tv_Term.setText(start + " - " + end + " : Term " +  num);
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

                        tv_Faculty.setText(first + " " + last);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                tv_ccode.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_COURSECODE).getValue());
                tv_Section.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_SECTION).getValue());
                tv_StartTime.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_TIME_START).getValue());
                tv_EndTime.setText((String) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_TIME_END).getValue());

                if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_M).getValue())
                    btn_M.setBackgroundColor(Color.parseColor("green"));

                if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_T).getValue())
                    btn_T.setBackgroundColor(Color.parseColor("green"));

                if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_W).getValue())
                    btn_W.setBackgroundColor(Color.parseColor("green"));

                if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_H).getValue())
                    btn_H.setBackgroundColor(Color.parseColor("green"));

                if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_F).getValue())
                    btn_F.setBackgroundColor(Color.parseColor("green"));

                if((Boolean) dataSnapshot.child(CourseOffering.COL_COURSEOFFERING_S).getValue())
                    btn_S.setBackgroundColor(Color.parseColor("green"));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatabaseReference update = mDatabaseBuilding.child(bldgID).child(Building.COL_ROOM);
//                update.child(id).removeValue();

                mDatabase.child(id).removeValue();
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddEditCourseOffering.class);
                //add = 0
                //edit = 1
                i.putExtra("currProcess", 1 );
                i.putExtra(CourseOffering.COL_COURSEOFFERING_ID, id);
                startActivity(i);


            }
        });



    }
}
