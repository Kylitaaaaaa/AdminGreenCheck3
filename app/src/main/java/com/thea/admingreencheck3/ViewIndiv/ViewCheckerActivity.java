package com.thea.admingreencheck3.ViewIndiv;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Add.AddEditChecker;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Checker;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;

public class ViewCheckerActivity extends AppCompatActivity {
    TextView et_Email;
    Button btn_add, btn_edit, btn_A, btn_B, btn_C, btn_D, btn_E, btn_F, btn_G;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private int currProcess;
    private String id;
    private Boolean a = false,
            b = false,
            c = false,
            d = false,
            e = false,
            f = false,
            g = false;
    private String unavailableRotId = "";
    private String rotid;
    String r_id = "";
    private ImageView imgFac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_checker);

        getSupportActionBar().setTitle("Checker");

        et_Email = (TextView) findViewById(R.id.et_email);
        btn_add = (Button) findViewById(R.id.btn_Add);
        btn_edit = (Button) findViewById(R.id.btn_Edit);

        btn_A = (Button) findViewById(R.id.btn_A);
        btn_B = (Button) findViewById(R.id.btn_B);
        btn_C = (Button) findViewById(R.id.btn_C);
        btn_D = (Button) findViewById(R.id.btn_D);
        btn_E = (Button) findViewById(R.id.btn_E);
        btn_F = (Button) findViewById(R.id.btn_F);
        btn_G = (Button) findViewById(R.id.btn_G);
        imgFac = (ImageView) findViewById(R.id.imgFac);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Checker.TABLE_NAME);


        id = getIntent().getExtras().getString(Checker.COL_C_ID);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child(Checker.COL_NAME).getValue();

                et_Email.setText(name);

                r_id= (String) dataSnapshot.child(Checker.COL_ROT_ID).getValue();
                String pic = (String) dataSnapshot.child(Checker.COL_PIC).getValue();
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



                Picasso.with(getBaseContext()).load(pic).into(imgFac);

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
            Intent i = new Intent(getBaseContext(), AddEditChecker.class);
            //add = 0
            //edit = 1
            i.putExtra("currProcess", 1 );
            i.putExtra(Checker.COL_C_ID, id);
            startActivity(i);

        }
        else if (itemid == R.id.action_delete) {
            AlertDialog diaBox = AskOption();
            diaBox.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(){
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


