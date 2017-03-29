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
            Log.i("image", image);
            ImageView fac_image = (ImageView) mView.findViewById(R.id.facImage);
            Picasso.with(ctx).load(image).into(fac_image);
        }


    }
}
