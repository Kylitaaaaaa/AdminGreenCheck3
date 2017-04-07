package com.thea.admingreencheck3.ViewIndiv;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Add.AddEditChecker;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Checker;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.IDClass;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;

public class ViewRoomActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase, mDatabaseBuilding, mDatabaseCO, mDatabaseC, mDatabaseF;

    private TextView tv_roomNumber, tv_building;
    String bldgID;
    Button btn_A, btn_B, btn_C, btn_D, btn_E, btn_F, btn_G;

    private Boolean a = false,
            b = false,
            c = false,
            d = false,
            e = false,
            f = false,
            g = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);
        mDatabaseBuilding = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);
        mDatabaseCO = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseC = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
        mDatabaseF = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);

        id = getIntent().getExtras().getString(Room.COL_ROOM_ID);

        tv_roomNumber = (TextView) findViewById(R.id.tv_roomNumber);
        tv_building = (TextView) findViewById(R.id.tv_building);

        btn_A = (Button) findViewById(R.id.btn_A);
        btn_B = (Button) findViewById(R.id.btn_B);
        btn_C = (Button) findViewById(R.id.btn_C);
        btn_D = (Button) findViewById(R.id.btn_D);
        btn_E = (Button) findViewById(R.id.btn_E);
        btn_F = (Button) findViewById(R.id.btn_F);
        btn_G = (Button) findViewById(R.id.btn_G);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String room_num = (String) dataSnapshot.child(Room.COL_NAME).getValue();
                bldgID = (String) dataSnapshot.child(Room.COL_BUILDING_ID).getValue();
                tv_roomNumber.setText(room_num);
                if(bldgID !=null) {

                    mDatabaseBuilding.child(bldgID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String bldgName = (String) dataSnapshot.child(Building.COL_NAME).getValue();
                            tv_building.setText(bldgName);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }




                String r_id= (String) dataSnapshot.child(Room.COL_ROT_ID).getValue();
                if(r_id != null) {
                    a = false;
                    b = false;
                    c = false;
                    d = false;
                    e = false;
                    f = false;
                    g = false;
                    Log.i("huh", "radsf " + r_id);

                    if (r_id.indexOf('A') >= 0) {
                        btn_A.setPressed(true);
                        btn_A.setBackgroundResource(R.drawable.round_button_green);
                        btn_A.setTextColor(Color.parseColor("#ffffff"));
                        a = true;
                    }
                    else {
                        btn_A.setBackgroundResource(R.drawable.round_button);
                        btn_A.setTextColor(Color.parseColor("#0f0f0f"));
                        a = false;
                    }

                    if (r_id.indexOf('B') >= 0) {
                        btn_B.setPressed(true);
                        btn_B.setBackgroundResource(R.drawable.round_button_green);
                        btn_B.setTextColor(Color.parseColor("#ffffff"));
                        b = true;
                    }
                    else{
                        btn_B.setBackgroundResource(R.drawable.round_button);
                        btn_B.setTextColor(Color.parseColor("#0f0f0f"));
                        b = false;
                    }

                    if (r_id.indexOf('C') >= 0) {
                        btn_C.setPressed(true);
                        btn_C.setBackgroundResource(R.drawable.round_button_green);
                        btn_C.setTextColor(Color.parseColor("#ffffff"));
                        c = true;
                    }
                    else{
                        btn_C.setBackgroundResource(R.drawable.round_button);
                        btn_C.setTextColor(Color.parseColor("#0f0f0f"));
                        c = false;
                    }

                    if (r_id.indexOf('D') >= 0) {
                        btn_D.setPressed(true);
                        btn_D.setBackgroundResource(R.drawable.round_button_green);
                        btn_D.setTextColor(Color.parseColor("#ffffff"));
                        d = true;
                    }
                    else{
                        btn_D.setBackgroundResource(R.drawable.round_button);
                        btn_D.setTextColor(Color.parseColor("#0f0f0f"));
                        d = false;
                    }

                    if (r_id.indexOf('E') >= 0) {
                        btn_E.setPressed(true);
                        btn_E.setBackgroundResource(R.drawable.round_button_green);
                        btn_E.setTextColor(Color.parseColor("#ffffff"));
                        e = true;
                    }
                    else{
                        btn_E.setBackgroundResource(R.drawable.round_button);
                        btn_E.setTextColor(Color.parseColor("#0f0f0f"));
                        e = false;
                    }

                    if (r_id.indexOf('F') >= 0) {
                        btn_F.setPressed(true);
                        btn_F.setBackgroundResource(R.drawable.round_button_green);
                        btn_F.setTextColor(Color.parseColor("#ffffff"));
                        f = true;
                    }
                    else{
                        btn_F.setBackgroundResource(R.drawable.round_button);
                        btn_F.setTextColor(Color.parseColor("#0f0f0f"));
                        f = false;
                    }

                    if (r_id.indexOf('G') >= 0) {
                        btn_G.setPressed(true);
                        btn_G.setBackgroundResource(R.drawable.round_button_green);
                        btn_G.setTextColor(Color.parseColor("#ffffff"));
                        g = true;
                    }
                    else{
                        btn_G.setBackgroundResource(R.drawable.round_button);
                        btn_G.setTextColor(Color.parseColor("#0f0f0f"));
                        g = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        if (itemid == R.id.action_edit) {
            Intent i = new Intent(getBaseContext(), AddEditRoomActivity.class);
            //add = 0
            //edit = 1
            i.putExtra("currProcess", 1 );
            i.putExtra(Room.COL_ROOM_ID, id);
            startActivity(i);

        } else if (itemid == R.id.action_delete) {
            delete();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(){
        DatabaseReference update = mDatabaseBuilding.child(bldgID).child(Room.TABLE_NAME);
        update.child(id).removeValue();

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot trysnap = dataSnapshot.child(CourseOffering.TABLE_NAME);
                Iterable<DataSnapshot> tryChildren = trysnap.getChildren();

                for (DataSnapshot ty : tryChildren) {
                    IDClass idc = ty.getValue(IDClass.class);
                    Log.i("huh", "idc " + idc.getId());
                    final String idCO = idc.getId();

                    mDatabaseCO.child(idCO).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            String tempID = (String) dataSnapshot2.child(CourseOffering.COL_C_ID).getValue();
                            if(tempID != null) {
                                Log.i("huh", "tempID1 " + tempID);
                                mDatabaseC.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }
                            if(tempID != null) {
                                tempID = (String) dataSnapshot2.child(CourseOffering.COL_F_ID).getValue();
                                Log.i("huh", "tempID2 " + tempID);
                                mDatabaseF.child(tempID).child(CourseOffering.TABLE_NAME).child(idCO).removeValue();
                            }

                            if(tempID != null) {
                                tempID = (String) dataSnapshot2.child(CourseOffering.COL_B_ID).getValue();
                                Log.i("huh", "tempID2 " + tempID);
                                mDatabaseF.child(tempID).child(Building.TABLE_NAME).child(idCO).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    mDatabaseCO.child(idc.getId()).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        mDatabase.child(id).removeValue();
    }
}
