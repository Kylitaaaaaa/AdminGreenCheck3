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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.squareup.picasso.Picasso;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Add.AddEditCourseActivity;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Checker;
import com.thea.admingreencheck3.Course;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;
import com.thea.admingreencheck3.ViewIndiv.ViewBuildingActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewCheckerActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewCourseActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewFacultyActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewRoomActivity;

/**
 * Created by Thea on 28/03/2017.
 */

public class RoomActivity extends Fragment {
    private RecyclerView list;
    private View currView;
    FirebaseRecyclerAdapter<Room, RoomViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase;

    private BoomMenuButton bmb;
    static RippleView rippleView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Room");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currView =inflater.inflate(R.layout.activity_building, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);

        list = (RecyclerView) currView.findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(currView.getContext()));

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

        return currView;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Room, RoomViewHolder>(Room.class, R.layout.gen_row, RoomViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(RoomViewHolder viewHolder, Room model, int position) {
                final String stringPosition = getRef(position).getKey();

                viewHolder.setTitle(model.getName());

                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        Intent i = new Intent(currView.getContext(), ViewRoomActivity.class);
                        i.putExtra(Room.COL_ROOM_ID, stringPosition);
                        startActivity(i);
                    }

                });
            }
        };

        list.setAdapter(firebaseRecyclerAdapter);


    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public RoomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            rippleView = (RippleView) mView.findViewById(R.id.rippleView);
        }


        public void setTitle(String name){
            TextView title = (TextView) mView.findViewById(R.id.title);
            title.setText(name);
        }


    }
}

