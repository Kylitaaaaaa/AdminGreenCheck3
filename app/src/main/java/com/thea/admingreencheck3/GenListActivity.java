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

import com.andexert.library.RippleView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.View.BuildingActivity;
import com.thea.admingreencheck3.ViewIndiv.ViewBuildingActivity;

public class GenListActivity extends AppCompatActivity {

    private RecyclerView list;
    FirebaseRecyclerAdapter<Building, GenListActivity.BuildingViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mDatabase;
    private int currProcess = 0;
    static RippleView rippleView;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("fuck", "fuck");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_no_boom);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Building.TABLE_NAME);

        list = (RecyclerView) findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        emptyView = (TextView) findViewById(R.id.empty_view);
        getSupportActionBar().setTitle("Choose Building");
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {

                    list.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("No buildings yet");
                }
                else{
                    list.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Building, GenListActivity.BuildingViewHolder>(Building.class, R.layout.gen_row, GenListActivity.BuildingViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(GenListActivity.BuildingViewHolder viewHolder, Building model, int position) {
                final String stringPosition = getRef(position).getKey();

                viewHolder.setTitle(model.getName());

                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        Intent i = new Intent(getBaseContext(), AddEditRoomActivity.class);
                        i.putExtra(Building.COL_ID, stringPosition);
                        setResult(RESULT_OK, i);
                        finish();
                    }

                });
            }
        };

        list.setAdapter(firebaseRecyclerAdapter);


    }

    public static class BuildingViewHolder extends RecyclerView.ViewHolder{
        View vView;

        public BuildingViewHolder(View itemView) {
            super(itemView);
            vView = itemView;
            rippleView = (RippleView) vView.findViewById(R.id.rippleView);
        }

        public void setTitle(String name){
            TextView title = (TextView) vView.findViewById(R.id.title);
            title.setText(name);
        }


    }
}
