package com.thea.admingreencheck3.ViewIndiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.Term;

public class ViewRoomActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase, mDatabaseBuilding;

    private TextView tv_roomNumber, tv_building;

    private Button btn_delete, btn_edit;
    String bldgID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);
        mDatabaseBuilding = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);

        id = getIntent().getExtras().getString(Room.COL_ROOM_ID);

        tv_roomNumber = (TextView) findViewById(R.id.tv_roomNumber);
        tv_building = (TextView) findViewById(R.id.tv_building);

        btn_delete = (Button) findViewById(R.id.btn_Delete);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String room_num = (String) dataSnapshot.child(Room.COL_NAME).getValue();
                String bldg_name = (String) dataSnapshot.child(Room.COL_BUILDING_NAME).getValue();
                bldgID = (String) dataSnapshot.child(Room.COL_BUILDING_ID).getValue();

                tv_roomNumber.setText(room_num);
                tv_building.setText(bldg_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference update = mDatabaseBuilding.child(bldgID).child(Building.COL_ROOM);
                update.child(id).removeValue();

                mDatabase.child(id).removeValue();
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddEditRoomActivity.class);
                //add = 0
                //edit = 1
                i.putExtra("currProcess", 1 );
                i.putExtra(Room.COL_ROOM_ID, id);
                startActivity(i);


            }
        });


    }
}
