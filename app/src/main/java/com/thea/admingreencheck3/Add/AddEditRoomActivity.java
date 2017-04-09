package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.GenListActivity;
import com.thea.admingreencheck3.IDClass;
import com.thea.admingreencheck3.Joint;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;

public class AddEditRoomActivity extends AppCompatActivity {

    EditText et_RoomNum;
    TextView tv_BldgName;
    Button btn_A, btn_B, btn_C, btn_D, btn_E, btn_F, btn_G;
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

        tv_BldgName = (TextView) findViewById(R.id.tv_building);
        et_RoomNum = (EditText) findViewById(R.id.tv_roomNumber);

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
        if(currProcess == 1 ) {
            getSupportActionBar().setTitle("Edit Room");

            roomID = getIntent().getExtras().getString(Room.COL_ROOM_ID);

            mDatabase.child(roomID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child(Room.COL_NAME).getValue();
                    String bldgName = (String) dataSnapshot.child(Room.COL_BUILDING_NAME).getValue();
                    bldgID = (String) dataSnapshot.child(Room.COL_BUILDING_ID).getValue();
                    origID = (String) dataSnapshot.child(Room.COL_BUILDING_ID).getValue();

                    mDatabaseBuilding.child(bldgID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String bldgName = (String) dataSnapshot.child(Building.COL_NAME).getValue();
                            tv_BldgName.setText(bldgName);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    et_RoomNum.setText(name);

                    String r_id= (String) dataSnapshot.child(Room.COL_ROT_ID).getValue();
                    if(r_id !=null) {

                        if (r_id.indexOf('A') >= 0) {
                            btn_A.setPressed(true);
                            btn_A.setBackgroundResource(R.drawable.round_button_green);
                            btn_A.setTextColor(Color.parseColor("#ffffff"));
                            a = true;
                        }

                        if (r_id.indexOf('B') >= 0) {
                            btn_B.setPressed(true);
                            btn_B.setBackgroundResource(R.drawable.round_button_green);
                            btn_B.setTextColor(Color.parseColor("#ffffff"));
                            b = true;
                        }

                        if (r_id.indexOf('C') >= 0) {
                            btn_C.setPressed(true);
                            btn_C.setBackgroundResource(R.drawable.round_button_green);
                            btn_C.setTextColor(Color.parseColor("#ffffff"));
                            c = true;
                        }

                        if (r_id.indexOf('D') >= 0) {
                            btn_D.setPressed(true);
                            btn_D.setBackgroundResource(R.drawable.round_button_green);
                            btn_D.setTextColor(Color.parseColor("#ffffff"));
                            d = true;
                        }

                        if (r_id.indexOf('E') >= 0) {
                            btn_E.setPressed(true);
                            btn_E.setBackgroundResource(R.drawable.round_button_green);
                            btn_E.setTextColor(Color.parseColor("#ffffff"));
                            e = true;
                        }

                        if (r_id.indexOf('F') >= 0) {
                            btn_F.setPressed(true);
                            btn_F.setBackgroundResource(R.drawable.round_button_green);
                            btn_F.setTextColor(Color.parseColor("#ffffff"));
                            f = true;
                        }

                        if (r_id.indexOf('G') >= 0) {
                            btn_G.setPressed(true);
                            btn_G.setBackgroundResource(R.drawable.round_button_green);
                            btn_G.setTextColor(Color.parseColor("#ffffff"));
                            g = true;
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        }
        else
            getSupportActionBar().setTitle("Add Room");


        tv_BldgName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("putangina", "putang");
                Intent i = new Intent(getBaseContext(), GenListActivity.class);
                startActivityForResult(i, 1);
            }
        });

        btn_A.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_A.setBackgroundResource(R.drawable.round_button_green);
                    btn_A.setTextColor(Color.parseColor("#ffffff"));

                    a = true;
                    unpressOthers("A");
            }
        });

        btn_B.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_B.setBackgroundResource(R.drawable.round_button_green);
                    btn_B.setTextColor(Color.parseColor("#ffffff"));

                    b = true;
                    unpressOthers("B");
            }
        });

        btn_C.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_C.setBackgroundResource(R.drawable.round_button_green);
                    btn_C.setTextColor(Color.parseColor("#ffffff"));

                    c = true;
                    unpressOthers("C");
            }
        });

        btn_D.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_D.setBackgroundResource(R.drawable.round_button_green);
                    btn_D.setTextColor(Color.parseColor("#ffffff"));

                    d = true;
                    unpressOthers("D");
            }
        });

        btn_E.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_E.setBackgroundResource(R.drawable.round_button_green);
                    btn_E.setTextColor(Color.parseColor("#ffffff"));

                    e = true;
                    unpressOthers("E");
            }
        });

        btn_F.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_F.setBackgroundResource(R.drawable.round_button_green);
                    btn_F.setTextColor(Color.parseColor("#ffffff"));

                    f = true;
                    unpressOthers("F");
            }
        });

        btn_G.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_G.setBackgroundResource(R.drawable.round_button_green);
                    btn_G.setTextColor(Color.parseColor("#ffffff"));

                    g = true;
                    unpressOthers("G");
            }
        });

    }

    public void unpressOthers(String s){
        if(s.equals("A")){
            btn_A.setBackgroundResource(R.drawable.round_button_green);
            btn_A.setTextColor(Color.parseColor("#ffffff"));
            a = true;

            btn_B.setBackgroundResource(R.drawable.round_button);
            btn_B.setTextColor(Color.parseColor("#0f0f0f"));
            b = false;

            btn_C.setBackgroundResource(R.drawable.round_button);
            btn_C.setTextColor(Color.parseColor("#0f0f0f"));
            c = false;

            btn_D.setBackgroundResource(R.drawable.round_button);
            btn_D.setTextColor(Color.parseColor("#0f0f0f"));
            d = false;

            btn_E.setBackgroundResource(R.drawable.round_button);
            btn_E.setTextColor(Color.parseColor("#0f0f0f"));
            e = false;

            btn_F.setBackgroundResource(R.drawable.round_button);
            btn_F.setTextColor(Color.parseColor("#0f0f0f"));
            f = false;

            btn_G.setBackgroundResource(R.drawable.round_button);
            btn_G.setTextColor(Color.parseColor("#0f0f0f"));
            g = false;
        }
        else if(s.equals("B")){
            btn_A.setBackgroundResource(R.drawable.round_button);
            btn_A.setTextColor(Color.parseColor("#0f0f0f"));
            a = false;

            btn_B.setBackgroundResource(R.drawable.round_button_green);
            btn_B.setTextColor(Color.parseColor("#ffffff"));
            b = true;

            btn_C.setBackgroundResource(R.drawable.round_button);
            btn_C.setTextColor(Color.parseColor("#0f0f0f"));
            c = false;

            btn_D.setBackgroundResource(R.drawable.round_button);
            btn_D.setTextColor(Color.parseColor("#0f0f0f"));
            d = false;

            btn_E.setBackgroundResource(R.drawable.round_button);
            btn_E.setTextColor(Color.parseColor("#0f0f0f"));
            e = false;

            btn_F.setBackgroundResource(R.drawable.round_button);
            btn_F.setTextColor(Color.parseColor("#0f0f0f"));
            f = false;

            btn_G.setBackgroundResource(R.drawable.round_button);
            btn_G.setTextColor(Color.parseColor("#0f0f0f"));
            g = false;
        }
        else if(s.equals("C")){
            btn_A.setBackgroundResource(R.drawable.round_button);
            btn_A.setTextColor(Color.parseColor("#0f0f0f"));
            a = false;

            btn_B.setBackgroundResource(R.drawable.round_button);
            btn_B.setTextColor(Color.parseColor("#0f0f0f"));
            b = false;

            btn_C.setBackgroundResource(R.drawable.round_button_green);
            btn_C.setTextColor(Color.parseColor("#ffffff"));
            c = true;

            btn_D.setBackgroundResource(R.drawable.round_button);
            btn_D.setTextColor(Color.parseColor("#0f0f0f"));
            d = false;

            btn_E.setBackgroundResource(R.drawable.round_button);
            btn_E.setTextColor(Color.parseColor("#0f0f0f"));
            e = false;

            btn_F.setBackgroundResource(R.drawable.round_button);
            btn_F.setTextColor(Color.parseColor("#0f0f0f"));
            f = false;

            btn_G.setBackgroundResource(R.drawable.round_button);
            btn_G.setTextColor(Color.parseColor("#0f0f0f"));
            g = false;
        }
        else if(s.equals("D")){
            btn_A.setBackgroundResource(R.drawable.round_button);
            btn_A.setTextColor(Color.parseColor("#0f0f0f"));
            a = false;

            btn_B.setBackgroundResource(R.drawable.round_button);
            btn_B.setTextColor(Color.parseColor("#0f0f0f"));
            b = false;

            btn_C.setBackgroundResource(R.drawable.round_button);
            btn_C.setTextColor(Color.parseColor("#0f0f0f"));
            c = false;

            btn_D.setBackgroundResource(R.drawable.round_button_green);
            btn_D.setTextColor(Color.parseColor("#ffffff"));
            d = true;

            btn_E.setBackgroundResource(R.drawable.round_button);
            btn_E.setTextColor(Color.parseColor("#0f0f0f"));
            e = false;

            btn_F.setBackgroundResource(R.drawable.round_button);
            btn_F.setTextColor(Color.parseColor("#0f0f0f"));
            f = false;

            btn_G.setBackgroundResource(R.drawable.round_button);
            btn_G.setTextColor(Color.parseColor("#0f0f0f"));
            g = false;
        }
        else if(s.equals("E")){
            btn_A.setBackgroundResource(R.drawable.round_button);
            btn_A.setTextColor(Color.parseColor("#0f0f0f"));
            a = false;

            btn_B.setBackgroundResource(R.drawable.round_button);
            btn_B.setTextColor(Color.parseColor("#0f0f0f"));
            b = false;

            btn_C.setBackgroundResource(R.drawable.round_button);
            btn_C.setTextColor(Color.parseColor("#0f0f0f"));
            c = false;

            btn_D.setBackgroundResource(R.drawable.round_button);
            btn_D.setTextColor(Color.parseColor("#0f0f0f"));
            d = false;

            btn_E.setBackgroundResource(R.drawable.round_button_green);
            btn_E.setTextColor(Color.parseColor("#ffffff"));
            e = true;

            btn_F.setBackgroundResource(R.drawable.round_button);
            btn_F.setTextColor(Color.parseColor("#0f0f0f"));
            f = false;

            btn_G.setBackgroundResource(R.drawable.round_button);
            btn_G.setTextColor(Color.parseColor("#0f0f0f"));
            g = false;
        }
        else if(s.equals("F")){
            btn_A.setBackgroundResource(R.drawable.round_button);
            btn_A.setTextColor(Color.parseColor("#0f0f0f"));
            a = false;

            btn_B.setBackgroundResource(R.drawable.round_button);
            btn_B.setTextColor(Color.parseColor("#0f0f0f"));
            b = false;

            btn_C.setBackgroundResource(R.drawable.round_button);
            btn_C.setTextColor(Color.parseColor("#0f0f0f"));
            c = false;

            btn_D.setBackgroundResource(R.drawable.round_button);
            btn_D.setTextColor(Color.parseColor("#0f0f0f"));
            d = false;

            btn_E.setBackgroundResource(R.drawable.round_button);
            btn_E.setTextColor(Color.parseColor("#0f0f0f"));
            e = false;

            btn_F.setBackgroundResource(R.drawable.round_button_green);
            btn_F.setTextColor(Color.parseColor("#ffffff"));
            f = true;

            btn_G.setBackgroundResource(R.drawable.round_button);
            btn_G.setTextColor(Color.parseColor("#0f0f0f"));
            g = false;
        }
        else if(s.equals("G")){
            btn_A.setBackgroundResource(R.drawable.round_button);
            btn_A.setTextColor(Color.parseColor("#0f0f0f"));
            a = false;

            btn_B.setBackgroundResource(R.drawable.round_button);
            btn_B.setTextColor(Color.parseColor("#0f0f0f"));
            b = false;

            btn_C.setBackgroundResource(R.drawable.round_button);
            btn_C.setTextColor(Color.parseColor("#0f0f0f"));
            c = false;

            btn_D.setBackgroundResource(R.drawable.round_button);
            btn_D.setTextColor(Color.parseColor("#0f0f0f"));
            d = false;

            btn_E.setBackgroundResource(R.drawable.round_button);
            btn_E.setTextColor(Color.parseColor("#0f0f0f"));
            e = false;

            btn_F.setBackgroundResource(R.drawable.round_button);
            btn_F.setTextColor(Color.parseColor("#0f0f0f"));
            f = false;

            btn_G.setBackgroundResource(R.drawable.round_button_green);
            btn_G.setTextColor(Color.parseColor("#ffffff"));
            g = true;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                bldgID = data.getStringExtra(Building.COL_ID);

                Log.i("got", bldgID);

                if(bldgID != null) {

                    mDatabaseBuilding.child(bldgID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            bldgName = (String) dataSnapshot.child(Building.COL_NAME).getValue();
                            Log.i("got", bldgName + "");
                            tv_BldgName.setText(bldgName);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        }
    }

    public void startAdding(){
        final ProgressDialog progress = new ProgressDialog(this);




        final String roomNum = et_RoomNum.getText().toString();
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


        if(!TextUtils.isEmpty(roomNum) &&
                !TextUtils.isEmpty(bldgID) &&
                rotid != "") {
            progress.setMessage("Adding Room");
            progress.show();

            DatabaseReference newRoom = mDatabase.push();
            newRoom.child(Room.COL_NAME).setValue(roomNum);
            newRoom.child(Room.COL_BUILDING_ID).setValue(bldgID);
            newRoom.child(Room.COL_ROOM_ID).setValue(newRoom.getKey());



            newRoom.child(Room.COL_ROT_ID).setValue(rotid);

            DatabaseReference updateBuilding = mDatabaseBuilding.child(bldgID).child(Room.TABLE_NAME).child(newRoom.getKey());
            updateBuilding.child(IDClass.COL_ID).setValue(newRoom.getKey());
            progress.dismiss();
            Toast.makeText(getBaseContext(), "Successfully Added Room!", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            Toast.makeText(getBaseContext(), "Please complete fields", Toast.LENGTH_LONG).show();
    }

    public void startEditing(){
        final ProgressDialog progress = new ProgressDialog(this);

        final String roomNum = et_RoomNum.getText().toString();

        rotid = "";

        Log.i("huh", a + "");
        if(a) {
            rotid = rotid.concat("A");

        }

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


        if(!TextUtils.isEmpty(roomNum) &&
                !TextUtils.isEmpty(bldgID)&&
                rotid != "") {

            progress.setMessage("Saving Changes");
            progress.show();

            DatabaseReference newRoom = mDatabase.child(roomID);
            newRoom.child(Room.COL_NAME).setValue(roomNum);
            newRoom.child(Room.COL_BUILDING_ID).setValue(bldgID);
            newRoom.child(Room.COL_ROT_ID).setValue(rotid);


            if(!origID.equals(bldgID)) {
                DatabaseReference update = mDatabaseBuilding.child(origID).child(Room.TABLE_NAME);
                update.child(roomID).removeValue();
            }

            DatabaseReference updateBuilding = mDatabaseBuilding.child(bldgID).child(Room.TABLE_NAME).child(newRoom.getKey());
            updateBuilding.child(IDClass.COL_ID).setValue(newRoom.getKey());

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

