package com.thea.admingreencheck3.View;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.AcademicYear;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.ViewIndiv.ViewCourseActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewCourseOfferingActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewFacultyActivity;

/**
 * Created by Thea on 28/03/2017.
 */

public class CourseOfferingActivity extends Fragment {
    private RecyclerView facultyList;
    private View currView;
    FirebaseRecyclerAdapter<CourseOffering, FacultyViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase, mDatabaseCourse, mDatabaseProf;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("CourseOffering");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currView =inflater.inflate(R.layout.faculty, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);
        mDatabaseCourse = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);
        mDatabaseProf = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);

        facultyList = (RecyclerView) currView.findViewById(R.id.faculty_list);
        facultyList.setHasFixedSize(true);
        facultyList.setLayoutManager(new LinearLayoutManager(currView.getContext()));



        return currView;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<CourseOffering, FacultyViewHolder>(CourseOffering.class, R.layout.faculty_row, FacultyViewHolder.class, mDatabase)
        {
            @Override
            protected void populateViewHolder(final FacultyViewHolder viewHolder,  CourseOffering model,  int position) {
                final String stringPosition = getRef(position).getKey();
                Log.i("huh", "zzzs" + stringPosition);
                Log.i("huh", "zzz" + model.getCourse_id());
                String c_id = model.getCourse_id();
                String section = model.getSection();

                viewHolder.setName(section);

                mDatabaseCourse.child(c_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String code = (String) dataSnapshot.child(Course.COL_CODE).getValue();

                        viewHolder.setTV2(code);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                String f_id = model.getFaculty_id();
                Log.i("huh", "fac " + f_id);

                mDatabaseProf.child(f_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String pic = (String) dataSnapshot.child(Faculty.COL_PIC).getValue();
                        viewHolder.setImage(currView.getContext(), pic);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(currView.getContext(), ViewCourseOfferingActivity.class);
                        i.putExtra(CourseOffering.COL_CO_ID, stringPosition);
                        startActivity(i);

                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                    }
                });


            }
        };

        facultyList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class FacultyViewHolder extends RecyclerView.ViewHolder{
        View mView;


        public FacultyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView fac_name = (TextView) mView.findViewById(R.id.facName);
            fac_name.setText(name);
        }

        public void setTV2(String name){
            TextView fac_name = (TextView) mView.findViewById(R.id.tv2);
            fac_name.setText(name);
        }

        public void setImage(Context ctx, String image){
            ImageView fac_image = (ImageView) mView.findViewById(R.id.facImage);
            Picasso.with(ctx).load(image).into(fac_image);
        }



    }
}
