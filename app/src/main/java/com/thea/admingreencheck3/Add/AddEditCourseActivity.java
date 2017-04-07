package com.thea.admingreencheck3.Add;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.support.v4.app.Fragment;
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
        import com.thea.admingreencheck3.Course;
        import com.thea.admingreencheck3.Faculty;
        import com.thea.admingreencheck3.MainActivity;
        import com.thea.admingreencheck3.R;
        import com.thea.admingreencheck3.View.FacultyActivity;

public class AddEditCourseActivity extends AppCompatActivity {

    EditText et_CourseCode, et_CourseName;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private int currProcess;
    private String id;


    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        et_CourseCode = (EditText) findViewById(R.id.et_CourseCode);
        et_CourseName = (EditText) findViewById(R.id.et_CourseName);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 1 ) {

            id = getIntent().getExtras().getString(Course.COL_ID);

            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String code = (String) dataSnapshot.child(Course.COL_CODE).getValue();
                    String name = (String) dataSnapshot.child(Course.COL_NAME).getValue();

                    et_CourseCode.setText(code);
                    et_CourseName.setText(name);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void startAdding(){
        final ProgressDialog progress = new ProgressDialog(this);




        final String ccode = et_CourseCode.getText().toString();
        final String cname = et_CourseName.getText().toString();

        if(!TextUtils.isEmpty(ccode) && !TextUtils.isEmpty(cname)){
            progress.setMessage("Adding Course");
            progress.show();

            DatabaseReference newFaculty = mDatabase.push();

            newFaculty.child(Course.COL_CODE).setValue(ccode);
            newFaculty.child(Course.COL_NAME).setValue(cname);
            newFaculty.child(Course.COL_ID).setValue(newFaculty.getKey());
            progress.dismiss();

            Toast.makeText(getBaseContext(), "Changes Saved!", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            Toast.makeText(getBaseContext(), "Please complete fields", Toast.LENGTH_LONG).show();

    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);



        final String ccode = et_CourseCode.getText().toString();
        final String cname = et_CourseName.getText().toString();

        if(!TextUtils.isEmpty(ccode) && !TextUtils.isEmpty(cname)){
            progress.setMessage("Saving Changes");
            progress.show();

            DatabaseReference newCourse = mDatabase.child(id);

            newCourse.child(Course.COL_CODE).setValue(ccode);
            newCourse.child(Course.COL_NAME).setValue(cname);
            progress.dismiss();
            Toast.makeText(getBaseContext(), "Changes Saved!", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            Toast.makeText(getBaseContext(), "Please complete fields", Toast.LENGTH_LONG).show();

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
