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

import com.andexert.library.RippleView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.AcademicYear;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Add.AddEditCourseActivity;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
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

    private BoomMenuButton bmb;
    static RippleView rippleView;

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

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<CourseOffering, FacultyViewHolder>(CourseOffering.class, R.layout.course_row, FacultyViewHolder.class, mDatabase)
        {
            @Override
            protected void populateViewHolder(final FacultyViewHolder viewHolder,  CourseOffering model,  int position) {
                final String stringPosition = getRef(position).getKey();
                Log.i("huh", "zzzs" + stringPosition);
                Log.i("huh", "zzz" + model.getCourse_id());
                String c_id = model.getCourse_id();
                String section = model.getSection();
                String f_id = model.getFaculty_id();


                viewHolder.setName2(section);

                if(c_id != null &&
                        f_id != null) {

                    mDatabaseCourse.child(c_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String code = (String) dataSnapshot.child(Course.COL_CODE).getValue();
                            viewHolder.setName(code);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Log.i("huh", "fac " + f_id);

                    mDatabaseProf.child(f_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String pic = (String) dataSnapshot.child(Faculty.COL_PIC).getValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        Intent i = new Intent(currView.getContext(), ViewCourseOfferingActivity.class);
                        i.putExtra(CourseOffering.COL_CO_ID, stringPosition);
                        startActivity(i);
                    }

                });
            }
        };

        bmb =  (BoomMenuButton) currView.findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.TextInsideCircle);

        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_1);

        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_1);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder;
            switch(i){
                case 0:
                    builder = new TextInsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    // When the boom-button corresponding this builder is clicked.
                                    Intent intent = new Intent(getContext(), AddEditFacultyActivity.class);
                                    intent.putExtra("currProcess", 0 );
                                    startActivity(intent);
                                }
                            });
                    builder.normalImageRes(R.drawable.ic_action_faculty);
                    builder.normalText("Add Faculty");
                    bmb.addBuilder(builder);
                    break;
                case 1:
                    builder = new TextInsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    // When the boom-button corresponding this builder is clicked.
                                    Intent intent = new Intent(getContext(), AddEditCourseActivity.class);
                                    intent.putExtra("currProcess", 0 );
                                    startActivity(intent);
                                }
                            });
                    builder.normalImageRes(R.drawable.ic_action_course);
                    builder.normalText("Add Course");
                    bmb.addBuilder(builder);
                    break;
                case 2:
                    builder = new TextInsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    // When the boom-button corresponding this builder is clicked.
                                    Intent intent = new Intent(getContext(), AddEditCourseOffering.class);
                                    intent.putExtra("currProcess", 0 );
                                    startActivity(intent);
                                }
                            });
                    builder.normalImageRes(R.drawable.ic_action_courseoffering);
                    builder.normalText("Add CourseOffering");
                    bmb.addBuilder(builder);
                    break;
                case 3:
                    builder = new TextInsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    // When the boom-button corresponding this builder is clicked.
                                    Intent intent = new Intent(getContext(), AddEditBldgActivity.class);
                                    intent.putExtra("currProcess", 0 );
                                    startActivity(intent);
                                }
                            });
                    builder.normalImageRes(R.drawable.ic_action_building);
                    builder.normalText("Add Building");
                    bmb.addBuilder(builder);
                    break;
                case 4:
                    builder = new TextInsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    // When the boom-button corresponding this builder is clicked.
                                    Intent intent = new Intent(getContext(), AddEditRoomActivity.class);
                                    intent.putExtra("currProcess", 0 );
                                    startActivity(intent);
                                }
                            });
                    builder.normalImageRes(R.drawable.ic_action_room);
                    builder.normalText("Add Room");
                    bmb.addBuilder(builder);
                    break;
            }

        }

        facultyList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class FacultyViewHolder extends RecyclerView.ViewHolder{
        View mView;


        public FacultyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            rippleView = (RippleView) mView.findViewById(R.id.rippleView);
        }

        public void setName(String name){
            TextView fac_name = (TextView) mView.findViewById(R.id.coursecode);
            fac_name.setText(name);
        }

        public void setName2(String name){
            TextView fac_name = (TextView) mView.findViewById(R.id.coursename);
            fac_name.setText(name);
        }



    }
}
