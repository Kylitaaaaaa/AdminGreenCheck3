package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.GenListActivity;
import com.thea.admingreencheck3.Joint;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;

public class AddEditRoomActivity extends AppCompatActivity {

    EditText et_RoomNum;
    TextView tv_BldgName;
    Button btn_add, btn_edit;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseBuilding;
    private int currProcess;
    private String bldgID, origID;
    private String bldgName;
    private String roomID;

    private static final int GALLERY_REQUEST = 1;
    Joint j = new Joint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);

        tv_BldgName = (TextView) findViewById(R.id.et_End);
        tv_BldgName.setText("huh");
        et_RoomNum = (EditText) findViewById(R.id.et_Start);
        btn_add = (Button) findViewById(R.id.btn_Add);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);
        mDatabaseBuilding = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);

        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 0 ) {
            //add
            btn_add.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
        }

        else{

            btn_add.setVisibility(View.GONE);
            btn_edit.setVisibility(View.VISIBLE);

            roomID = getIntent().getExtras().getString(Room.COL_ROOM_ID);

            mDatabase.child(roomID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child(Room.COL_NAME).getValue();
                    String bldgName = (String) dataSnapshot.child(Room.COL_BUILDING_NAME).getValue();
                    bldgID = (String) dataSnapshot.child(Room.COL_BUILDING_ID).getValue();
                    origID = (String) dataSnapshot.child(Room.COL_BUILDING_ID).getValue();

                    et_RoomNum.setText(name);
                    tv_BldgName.setText(bldgName);
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

        tv_BldgName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("putangina", "putang");
                Intent i = new Intent(getBaseContext(), GenListActivity.class);
                startActivityForResult(i, 1);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                bldgID = data.getStringExtra(Building.COL_ID);

                Log.i("got", bldgID);

                mDatabaseBuilding.child(bldgID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        bldgName = (String) dataSnapshot.child(Building.COL_NAME).getValue();
                        Log.i("got", bldgName);
                        tv_BldgName.setText(bldgName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public void startAdding(){
        Log.i("huh", "hmmm");
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Adding Room");
        progress.show();


        final String roomNum = et_RoomNum.getText().toString();
        final String bldgName = tv_BldgName.getText().toString();


        if(!TextUtils.isEmpty(roomNum) &&
                !TextUtils.isEmpty(bldgID)) {

            DatabaseReference newRoom = mDatabase.push();
            newRoom.child(Room.COL_NAME).setValue(roomNum);
            newRoom.child(Room.COL_BUILDING_NAME).setValue(bldgName);
            newRoom.child(Room.COL_BUILDING_ID).setValue(bldgID);

            DatabaseReference updateBuilding = mDatabaseBuilding.child(bldgID).child(Building.COL_ROOM).child(newRoom.getKey());
            updateBuilding.child(Room.COL_ROOM_ID).setValue(newRoom.getKey());
            updateBuilding.child(Room.COL_NAME).setValue(roomNum);
            updateBuilding.child(Room.COL_BUILDING_NAME).setValue(bldgName);
            updateBuilding.child(Room.COL_BUILDING_ID).setValue(bldgID);
        }

        progress.dismiss();
        finish();

    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);

        progress.setMessage("Saving Changes");
        progress.show();

        final String roomNum = et_RoomNum.getText().toString();
        final String bldgName = tv_BldgName.getText().toString();


        if(!TextUtils.isEmpty(roomNum) &&
                !TextUtils.isEmpty(bldgID)) {

            DatabaseReference newRoom = mDatabase.child(roomID);
            newRoom.child(Room.COL_NAME).setValue(roomNum);
            newRoom.child(Room.COL_BUILDING_NAME).setValue(bldgName);
            newRoom.child(Room.COL_BUILDING_ID).setValue(bldgID);


            if(!origID.equals(bldgID)) {
                DatabaseReference update = mDatabaseBuilding.child(origID).child(Building.COL_ROOM);
                update.child(roomID).removeValue();
            }

            DatabaseReference update2 = mDatabaseBuilding.child(bldgID).child(Building.COL_ROOM).child(roomID);
            update2.child(Room.COL_NAME).setValue(roomNum);
            update2.child(Room.COL_BUILDING_NAME).setValue(bldgName);
            update2.child(Room.COL_BUILDING_ID).setValue(bldgID);


        }

        progress.dismiss();
        finish();

    }

}

