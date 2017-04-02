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
        import com.thea.admingreencheck3.Course;
        import com.thea.admingreencheck3.Faculty;
        import com.thea.admingreencheck3.MainActivity;
        import com.thea.admingreencheck3.R;
        import com.thea.admingreencheck3.View.FacultyActivity;

public class AddEditCourseActivity extends AppCompatActivity {

    EditText et_CourseCode, et_CourseName;
    Button btn_addCourse, btn_editCourse;
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
        btn_addCourse = (Button) findViewById(R.id.btn_AddCourse);
        btn_editCourse = (Button) findViewById(R.id.btn_EditCourse);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 0 ) {
            //add
            btn_addCourse.setVisibility(View.VISIBLE);
            btn_editCourse.setVisibility(View.GONE);
        }
        else{
            btn_addCourse.setVisibility(View.GONE);
            btn_editCourse.setVisibility(View.VISIBLE);

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


        btn_addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdding();
            }
        });

        btn_editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditing();

            }
        });
    }

    public void startAdding(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Adding Course");
        progress.show();


        final String ccode = et_CourseCode.getText().toString();
        final String cname = et_CourseName.getText().toString();

        if(!TextUtils.isEmpty(ccode) && !TextUtils.isEmpty(cname)){

            DatabaseReference newFaculty = mDatabase.push();

            newFaculty.child(Course.COL_CODE).setValue(ccode);
            newFaculty.child(Course.COL_NAME).setValue(cname);
            progress.dismiss();

            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }

    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Saving Changes");
        progress.show();

        final String ccode = et_CourseCode.getText().toString();
        final String cname = et_CourseName.getText().toString();

        if(!TextUtils.isEmpty(ccode) && !TextUtils.isEmpty(cname)){

            DatabaseReference newCourse = mDatabase.child(id);

            newCourse.child(Course.COL_CODE).setValue(ccode);
            newCourse.child(Course.COL_NAME).setValue(cname);
            progress.dismiss();

            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }

    }

}
