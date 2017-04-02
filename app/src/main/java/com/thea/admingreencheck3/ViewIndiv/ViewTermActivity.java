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
import com.thea.admingreencheck3.Add.AddEditTermActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Term;

public class ViewTermActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase, mDatabaseAY;

    private TextView tv_term, tv_acadyear;

    private Button btn_delete, btn_edit;

    String ayID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Term.TABLE_NAME);
        mDatabaseAY = FirebaseDatabase.getInstance().getReference().child(AcademicYear.TABLE_NAME);

        id = getIntent().getExtras().getString(Term.COL_TERM_ID);

        tv_term = (TextView) findViewById(R.id.tv_term);
        tv_acadyear = (TextView) findViewById(R.id.tv_acadyear);

        btn_delete = (Button) findViewById(R.id.btn_Delete);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String termNum = (String) dataSnapshot.child(Term.COL_TERM_NUM).getValue();
                String start = (String) dataSnapshot.child(Term.COL_ACAD_START).getValue();
                String end = (String) dataSnapshot.child(Term.COL_ACAD_END).getValue();
                ayID = (String) dataSnapshot.child(Term.COL_ACAD_ID).getValue();

                tv_term.setText(termNum);
                tv_acadyear.setText(start + " - " + end);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updateYear = mDatabaseAY.child(ayID).child(Term.COL_TERM);
                updateYear.child(id).removeValue();

                mDatabase.child(id).removeValue();
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddEditTermActivity.class);
                //add = 0
                //edit = 1
                i.putExtra("currProcess", 1 );
                i.putExtra(Term.COL_TERM_ID, id);
                startActivity(i);


            }
        });


    }
}


