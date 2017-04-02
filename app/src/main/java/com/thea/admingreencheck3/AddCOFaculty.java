package com.thea.admingreencheck3;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.View.BuildingActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewBuildingActivity;

public class AddCOFaculty extends AppCompatActivity {

    private RecyclerView list;
    FirebaseRecyclerAdapter<Faculty, AddCOFaculty.FacultyViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase;
    private int currProcess = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);


        list = (RecyclerView) findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Faculty, AddCOFaculty.FacultyViewHolder>(Faculty.class, R.layout.gen_row, AddCOFaculty.FacultyViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(AddCOFaculty.FacultyViewHolder viewHolder, Faculty model, int position) {
                final String stringPosition = getRef(position).getKey();

                viewHolder.setTitle(model.getFirst_name() + " " + model.getLast_name());

                viewHolder.vView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(getBaseContext(), AddEditCourseOffering.class);
                        i.putExtra(Faculty.COL_ID, stringPosition);
                        setResult(RESULT_OK, i);
                        finish();
                        //startActivity(i);

                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                    }
                });
            }
        };

        list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FacultyViewHolder extends RecyclerView.ViewHolder{
        View vView;

        public FacultyViewHolder(View itemView) {
            super(itemView);
            vView = itemView;
        }

        public void setTitle(String name){
            TextView title = (TextView) vView.findViewById(R.id.title);
            title.setText(name);
        }


    }
}
