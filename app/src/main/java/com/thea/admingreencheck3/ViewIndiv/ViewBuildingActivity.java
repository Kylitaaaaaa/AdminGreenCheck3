package com.thea.admingreencheck3.ViewIndiv;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.thea.admingreencheck3.Add.AddEditBldgActivity;
        import com.thea.admingreencheck3.Building;
        import com.thea.admingreencheck3.Course;
        import com.thea.admingreencheck3.CourseOffering;
        import com.thea.admingreencheck3.Faculty;
        import com.thea.admingreencheck3.IDClass;
        import com.thea.admingreencheck3.R;
        import com.thea.admingreencheck3.Room;

public class ViewBuildingActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase, mDatabaseRoom, mDatabaseCO, mDatabaseC, mDatabaseF;

    private TextView tv_BldgName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_building);
        getSupportActionBar().setTitle("Building");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);
        mDatabaseRoom = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);
        mDatabaseCO = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseC = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
        mDatabaseF = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);

        id = getIntent().getExtras().getString(Building.COL_ID);

        tv_BldgName = (TextView) findViewById(R.id.tv_building);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child(Building.COL_NAME).getValue();

                tv_BldgName.setText(name);
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
            Intent i = new Intent(getBaseContext(), AddEditBldgActivity.class);
            //add = 0
            //edit = 1
            i.putExtra("currProcess", 1 );
            i.putExtra(Building.COL_ID, id);
            startActivity(i);

        }
        else if (itemid == R.id.action_delete) {
            AlertDialog diaBox = AskOption();
            diaBox.show();


        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(){
        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Delete Room
                DataSnapshot trysnap = dataSnapshot.child(Room.TABLE_NAME);
                Iterable<DataSnapshot> tryChildren = trysnap.getChildren();

                for (DataSnapshot ty : tryChildren) {
                    IDClass idc = ty.getValue(IDClass.class);
                    Log.i("huh", "idc " + idc.getId());
                    mDatabaseRoom.child(idc.getId()).removeValue();
                }

                //Delete CO
                trysnap = dataSnapshot.child(CourseOffering.TABLE_NAME);
                tryChildren = trysnap.getChildren();

                for (DataSnapshot ty : tryChildren) {
                    IDClass idc = ty.getValue(IDClass.class);
                    Log.i("huh", "idc2 " + idc.getId());

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
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    mDatabaseCO.child(idCO).removeValue();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        mDatabase.child(id).removeValue();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setIcon(R.drawable.ic_delete_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        delete();
                        dialog.dismiss();
                        finish();
                        Toast.makeText(getBaseContext(), "Successfully Deleted!", Toast.LENGTH_LONG).show();
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}


