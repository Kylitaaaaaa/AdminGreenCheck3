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
        import com.thea.admingreencheck3.Course;
        import com.thea.admingreencheck3.Faculty;
        import com.thea.admingreencheck3.R;
        import com.thea.admingreencheck3.ViewIndiv.ViewCourseActivity;
        import com.thea.admingreencheck3.ViewIndiv.ViewFacultyActivity;

/**
 * Created by Thea on 28/03/2017.
 */

public class CourseActivity extends Fragment {
    private RecyclerView courseList;
    private View currView;
    FirebaseRecyclerAdapter<Course, CourseViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Course");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currView =inflater.inflate(R.layout.activity_course, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Course.TABLE_NAME);

        courseList = (RecyclerView) currView.findViewById(R.id.course_list);
        courseList.setHasFixedSize(true);
        courseList.setLayoutManager(new LinearLayoutManager(currView.getContext()));



        return currView;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Course, CourseViewHolder>(Course.class, R.layout.course_row, CourseViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(CourseViewHolder viewHolder, Course model, int position) {
                final String stringPosition = getRef(position).getKey();

                viewHolder.setCode(model.getCode());
                viewHolder.setName(model.getName());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(currView.getContext(), ViewCourseActivity.class);
                        i.putExtra(Faculty.COL_ID, stringPosition);
                        startActivity(i);

                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                    }
                });
            }
        };

        courseList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public CourseViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setCode(String code){
            TextView coursecode = (TextView) mView.findViewById(R.id.coursecode);
            coursecode.setText(code);
        }

        public void setName(String name){
            TextView coursename = (TextView) mView.findViewById(R.id.coursename);
            coursename.setText(name);
        }


    }
}
