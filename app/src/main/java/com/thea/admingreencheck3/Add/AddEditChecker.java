package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.thea.admingreencheck3.Checker;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;

public class AddEditChecker extends AppCompatActivity {

    EditText et_Email;
    ImageButton addFacImage;
    Uri imageUri = null;
    Button btn_A, btn_B, btn_C, btn_D, btn_E, btn_F, btn_G;
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


    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_checker);

        et_Email = (EditText) findViewById(R.id.et_email);
        addFacImage = (ImageButton) findViewById(R.id.addFacImage);

        btn_A = (Button) findViewById(R.id.btn_A);
        btn_B = (Button) findViewById(R.id.btn_B);
        btn_C = (Button) findViewById(R.id.btn_C);
        btn_D = (Button) findViewById(R.id.btn_D);
        btn_E = (Button) findViewById(R.id.btn_E);
        btn_F = (Button) findViewById(R.id.btn_F);
        btn_G = (Button) findViewById(R.id.btn_G);

        //mProgress = new ProgressDialog(this.getApplicationContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Checker.TABLE_NAME);

        //getUnavailableRotId();


        currProcess = getIntent().getIntExtra("currProcess", -1);
        if(currProcess == 1 ) {

            id = getIntent().getExtras().getString(Checker.COL_C_ID);

            mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child(Checker.COL_NAME).getValue();
                    String pic = (String) dataSnapshot.child(Checker.COL_PIC).getValue();

                    et_Email.setText(name);

                    Picasso.with(getBaseContext()).load(pic).into(addFacImage);

                    String r_id= (String) dataSnapshot.child(Checker.COL_ROT_ID).getValue();
                    if(r_id != null) {

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

        addFacImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                Log.i("huh", "huh 1");
                startActivityForResult(i, GALLERY_REQUEST);
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

    public void getUnavailableRotId(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot trysnap = dataSnapshot;
                Iterable<DataSnapshot> tryChildren = trysnap.getChildren();
                Log.i("huh", "count: " + trysnap.getChildrenCount());

                for (DataSnapshot ty : tryChildren) {
                    Checker huh = ty.getValue(Checker.class);

                    Log.i("huh", "checker: " + huh.getName());

                    String tempS = huh.getRotationId();
                    Log.i("huh", "tempS2 " + tempS);


                    for (char ch : tempS.toCharArray()){
                        if(unavailableRotId.indexOf(ch + "") <0)
                            unavailableRotId = unavailableRotId.concat(ch + "");
                    }





                }

                Log.i("huh", "unavailableRotId " + unavailableRotId);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public void startEditing(){

        final ProgressDialog progress = new ProgressDialog(this);

        final String name = et_Email.getText().toString();
        rotid = "";
        if (a)
            rotid = rotid.concat("A");

        if (b)
            rotid = rotid.concat("B");

        if (c)
            rotid = rotid.concat("C");

        if (d)
            rotid = rotid.concat("D");

        if (e)
            rotid = rotid.concat("E");

        if (f)
            rotid = rotid.concat("F");

        if (g)
            rotid = rotid.concat("G");

            if(imageUri != null) {
                if (!TextUtils.isEmpty(name) &&
                        rotid != "") {
                    progress.setMessage("Saving Changes");
                    progress.show();

                    StorageReference filepath = mStorage.child("profile_images").child(imageUri.getLastPathSegment());

                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            DatabaseReference newThing = mDatabase.child(id);
                            Log.i("change", "huh");

                            newThing.child(Checker.COL_PIC).setValue(downloadUrl.toString());

                            newThing.child(Checker.COL_NAME).setValue(name);
                            newThing.child(Checker.COL_ROT_ID).setValue(rotid);

                        }
                    });
                    progress.dismiss();
                    Log.i("huh", "HERE");
                    //startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(getBaseContext(), "Please complete the fields", Toast.LENGTH_LONG).show();
            }
            else{
                if (!TextUtils.isEmpty(name) &&
                        rotid != "") {
                    progress.setMessage("Saving Changes");
                    progress.show();

                    DatabaseReference newThing = mDatabase.child(id);
                    newThing.child(Checker.COL_NAME).setValue(name);
                    newThing.child(Checker.COL_ROT_ID).setValue(rotid);

                    progress.dismiss();
                    finish();
                }
                else
                    Toast.makeText(getBaseContext(), "Please complete the fields", Toast.LENGTH_LONG).show();
            }



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
            startEditing();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("request", requestCode + "");

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            addFacImage.setImageURI(imageUri);
            Log.i("huh", "huh 213123");

        }
    }

}

