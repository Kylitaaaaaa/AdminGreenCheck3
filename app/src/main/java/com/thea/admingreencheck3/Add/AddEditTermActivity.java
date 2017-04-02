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
import android.widget.TextView;

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
import com.thea.admingreencheck3.GenListActivity;
import com.thea.admingreencheck3.GenListAddTerm;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;
import com.thea.admingreencheck3.View.AcademicYearActivity;
import com.thea.admingreencheck3.View.BuildingActivity;
import com.thea.admingreencheck3.View.FacultyActivity;

import org.w3c.dom.Text;

import static android.R.attr.name;

public class AddEditTermActivity extends AppCompatActivity {

    EditText et_term;
    TextView tv_AcadYear;
    Button btn_add, btn_edit;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase, mDatabaseAY;
    private int currProcess;
    private String id, ayID, ayStart, ayEnd, origID;


    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);

        et_term = (EditText) findViewById(R.id.et_term);
        tv_AcadYear = (TextView) findViewById(R.id.tv_AcadYear);

        btn_add = (Button) findViewById(R.id.btn_Add);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Term.TABLE_NAME);
        mDatabaseAY = FirebaseDatabase.getInstance().getReference().child(AcademicYear.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 0 ) {
            //add
            btn_add.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
        }
        else{
            btn_add.setVisibility(View.GONE);
            btn_edit.setVisibility(View.VISIBLE);

            id = getIntent().getExtras().getString(Term.COL_TERM_ID);

            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String num = (String) dataSnapshot.child(Term.COL_TERM_NUM).getValue();
                    ayStart = (String) dataSnapshot.child(Term.COL_ACAD_START).getValue();
                    ayEnd = (String) dataSnapshot.child(Term.COL_ACAD_END).getValue();
                    ayID = (String) dataSnapshot.child(Term.COL_ACAD_ID).getValue();
                    origID = (String) dataSnapshot.child(Term.COL_ACAD_ID).getValue();


                    et_term.setText(num);
                    tv_AcadYear.setText(ayStart + " - " + ayEnd);
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

        tv_AcadYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), GenListAddTerm.class);
                startActivityForResult(i, 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                ayID = data.getStringExtra(AcademicYear.COL_ACAD_ID);


                mDatabaseAY.child(ayID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ayStart = (String) dataSnapshot.child(AcademicYear.COL_ACAD_START).getValue();
                        ayEnd = (String) dataSnapshot.child(AcademicYear.COL_ACAD_END).getValue();
                        tv_AcadYear.setText(ayStart + " - " + ayEnd);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public void startAdding(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Adding Term");
        progress.show();


        final String termNum = et_term.getText().toString();

        if(!TextUtils.isEmpty(termNum) &&
                !TextUtils.isEmpty(ayStart) &&
                !TextUtils.isEmpty(ayEnd)){

            DatabaseReference newThing = mDatabase.push();

            newThing.child(Term.COL_TERM_NUM).setValue(termNum);
            newThing.child(Term.COL_ACAD_START).setValue(ayStart);
            newThing.child(Term.COL_ACAD_END).setValue(ayEnd);
            newThing.child(Term.COL_ACAD_ID).setValue(ayID);

            DatabaseReference updateYear = mDatabaseAY.child(ayID).child(Term.COL_TERM).child(newThing.getKey());
            updateYear.child(Term.COL_TERM_NUM).setValue(termNum);
            updateYear.child(Term.COL_ACAD_START).setValue(ayStart);
            updateYear.child(Term.COL_ACAD_END).setValue(ayEnd);
            updateYear.child(Term.COL_ACAD_ID).setValue(ayID);
            updateYear.child(Term.COL_TERM_ID).setValue(newThing.getKey());


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

        final String num = et_term.getText().toString();

        if(!TextUtils.isEmpty(num) &&
                !TextUtils.isEmpty(ayStart) &&
                !TextUtils.isEmpty(ayEnd)){

            final DatabaseReference newThing = mDatabase.child(id);
            newThing.child(Term.COL_TERM_NUM).setValue(num);
            newThing.child(Term.COL_ACAD_START).setValue(ayStart);
            newThing.child(Term.COL_ACAD_END).setValue(ayEnd);
            newThing.child(Term.COL_ACAD_ID).setValue(ayID);

            if(!origID.equals(ayID)) {
                DatabaseReference updateYear = mDatabaseAY.child(origID).child(Term.COL_TERM);
                updateYear.child(id).removeValue();
            }

            DatabaseReference updateYear2 = mDatabaseAY.child(ayID).child(Term.COL_TERM).child(id);
            updateYear2.child(Term.COL_TERM_NUM).setValue(num);
            updateYear2.child(Term.COL_ACAD_START).setValue(ayStart);
            updateYear2.child(Term.COL_ACAD_END).setValue(ayEnd);
            updateYear2.child(Term.COL_ACAD_ID).setValue(ayID);

            //startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
        progress.dismiss();
        //Fragment fragment = new AcademicYearActivity();
        finish();

    }

}

