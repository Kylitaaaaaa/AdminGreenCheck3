package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.AcademicYear;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.View.AcademicYearActivity;
import com.thea.admingreencheck3.View.BuildingActivity;
import com.thea.admingreencheck3.View.FacultyActivity;

public class AddEditAcademicYearActivity extends AppCompatActivity {

    EditText et_Start, et_End;
    Button btn_add, btn_edit;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private int currProcess;
    private String id;


    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_academic_year);

        et_Start = (EditText) findViewById(R.id.et_Start);
        et_End = (EditText) findViewById(R.id.et_End);

        btn_add = (Button) findViewById(R.id.btn_Add);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(AcademicYear.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 0 ) {
            //add
            btn_add.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
        }
        else{
            btn_add.setVisibility(View.GONE);
            btn_edit.setVisibility(View.VISIBLE);

            id = getIntent().getExtras().getString(AcademicYear.COL_ACAD_ID);

            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String start = (String) dataSnapshot.child(AcademicYear.COL_ACAD_START).getValue();
                    String end = (String) dataSnapshot.child(AcademicYear.COL_ACAD_END).getValue();

                    et_Start.setText(start);
                    et_End.setText(end);
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
    }

    public void startAdding(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Adding Academic Year");
        progress.show();


        final String start = et_Start.getText().toString();
        final String end = et_End.getText().toString();

        if(!TextUtils.isEmpty(start) &&
                !TextUtils.isEmpty(end)){

            DatabaseReference newThing = mDatabase.push();

            newThing.child(AcademicYear.COL_ACAD_START).setValue(start);
            newThing.child(AcademicYear.COL_ACAD_END).setValue(end);

        }

        progress.dismiss();
        //Fragment fragment = new AcademicYearActivity();
        //startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();

    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Saving Changes");
        progress.show();

        final String start = et_Start.getText().toString();
        final String end = et_End.getText().toString();

        if(!TextUtils.isEmpty(start) &&
                !TextUtils.isEmpty(end)){

            DatabaseReference newThing = mDatabase.child(id);

            newThing.child(AcademicYear.COL_ACAD_START).setValue(start);
            newThing.child(AcademicYear.COL_ACAD_END).setValue(end);


            //startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
        progress.dismiss();
        //Fragment fragment = new AcademicYearActivity();
        finish();

    }

}

