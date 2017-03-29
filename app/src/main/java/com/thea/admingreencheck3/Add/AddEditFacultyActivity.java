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
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.View.FacultyActivity;

public class AddEditFacultyActivity extends AppCompatActivity {

    ImageButton addFacImage;
    EditText et_first_name, et_middle_name, et_last_name, et_college, et_email, et_mobile_number, et_department;
    Button btn_addFac, btn_editFac;
    Uri imageUri = null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private int currProcess;
    private String faculty_id;


    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_faculty);

        addFacImage = (ImageButton) findViewById(R.id.addFacImage);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_middle_name = (EditText) findViewById(R.id.et_middle_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_college = (EditText) findViewById(R.id.et_college);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile_number = (EditText) findViewById(R.id.et_mobile_number);
        et_department = (EditText) findViewById(R.id.et_department);
        btn_addFac = (Button) findViewById(R.id.btn_addFac);
        btn_editFac = (Button) findViewById(R.id.btn_editFac);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 0 ) {
            //add
            btn_addFac.setVisibility(View.VISIBLE);
            btn_editFac.setVisibility(View.GONE);
        }
        else{
            btn_addFac.setVisibility(View.GONE);
            btn_editFac.setVisibility(View.VISIBLE);

            faculty_id = getIntent().getExtras().getString(Faculty.COL_ID);

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



                    et_first_name.setText(first_name);
                    et_middle_name.setText(middle_name);
                    et_last_name.setText(last_name);
                    et_college.setText(college);
                    et_email.setText(email);
                    et_mobile_number.setText(mobile_number);
                    et_department.setText(department);

                    Picasso.with(getBaseContext()).load(pic).into(addFacImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        addFacImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                Log.i("huh", "huh 1");
                startActivityForResult(i, GALLERY_REQUEST);
            }
        });

        btn_addFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdding();
            }
        });

        btn_editFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditing();

            }
        });
    }

    public void startAdding(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Adding Faculty");
        progress.show();




        final String fname = et_first_name.getText().toString();
        Log.i("change", fname);
        final String mname = et_middle_name.getText().toString();
        final String lname = et_last_name.getText().toString();
        final String college = et_college.getText().toString();
        final String email = et_email.getText().toString();
        final String mobnum = et_mobile_number.getText().toString();
        final String dept = et_department.getText().toString();



        if(!TextUtils.isEmpty(fname) &&
                !TextUtils.isEmpty(mname) &&
                !TextUtils.isEmpty(lname) &&
                !TextUtils.isEmpty(college) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(mobnum) &&
                !TextUtils.isEmpty(dept) &&
                imageUri != null){





            StorageReference filepath = mStorage.child("Fac_Images").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("change", "huh");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newFaculty = mDatabase.push();

                    newFaculty.child(Faculty.COL_FNAME).setValue(fname);
                    newFaculty.child(Faculty.COL_MNAME).setValue(mname);
                    newFaculty.child(Faculty.COL_LNAME).setValue(lname);
                    newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
                    newFaculty.child(Faculty.COL_EMAIL).setValue(email);

                    newFaculty.child(Faculty.COL_PIC).setValue(downloadUrl.toString());

                    newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
                    newFaculty.child(Faculty.COL_DEPT).setValue(dept);
                    progress.dismiss();

                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                }
            });

        }

    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Saving Changes");
        progress.show();

        final String fname = et_first_name.getText().toString();
        Log.i("change", fname);
        final String mname = et_middle_name.getText().toString();
        final String lname = et_last_name.getText().toString();
        final String college = et_college.getText().toString();
        final String email = et_email.getText().toString();
        final String mobnum = et_mobile_number.getText().toString();
        final String dept = et_department.getText().toString();

        Log.i("change", "imageUri " + imageUri);

        if(!TextUtils.isEmpty(fname) &&
                !TextUtils.isEmpty(mname) &&
                !TextUtils.isEmpty(lname) &&
                !TextUtils.isEmpty(college) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(mobnum) &&
                !TextUtils.isEmpty(dept)){

            if(imageUri != null){
                StorageReference filepath = mStorage.child("Fac_Images").child(imageUri.getLastPathSegment());

                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        DatabaseReference newFaculty = mDatabase.child(faculty_id);
                        Log.i("change", "huh");

                        newFaculty.child(Faculty.COL_PIC).setValue(downloadUrl.toString());

                    }
                });
            }

            DatabaseReference newFaculty = mDatabase.child(faculty_id);
            Log.i("change", "huh");

            newFaculty.child(Faculty.COL_FNAME).setValue(fname);
            newFaculty.child(Faculty.COL_MNAME).setValue(mname);
            newFaculty.child(Faculty.COL_LNAME).setValue(lname);
            newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
            newFaculty.child(Faculty.COL_EMAIL).setValue(email);


            newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
            newFaculty.child(Faculty.COL_DEPT).setValue(dept);
            progress.dismiss();

            //Fragment fragment = new FacultyActivity();
            startActivity(new Intent(getBaseContext(), MainActivity.class));

        }



        /*
        if(!TextUtils.isEmpty(fname) &&
                !TextUtils.isEmpty(mname) &&
                !TextUtils.isEmpty(lname) &&
                !TextUtils.isEmpty(college) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(mobnum) &&
                !TextUtils.isEmpty(dept) &&
                imageUri != null){

            StorageReference filepath = mStorage.child("Fac_Images").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newFaculty = mDatabase.child(faculty_id);
                    Log.i("change", "huh");

                    newFaculty.child(Faculty.COL_FNAME).setValue(fname);
                    newFaculty.child(Faculty.COL_MNAME).setValue(mname);
                    newFaculty.child(Faculty.COL_LNAME).setValue(lname);
                    newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
                    newFaculty.child(Faculty.COL_EMAIL).setValue(email);

                    newFaculty.child(Faculty.COL_PIC).setValue(downloadUrl.toString());

                    newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
                    newFaculty.child(Faculty.COL_DEPT).setValue(dept);
                    progress.dismiss();

                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                }
            });

        }

        else{
            DatabaseReference newFaculty = mDatabase.child(faculty_id);
            Log.i("change", "huh");

            newFaculty.child(Faculty.COL_FNAME).setValue(fname);
            newFaculty.child(Faculty.COL_MNAME).setValue(mname);
            newFaculty.child(Faculty.COL_LNAME).setValue(lname);
            newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
            newFaculty.child(Faculty.COL_EMAIL).setValue(email);


            newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
            newFaculty.child(Faculty.COL_DEPT).setValue(dept);
            progress.dismiss();

            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
        */

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("request", requestCode + "");

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            addFacImage.setImageURI(imageUri);
            Log.i("huh", "huh 213123");

        }
    }
}
