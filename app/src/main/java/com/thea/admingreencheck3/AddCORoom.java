package com.thea.admingreencheck3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;

public class AddCORoom extends AppCompatActivity {

    private RecyclerView list;
    FirebaseRecyclerAdapter<Room, AddCORoom.CourseViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase;
    private int currProcess = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Room.TABLE_NAME);


        list = (RecyclerView) findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Room, AddCORoom.CourseViewHolder>(Room.class, R.layout.gen_row, AddCORoom.CourseViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(AddCORoom.CourseViewHolder viewHolder, Room model, int position) {
                final String stringPosition = getRef(position).getKey();

                viewHolder.setTitle(model.getName());

                viewHolder.vView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(getBaseContext(), AddEditCourseOffering.class);
                        i.putExtra(Room.COL_ROOM_ID, stringPosition);
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

    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        View vView;

        public CourseViewHolder(View itemView) {
            super(itemView);
            vView = itemView;
        }

        public void setTitle(String name){
            TextView title = (TextView) vView.findViewById(R.id.title);
            title.setText(name);
        }


    }
}
