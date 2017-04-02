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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.AcademicYear;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.CourseOffering;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.ViewIndiv.ViewAcademicYearActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewBuildingActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewCourseActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewCourseOfferingActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewFacultyActivity;

/**
 * Created by Thea on 28/03/2017.
 */

public class CourseOfferingActivity extends Fragment {
    private RecyclerView list;
    private View currView;
    FirebaseRecyclerAdapter<CourseOffering, CourseOfferingViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase;
    private int currProcess = 0;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Course Offering");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currView =inflater.inflate(R.layout.activity_gen_list, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(CourseOffering.TABLE_NAME);

        list = (RecyclerView) currView.findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(currView.getContext()));

        return currView;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<CourseOffering, CourseOfferingViewHolder>(CourseOffering.class, R.layout.gen_row, CourseOfferingViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(CourseOfferingViewHolder viewHolder, CourseOffering model, int position) {
                final String stringPosition = getRef(position).getKey();

                viewHolder.setTitle(model.getCourse_code() + " : " + model.getSection());


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(currView.getContext(), ViewCourseOfferingActivity.class);
                        i.putExtra(CourseOffering.COL_COURSEOFFERING_ID, stringPosition);
                        startActivity(i);

                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                    }
                });
            }
        };

        list.setAdapter(firebaseRecyclerAdapter);


    }

    public static class CourseOfferingViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public CourseOfferingViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String name){
            TextView title = (TextView) mView.findViewById(R.id.title);
            title.setText(name);
        }


    }
}

