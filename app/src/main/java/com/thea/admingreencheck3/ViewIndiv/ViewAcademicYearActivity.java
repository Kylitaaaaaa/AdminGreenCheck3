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
import com.thea.admingreencheck3.AcademicYear;
import com.thea.admingreencheck3.Add.AddEditAcademicYearActivity;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.R;

public class ViewAcademicYearActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase;

    private TextView tv_Start, tv_End;

    private Button btn_delete, btn_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academic_year);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(AcademicYear.TABLE_NAME);

        id = getIntent().getExtras().getString(Building.COL_ID);

        tv_Start = (TextView) findViewById(R.id.tv_Start);
        tv_End = (TextView) findViewById(R.id.tv_End);

        btn_delete = (Button) findViewById(R.id.btn_Delete);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String start = (String) dataSnapshot.child(AcademicYear.COL_ACAD_START).getValue();
                String end = (String) dataSnapshot.child(AcademicYear.COL_ACAD_END).getValue();

                tv_Start.setText(start);
                tv_End.setText(end);
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
                Intent i = new Intent(getBaseContext(), AddEditAcademicYearActivity.class);
                //add = 0
                //edit = 1
                i.putExtra("currProcess", 1 );
                i.putExtra(AcademicYear.COL_ACAD_ID, id);
                startActivity(i);


            }
        });


    }
}


