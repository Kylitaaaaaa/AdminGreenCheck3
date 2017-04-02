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
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.squareup.picasso.Picasso;
        import com.thea.admingreencheck3.Building;
        import com.thea.admingreencheck3.Course;
        import com.thea.admingreencheck3.Faculty;
        import com.thea.admingreencheck3.MainActivity;
        import com.thea.admingreencheck3.R;
        import com.thea.admingreencheck3.Room;
        import com.thea.admingreencheck3.View.BuildingActivity;
        import com.thea.admingreencheck3.View.FacultyActivity;

public class AddEditBldgActivity extends AppCompatActivity {

    EditText et_BldgName;
    Button btn_add, btn_edit;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase, mDatabaseRoom;
    private int currProcess;
    private String id;


    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_bldg);

        et_BldgName = (EditText) findViewById(R.id.et_name);
        btn_add = (Button) findViewById(R.id.btn_Add);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);
        mDatabaseRoom = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);


        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 0 ) {
            //add
            btn_add.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
        }
        else{
            btn_add.setVisibility(View.GONE);
            btn_edit.setVisibility(View.VISIBLE);

            id = getIntent().getExtras().getString(Building.COL_ID);

            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child(Building.COL_NAME).getValue();

                    et_BldgName.setText(name);
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

        progress.setMessage("Adding Course");
        progress.show();


        final String name = et_BldgName.getText().toString();

        if(!TextUtils.isEmpty(name)){

            DatabaseReference newFaculty = mDatabase.push();

            newFaculty.child(Building.COL_NAME).setValue(name);
            progress.dismiss();

            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }

        progress.dismiss();
        Fragment fragment = new BuildingActivity();

    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Saving Changes");
        progress.show();

        final String name = et_BldgName.getText().toString();

        if(!TextUtils.isEmpty(name)){

            DatabaseReference newCourse = mDatabase.child(id);

            newCourse.child(Building.COL_NAME).setValue(name);

            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    DataSnapshot trysnap = dataSnapshot.child(Building.COL_ROOM);
                    Iterable<DataSnapshot> tryChildren = trysnap.getChildren();

                    for (DataSnapshot ty : tryChildren) {
                        Room huh = ty.getValue(Room.class);

                        DatabaseReference newCourse = mDatabaseRoom.child(huh.getRoom_id());

                        newCourse.child(Room.COL_BUILDING_NAME).setValue(name);

                        DatabaseReference updateBuilding = mDatabase.child(id).child(Building.COL_ROOM).child(huh.getRoom_id());

                        updateBuilding.child(Room.COL_BUILDING_NAME).setValue(name);

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });



            mDatabaseRoom.child(Room.COL_BUILDING_ID).equalTo(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child(Room.COL_BUILDING_NAME).getValue();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            progress.dismiss();

            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
        progress.dismiss();
        Fragment fragment = new BuildingActivity();

    }

}

