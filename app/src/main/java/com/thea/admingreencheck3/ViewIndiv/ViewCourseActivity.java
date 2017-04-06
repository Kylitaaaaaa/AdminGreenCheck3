package com.thea.admingreencheck3.ViewIndiv;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.thea.admingreencheck3.Add.AddEditChecker;
        import com.thea.admingreencheck3.Add.AddEditCourseActivity;
        import com.thea.admingreencheck3.Building;
        import com.thea.admingreencheck3.Checker;
        import com.thea.admingreencheck3.Course;
        import com.thea.admingreencheck3.CourseOffering;
        import com.thea.admingreencheck3.Faculty;
        import com.thea.admingreencheck3.IDClass;
        import com.thea.admingreencheck3.R;
        import com.thea.admingreencheck3.Room;

public class ViewCourseActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase, mDatabaseCO, mDatabaseF, mDatabaseR, mDatabaseB;

    private TextView tv_CourseCode, tv_CourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
        mDatabaseCO = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseF = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);
        mDatabaseR = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);
        mDatabaseB = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);

        id = getIntent().getExtras().getString(Course.COL_ID);

        tv_CourseCode = (TextView) findViewById(R.id.tv_CourseCode);
        tv_CourseName = (TextView) findViewById(R.id.tv_Term);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String code = (String) dataSnapshot.child(Course.COL_CODE).getValue();
                String name = (String) dataSnapshot.child(Course.COL_NAME).getValue();

                tv_CourseCode.setText(code);
                tv_CourseName.setText(name);
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
            Intent i = new Intent(getBaseContext(), AddEditCourseActivity.class);
            //add = 0
            //edit = 1
            i.putExtra("currProcess", 1 );
            i.putExtra(Faculty.COL_ID, id);
            startActivity(i);

        } else if (itemid == R.id.action_delete) {
            //mDatabase.child(id).removeValue();

            delete();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(){

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot trysnap = dataSnapshot.child(CourseOffering.TABLE_NAME);
                Iterable<DataSnapshot> tryChildren = trysnap.getChildren();

                for (DataSnapshot ty : tryChildren) {
                    IDClass idc = ty.getValue(IDClass.class);
                    Log.i("huh", "idc " + idc.getId());
                    final String idCO = idc.getId();

                    mDatabaseCO.child(idCO).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            String tempID = (String) dataSnapshot2.child(CourseOffering.COL_B_ID).getValue();
                            if(tempID != null) {
                                Log.i("huh", "tempID1 " + tempID);
                                mDatabaseB.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }

                            if(tempID != null) {
                                tempID = (String) dataSnapshot2.child(CourseOffering.COL_R_ID).getValue();
                                Log.i("huh", "tempID2 " + tempID);
                                mDatabaseR.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }

                            if(tempID != null) {
                                tempID = (String) dataSnapshot2.child(CourseOffering.COL_F_ID).getValue();
                                Log.i("huh", "tempID2 " + tempID);
                                mDatabaseF.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    mDatabaseCO.child(idc.getId()).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        mDatabase.child(id).removeValue();
    }
}

