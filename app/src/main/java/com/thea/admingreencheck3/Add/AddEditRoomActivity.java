package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
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
import com.thea.admingreencheck3.Checker;
import com.thea.admingreencheck3.GenListActivity;
import com.thea.admingreencheck3.Joint;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;

public class AddEditRoomActivity extends AppCompatActivity {

    EditText et_RoomNum;
    TextView tv_BldgName;
    Button btn_add, btn_edit, btn_A, btn_B, btn_C, btn_D, btn_E, btn_F, btn_G;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseBuilding;
    private int currProcess;
    private String bldgID, origID;
    private String bldgName;
    private String roomID;
    private Boolean a = false,
            b = false,
            c = false,
            d = false,
            e = false,
            f = false,
            g = false;
    String rotid;

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

        btn_A = (Button) findViewById(R.id.btn_A);
        btn_B = (Button) findViewById(R.id.btn_B);
        btn_C = (Button) findViewById(R.id.btn_C);
        btn_D = (Button) findViewById(R.id.btn_D);
        btn_E = (Button) findViewById(R.id.btn_E);
        btn_F = (Button) findViewById(R.id.btn_F);
        btn_G = (Button) findViewById(R.id.btn_G);

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

                    String r_id= (String) dataSnapshot.child(Room.COL_ROT_ID).getValue();

                    if(r_id.indexOf('A') >=0){
                        btn_A.setPressed(true);
                        btn_A.setBackgroundResource(R.drawable.round_button_green);
                        btn_A.setTextColor(Color.parseColor("#ffffff"));
                        a = true;
                    }

                    if(r_id.indexOf('B') >=0){
                        btn_B.setPressed(true);
                        btn_B.setBackgroundResource(R.drawable.round_button_green);
                        btn_B.setTextColor(Color.parseColor("#ffffff"));
                        b = true;
                    }

                    if(r_id.indexOf('C') >=0){
                        btn_C.setPressed(true);
                        btn_C.setBackgroundResource(R.drawable.round_button_green);
                        btn_C.setTextColor(Color.parseColor("#ffffff"));
                        c = true;
                    }

                    if(r_id.indexOf('D') >=0){
                        btn_D.setPressed(true);
                        btn_D.setBackgroundResource(R.drawable.round_button_green);
                        btn_D.setTextColor(Color.parseColor("#ffffff"));
                        d = true;
                    }

                    if(r_id.indexOf('E') >=0){
                        btn_E.setPressed(true);
                        btn_E.setBackgroundResource(R.drawable.round_button_green);
                        btn_E.setTextColor(Color.parseColor("#ffffff"));
                        e = true;
                    }

                    if(r_id.indexOf('F') >=0){
                        btn_F.setPressed(true);
                        btn_F.setBackgroundResource(R.drawable.round_button_green);
                        btn_F.setTextColor(Color.parseColor("#ffffff"));
                        f = true;
                    }

                    if(r_id.indexOf('G') >=0){
                        btn_G.setPressed(true);
                        btn_G.setBackgroundResource(R.drawable.round_button_green);
                        btn_G.setTextColor(Color.parseColor("#ffffff"));
                        g = true;
                    }

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

        btn_A.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_A.isPressed()) {
                    btn_A.setPressed(false);
                    btn_A.setBackgroundResource(R.drawable.round_button);
                    btn_A.setTextColor(Color.parseColor("#0f0f0f"));
                    a = false;
                }
                else {
                    btn_A.setPressed(true);
                    btn_A.setBackgroundResource(R.drawable.round_button_green);
                    btn_A.setTextColor(Color.parseColor("#ffffff"));
                    a = true;
                }
                return true;
            }
        });

        btn_B.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_B.isPressed()) {
                    btn_B.setPressed(false);
                    btn_B.setBackgroundResource(R.drawable.round_button);
                    btn_B.setTextColor(Color.parseColor("#0f0f0f"));
                    b = false;
                }
                else {
                    btn_B.setPressed(true);
                    btn_B.setBackgroundResource(R.drawable.round_button_green);
                    btn_B.setTextColor(Color.parseColor("#ffffff"));
                    b = true;
                }
                return true;
            }
        });

        btn_C.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_C.isPressed()) {
                    btn_C.setPressed(false);
                    btn_C.setBackgroundResource(R.drawable.round_button);
                    btn_C.setTextColor(Color.parseColor("#0f0f0f"));
                    c = false;
                }
                else {
                    btn_C.setPressed(true);
                    btn_C.setBackgroundResource(R.drawable.round_button_green);
                    btn_C.setTextColor(Color.parseColor("#ffffff"));
                    c = true;
                }
                return true;
            }
        });

        btn_D.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_D.isPressed()) {
                    btn_D.setPressed(false);
                    btn_D.setBackgroundResource(R.drawable.round_button);
                    btn_D.setTextColor(Color.parseColor("#0f0f0f"));
                    d = false;
                }
                else {
                    btn_D.setPressed(true);
                    btn_D.setBackgroundResource(R.drawable.round_button_green);
                    btn_D.setTextColor(Color.parseColor("#ffffff"));
                    d = true;
                }
                return true;
            }
        });

        btn_E.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_E.isPressed()) {
                    btn_E.setPressed(false);
                    btn_E.setBackgroundResource(R.drawable.round_button);
                    btn_E.setTextColor(Color.parseColor("#0f0f0f"));
                    e = false;
                }
                else {
                    btn_E.setPressed(true);
                    btn_E.setBackgroundResource(R.drawable.round_button_green);
                    btn_E.setTextColor(Color.parseColor("#ffffff"));
                    e = true;
                }
                return true;
            }
        });

        btn_F.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_F.isPressed()) {
                    btn_F.setPressed(false);
                    btn_F.setBackgroundResource(R.drawable.round_button);
                    btn_F.setTextColor(Color.parseColor("#0f0f0f"));
                    f = false;
                }
                else {
                    btn_F.setPressed(true);
                    btn_F.setBackgroundResource(R.drawable.round_button_green);
                    btn_F.setTextColor(Color.parseColor("#ffffff"));
                    f = true;
                }
                return true;
            }
        });

        btn_G.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(btn_G.isPressed()) {
                    btn_G.setPressed(false);
                    btn_G.setBackgroundResource(R.drawable.round_button);
                    btn_G.setTextColor(Color.parseColor("#0f0f0f"));
                    g = false;
                }
                else {
                    btn_G.setPressed(true);
                    btn_G.setBackgroundResource(R.drawable.round_button_green);
                    btn_G.setTextColor(Color.parseColor("#ffffff"));
                    g = true;
                }
                return true;
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


            rotid = "";

            if(a)
                rotid = rotid.concat("A");

            if(b)
                rotid = rotid.concat("B");

            if(c)
                rotid = rotid.concat("C");

            if(d)
                rotid = rotid.concat("D");

            if(e)
                rotid = rotid.concat("E");

            if(f)
                rotid = rotid.concat("F");

            if(g)
                rotid = rotid.concat("G");

            newRoom.child(Room.COL_ROT_ID).setValue(rotid);

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

