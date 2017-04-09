package com.thea.admingreencheck3.ViewIndiv;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.IDClass;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;

public class ViewFacultyActivity extends AppCompatActivity {
    String faculty_id;
    private DatabaseReference mDatabase, mDatabaseCO, mDatabaseC, mDatabaseB, mDatabaseR;

    private ImageView imgFac;
    private TextView tv_Name, tv_College, tv_Department, tv_Email, tv_Mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        getSupportActionBar().setTitle("Faculty");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);
        mDatabaseCO = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseC = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
        mDatabaseB = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);
        mDatabaseR = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);

        faculty_id = getIntent().getExtras().getString(Faculty.COL_ID);

        imgFac = (ImageView) findViewById(R.id.imgFac);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_College = (TextView) findViewById(R.id.tv_College);
        tv_Department = (TextView) findViewById(R.id.tv_Department);
        tv_Email = (TextView) findViewById(R.id.tv_Email);
        tv_Mobile = (TextView) findViewById(R.id.tv_Mobile);


        mDatabase.child(faculty_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String first_name = (String) dataSnapshot.child(Faculty.COL_FNAME).getValue();
                String middle_name = (String) dataSnapshot.child(Faculty.COL_MNAME).getValue();
                String last_name = (String) dataSnapshot.child(Faculty.COL_LNAME).getValue();
                String college = (String) dataSnapshot.child(Faculty.COL_COLLEGE).getValue();
                String email = (String) dataSnapshot.child(Faculty.COL_EMAIL).getValue();
                String pic = (String) dataSnapshot.child(Faculty.COL_PIC).getValue();
                String mobile_number = (String) dataSnapshot.child(Faculty.COL_MOBNUM).getValue();
                String department = (String) dataSnapshot.child(Faculty.COL_DEPT).getValue();


                tv_Name.setText(first_name + " " + last_name);
                tv_College.setText(college);
                tv_Email.setText(email);
                tv_Mobile.setText(mobile_number);
                tv_Department.setText(department);

                Picasso.with(getBaseContext()).load(pic).into(imgFac);
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
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Intent i = new Intent(getBaseContext(), AddEditFacultyActivity.class);
            //add = 0
            //edit = 1
            i.putExtra("currProcess", 1 );
            i.putExtra(Faculty.COL_ID, faculty_id);
            startActivity(i);

        }
        else if (item.getItemId() == R.id.action_delete) {
            AlertDialog diaBox = AskOption();
            diaBox.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(){

        mDatabase.child(faculty_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot trysnap = dataSnapshot.child(CourseOffering.TABLE_NAME);
                Iterable<DataSnapshot> tryChildren = trysnap.getChildren();

                for (DataSnapshot ty : tryChildren) {
                    IDClass idc = ty.getValue(IDClass.class);
                    //Log.i("huh", "idc " + idc.getId());
                    final String idCO = idc.getId();

                    mDatabaseCO.child(idCO).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            String tempID = (String) dataSnapshot2.child(CourseOffering.COL_B_ID).getValue();
                            if(tempID != null) {
                                Log.i("huh", "tempID1 " + tempID);
                                mDatabaseB.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }
                            tempID = (String) dataSnapshot2.child(CourseOffering.COL_R_ID).getValue();
                            if(tempID != null) {

                                Log.i("huh", "tempID2 " + tempID);
                                mDatabaseR.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }

                            tempID = (String) dataSnapshot2.child(CourseOffering.COL_C_ID).getValue();
                            if(tempID != null) {

                                Log.i("huh", "tempID3 " + tempID);
                                mDatabaseC.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    mDatabaseCO.child(idc.getId()).removeValue();
                    //mDatabaseCO.child(idc.getId()).child(CourseOffering.COL_IS_ENABLED).setValue(false);
                }

                mDatabase.child(faculty_id).removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        mDatabase.child(faculty_id).child(Faculty.COL_IS_ENABLED).setValue(false);
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setIcon(R.drawable.ic_delete_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        delete();
                        dialog.dismiss();
                        finish();
                        Toast.makeText(getBaseContext(), "Successfully Deleted!", Toast.LENGTH_LONG).show();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }


}
