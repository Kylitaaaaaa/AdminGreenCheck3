package com.thea.admingreencheck3.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.MainActivity;
import com.thea.admingreencheck3.R;

import static android.app.Activity.RESULT_OK;

public class AddFacultyActivity extends Fragment {
    ImageButton addFacImage;
    EditText et_first_name, et_middle_name, et_last_name, et_college, et_email, et_mobile_number, et_department;
    Button btn_addFac;
    Uri imageUri = null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    private View thisFragmentView;

    private static final int GALLERY_REQUEST = 1;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Faculty");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisFragmentView = inflater.inflate(R.layout.add_faculty, container, false);
        addFacImage = (ImageButton) thisFragmentView.findViewById(R.id.addFacImage);
        et_first_name = (EditText) thisFragmentView.findViewById(R.id.et_first_name);
        et_middle_name = (EditText) thisFragmentView.findViewById(R.id.et_middle_name);
        et_last_name = (EditText) thisFragmentView.findViewById(R.id.et_last_name);
        et_college = (EditText) thisFragmentView.findViewById(R.id.et_college);
        et_email = (EditText) thisFragmentView.findViewById(R.id.et_email);
        et_mobile_number = (EditText) thisFragmentView.findViewById(R.id.et_mobile_number);
        et_department = (EditText) thisFragmentView.findViewById(R.id.et_department);
        btn_addFac = (Button) thisFragmentView.findViewById(R.id.btn_addFac);

        mProgress = new ProgressDialog(thisFragmentView.getContext());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Faculty");


        addFacImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                Log.i("huh", "huh 1");
                startActivityForResult(i, GALLERY_REQUEST);
            }
        });

        btn_addFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdding();
            }
        });


        return thisFragmentView;
    }

    public void startAdding(){
        mProgress.setMessage("Adding Faculty to Database...");
        mProgress.show();

        final String fname = et_first_name.getText().toString();
        final String mname = et_middle_name.getText().toString();
        final String lname = et_last_name.getText().toString();
        final String college = et_college.getText().toString();
        final String email = et_email.getText().toString();
        final String mobnum = et_mobile_number.getText().toString();
        final String dept = et_department.getText().toString();

        if(!TextUtils.isEmpty(fname) &&
                !TextUtils.isEmpty(mname) &&
                !TextUtils.isEmpty(lname) &&
                !TextUtils.isEmpty(college) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(mobnum) &&
                !TextUtils.isEmpty(dept) &&
                imageUri != null){

            StorageReference filepath = mStorage.child("Fac_Images").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newFaculty = mDatabase.push();

                    newFaculty.child(Faculty.COL_FNAME).setValue(fname);
                    newFaculty.child(Faculty.COL_MNAME).setValue(mname);
                    newFaculty.child(Faculty.COL_LNAME).setValue(lname);
                    newFaculty.child(Faculty.COL_COLLEGE).setValue(college);
                    newFaculty.child(Faculty.COL_EMAIL).setValue(email);


                    newFaculty.child(Faculty.COL_PIC).setValue(downloadUrl.toString());


                    newFaculty.child(Faculty.COL_MOBNUM).setValue(mobnum);
                    newFaculty.child(Faculty.COL_DEPT).setValue(dept);

                    mProgress.dismiss();

                    startActivity(new Intent(thisFragmentView.getContext(), MainActivity.class));
                }
            });

        }

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
