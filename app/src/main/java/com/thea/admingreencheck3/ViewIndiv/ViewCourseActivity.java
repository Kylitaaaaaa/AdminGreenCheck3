package com.thea.admingreencheck3.ViewIndiv;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.thea.admingreencheck3.Add.AddEditCourseActivity;
        import com.thea.admingreencheck3.Course;
        import com.thea.admingreencheck3.Faculty;
        import com.thea.admingreencheck3.R;

public class ViewCourseActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase;

    private TextView tv_CourseCode, tv_CourseName;

    private Button btn_delete, btn_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);

        id = getIntent().getExtras().getString(Course.COL_ID);

        tv_CourseCode = (TextView) findViewById(R.id.tv_CourseCode);
        tv_CourseName = (TextView) findViewById(R.id.tv_Term);

        btn_delete = (Button) findViewById(R.id.btn_Delete);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

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

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(id).removeValue();
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddEditCourseActivity.class);
                //add = 0
                //edit = 1
                i.putExtra("currProcess", 1 );
                i.putExtra(Faculty.COL_ID, id);
                startActivity(i);


            }
        });


    }
}

