package com.thea.admingreencheck3.ViewIndiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thea.admingreencheck3.AcademicYear;
import com.thea.admingreencheck3.Add.AddEditAcademicYearActivity;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Attendance;
import com.thea.admingreencheck3.Building;
import com.thea.admingreencheck3.Faculty;
import com.thea.admingreencheck3.R;
import com.thea.admingreencheck3.Room;

import org.w3c.dom.Text;

public class ViewReportActivity extends AppCompatActivity {
    String id;
    private DatabaseReference mDatabase, mDatabaseF;

    private TextView tv_a, tv_ed, tv_l, tv_p, tv_s, tv_sw, tv_us, tv_vr, tv_ce;
    int a = 0,
            ed = 0,
            l = 0,
            p = 0,
            s = 0,
            sw = 0,
            us = 0,
            vr = 0,
            ce = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        getSupportActionBar().setTitle("Report");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Attendance.TABLE_NAME_ADMIN);
        mDatabaseF = FirebaseDatabase.getInstance().getReference().child(Faculty.TABLE_NAME);

        id = getIntent().getExtras().getString(Faculty.COL_ID);

        tv_a = (TextView) findViewById(R.id.tv_a);
        tv_ed = (TextView) findViewById(R.id.tv_ed);
        tv_l = (TextView) findViewById(R.id.tv_l);
        tv_p = (TextView) findViewById(R.id.tv_p);
        tv_s = (TextView) findViewById(R.id.tv_s);
        tv_sw = (TextView) findViewById(R.id.tv_sw);
        tv_us = (TextView) findViewById(R.id.tv_us);
        tv_vr = (TextView) findViewById(R.id.tv_vr);
        tv_ce = (TextView) findViewById(R.id.tv_ce);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot trysnap = dataSnapshot;
                Iterable<DataSnapshot> tryChildren = trysnap.getChildren();

                Log.i("huh", "Attendancecount " + dataSnapshot.getChildrenCount());

                for (DataSnapshot ty : tryChildren) {

                    Attendance huh = ty.getValue(Attendance.class);
                    String tempID = huh.getF_id();
                    String tempStatus = huh.getStatus();

                    Log.i("huh", "tempID " + tempID + " " + tempStatus);
                    if(tempID != null &&
                            tempStatus != null) {


                        if (tempID.equals(id) &&
                        tempStatus.equals("SUBMITTED")){
                            Log.i("huh", "Attendance " + huh.getName());
                            if (huh.getCode().equals("Absent"))
                                a++;
                            else if (huh.getCode().equals("Early Dismissal"))
                                ed++;
                            else if (huh.getCode().equals("Late"))
                                l++;
                            else if (huh.getCode().equals("Present"))
                                p++;
                            else if (huh.getCode().equals("Substitute"))
                                s++;
                            else if (huh.getCode().equals("Seatwork"))
                                sw++;
                            else if (huh.getCode().equals("Unscheduled"))
                                us++;
                            else if (huh.getCode().equals("Vacant Room"))
                                vr++;
                            else if (huh.getCode().equals("CHECKERERROR"))
                                ce++;
                            Log.i("huh", "ce " + ce);
                        }
                    }



                }

                tv_a.setText(a + "");
                tv_ed.setText(ed + "");
                tv_l.setText(l + "");
                tv_p.setText(p + "");
                tv_s.setText(s + "");
                tv_sw.setText(sw + "");
                tv_us.setText(us + "");
                tv_vr.setText(vr + "");
                tv_ce.setText(ce + "");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });




    }
}


