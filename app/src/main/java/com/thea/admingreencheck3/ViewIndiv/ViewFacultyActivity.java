package com.thea.admingreencheck3.ViewIndiv;

import android.content.Intent;
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
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;

public class ViewFacultyActivity extends AppCompatActivity {
    String faculty_id;
    private DatabaseReference mDatabase;

    private ImageView imgFac;
    private TextView tv_Name, tv_College, tv_Department, tv_Email, tv_Mobile;

    private Button btn_delete, btn_edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);

        faculty_id = getIntent().getExtras().getString(Faculty.COL_ID);

        imgFac = (ImageView) findViewById(R.id.imgFac);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_College = (TextView) findViewById(R.id.tv_College);
        tv_Department = (TextView) findViewById(R.id.tv_Department);
        tv_Email = (TextView) findViewById(R.id.tv_Email);
        tv_Mobile = (TextView) findViewById(R.id.tv_Mobile);

        btn_delete = (Button) findViewById(R.id.btn_Delete);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

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

                tv_Name.setText(first_name + " " + middle_name + " " + last_name);
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

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(faculty_id).removeValue();
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddEditFacultyActivity.class);
                //add = 0
                //edit = 1
                i.putExtra("currProcess", 1 );
                i.putExtra(Faculty.COL_ID, faculty_id);
                startActivity(i);


            }
        });


    }
}
