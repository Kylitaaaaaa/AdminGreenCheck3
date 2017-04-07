package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.thea.admingreencheck3.View.CourseOfferingActivity;
import com.thea.admingreencheck3.View.FacultyActivity;

public class AddEditFacultyActivity extends AppCompatActivity {

    ImageButton addFacImage;
    EditText et_first_name, et_middle_name, et_last_name, et_college, et_email, et_mobile_number, et_department;
    Uri imageUri = null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase, mDatabaseC, mDatabaseT, mDatabaseF, mDatabaseR;
    private int currProcess;
    private String faculty_id;


    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_faculty);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addFacImage = (ImageButton) findViewById(R.id.addFacImage);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_middle_name = (EditText) findViewById(R.id.et_middle_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_college = (EditText) findViewById(R.id.et_college);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile_number = (EditText) findViewById(R.id.et_mobile_number);
        et_department = (EditText) findViewById(R.id.et_department);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = database.getReference().child(Faculty.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 1) {

            faculty_id = getIntent().getExtras().getString(Faculty.COL_ID);

            mDatabase.child(faculty_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String first_name = (String) dataSnapshot.child(Faculty.COL_FNAME).getValue();
                    String last_name = (String) dataSnapshot.child(Faculty.COL_LNAME).getValue();
                    String college = (String) dataSnapshot.child(Faculty.COL_COLLEGE).getValue();
                    String email = (String) dataSnapshot.child(Faculty.COL_EMAIL).getValue();
                    String pic = (String) dataSnapshot.child(Faculty.COL_PIC).getValue();
                    String mobile_number = (String) dataSnapshot.child(Faculty.COL_MOBNUM).getValue();
                    String department = (String) dataSnapshot.child(Faculty.COL_DEPT).getValue();


                    et_first_name.setText(first_name);
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
    }

    public void startAdding(){
        final ProgressDialog progress = new ProgressDialog(this);




        final String fname = et_first_name.getText().toString();
        Log.i("change", fname);
        final String lname = et_last_name.getText().toString();
        final String college = et_college.getText().toString();
        final String email = et_email.getText().toString();
        final String mobnum = et_mobile_number.getText().toString();
        final String dept = et_department.getText().toString();


        if(imageUri == null)
            Toast.makeText(getBaseContext(), "Please choose a picture", Toast.LENGTH_LONG).show();
        else {
            if (!TextUtils.isEmpty(fname) &&
                    !TextUtils.isEmpty(lname) &&
                    !TextUtils.isEmpty(college) &&
                    !TextUtils.isEmpty(email) &&
                    !TextUtils.isEmpty(mobnum) &&
                    !TextUtils.isEmpty(dept)) {
                progress.setMessage("Adding Faculty");
                progress.show();

                StorageReference filepath = mStorage.child("Fac_Images").child(imageUri.getLastPathSegment());

                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        DatabaseReference newFaculty = mDatabase.push();

                        newFaculty.child(Faculty.COL_FNAME).setValue(fname);
                        newFaculty.child(Faculty.COL_LNAME).setValue(lname);
                        newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
                        newFaculty.child(Faculty.COL_EMAIL).setValue(email);

                        newFaculty.child(Faculty.COL_PIC).setValue(downloadUrl.toString());

                        newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
                        newFaculty.child(Faculty.COL_DEPT).setValue(dept);

                    }
                });
                progress.dismiss();
                Toast.makeText(getBaseContext(), "Changes Saved!", Toast.LENGTH_LONG).show();
                finish();

            }
            else
                Toast.makeText(getBaseContext(), "Please complete the fields", Toast.LENGTH_LONG).show();
        }


    }


    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);
        final String fname = et_first_name.getText().toString();
        Log.i("change", fname);
        final String lname = et_last_name.getText().toString();
        final String college = et_college.getText().toString();
        final String email = et_email.getText().toString();
        final String mobnum = et_mobile_number.getText().toString();
        final String dept = et_department.getText().toString();

        if(imageUri != null) {
            if (!TextUtils.isEmpty(fname) &&
                    !TextUtils.isEmpty(lname) &&
                    !TextUtils.isEmpty(college) &&
                    !TextUtils.isEmpty(email) &&
                    !TextUtils.isEmpty(mobnum) &&
                    !TextUtils.isEmpty(dept)) {
                progress.setMessage("Saving Changes");
                progress.show();

                StorageReference filepath = mStorage.child("Fac_Images").child(imageUri.getLastPathSegment());

                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        DatabaseReference newFaculty = mDatabase.child(faculty_id);

                        newFaculty.child(Faculty.COL_FNAME).setValue(fname);
                        newFaculty.child(Faculty.COL_LNAME).setValue(lname);
                        newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
                        newFaculty.child(Faculty.COL_EMAIL).setValue(email);

                        newFaculty.child(Faculty.COL_PIC).setValue(downloadUrl.toString());

                        newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
                        newFaculty.child(Faculty.COL_DEPT).setValue(dept);
                        progress.dismiss();
                        Toast.makeText(getBaseContext(), "Changes Saved!", Toast.LENGTH_LONG).show();

                        finish();
                    }
                });

            }
            else
                Toast.makeText(getBaseContext(), "Please Complete the fields", Toast.LENGTH_LONG).show();
        }

        else {
            if (!TextUtils.isEmpty(fname) &&
                    !TextUtils.isEmpty(lname) &&
                    !TextUtils.isEmpty(college) &&
                    !TextUtils.isEmpty(email) &&
                    !TextUtils.isEmpty(mobnum) &&
                    !TextUtils.isEmpty(dept)) {
                progress.setMessage("Saving Changes");
                progress.show();

                DatabaseReference newFaculty = mDatabase.child(faculty_id);

                newFaculty.child(Faculty.COL_FNAME).setValue(fname);
                newFaculty.child(Faculty.COL_LNAME).setValue(lname);
                newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
                newFaculty.child(Faculty.COL_EMAIL).setValue(email);


                newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
                newFaculty.child(Faculty.COL_DEPT).setValue(dept);
                progress.dismiss();
                Toast.makeText(getBaseContext(), "Changes Saved!", Toast.LENGTH_LONG).show();
                finish();
            }
            else
                Toast.makeText(getBaseContext(), "Please Complete the fields", Toast.LENGTH_LONG).show();
        }


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
