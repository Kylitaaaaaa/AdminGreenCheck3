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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Add.AddEditChecker;
import com.thea.admingreencheck3.Add.AddEditCourseActivity;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.ViewIndiv.ViewFacultyActivity;

/**
 * Created by Thea on 28/03/2017.
 */

public class FacultyActivity extends Fragment {
    private RecyclerView facultyList;
    private View currView;
    FirebaseRecyclerAdapter<Faculty, FacultyViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase;
    private BoomMenuButton bmb;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Faculty");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currView =inflater.inflate(R.layout.faculty, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Faculty");

        facultyList = (RecyclerView) currView.findViewById(R.id.faculty_list);
        facultyList.setHasFixedSize(true);
        facultyList.setLayoutManager(new LinearLayoutManager(currView.getContext()));

        bmb =  (BoomMenuButton) currView.findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.TextInsideCircle);

        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_1);

        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_1);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder;
            switch(i){
                case 0:
                    builder = new TextInsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    // When the boom-button corresponding this builder is clicked.
//                                    Intent intent = new Intent(getContext(), AddEditFacultyActivity.class);
//                                    intent.putExtra("currProcess", 0 );
//                                    startActivity(intent);
                                }
                            });
                    builder.normalImageRes(R.drawable.ic_action_attendance);
                    builder.normalText("Add Attendance");
                    bmb.addBuilder(builder);
                    break;
                case 1:
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
                case 2:
                    builder = new TextInsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    // When the boom-button corresponding this builder is clicked.
                                    Intent intent = new Intent(getContext(), AddEditChecker.class);
                                    intent.putExtra("currProcess", 0 );
                                    startActivity(intent);
                                }
                            });
                    builder.normalImageRes(R.drawable.ic_action_checker);
                    builder.normalText("Add Checker");
                    bmb.addBuilder(builder);
                    break;
                case 3:
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
                case 4:
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
                case 5:
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
                case 6:
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





        return currView;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Faculty, FacultyViewHolder>(Faculty.class, R.layout.faculty_row, FacultyViewHolder.class, mDatabase)
        {
            @Override
            protected void populateViewHolder(FacultyViewHolder viewHolder, final Faculty model, final int position) {
                final String fac_position = getRef(position).getKey();

                viewHolder.setName(model.getFirst_name() + model.getLast_name());
                viewHolder.setImage(currView.getContext(), model.getPic());
                Log.i("huh", "huahdfuhasdf");
                Log.i("huh", "model: " + model.getFirst_name());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(currView.getContext(), ViewFacultyActivity.class);
                        i.putExtra(Faculty.COL_ID, fac_position );
                        Log.i("idmodel", "model:  " + model.getId());
                        startActivity(i);

                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                    }
                });



            }
        };
        Log.i("huh", "huhhh");

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

        public void setImage(Context ctx, String image){
            ImageView fac_image = (ImageView) mView.findViewById(R.id.facImage);
            Picasso.with(ctx).load(image).into(fac_image);
        }


    }
}
