package com.thea.admingreencheck3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.thea.admingreencheck3.Add.AddEditAcademicYearActivity;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Add.AddEditChecker;
import com.thea.admingreencheck3.Add.AddEditCourseActivity;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Add.AddEditTermActivity;
import com.thea.admingreencheck3.Add.AddFacultyActivity;
import com.thea.admingreencheck3.View.AcademicYearActivity;
import com.thea.admingreencheck3.View.AttendanceActivity;
import com.thea.admingreencheck3.View.BuildingActivity;
import com.thea.admingreencheck3.View.CheckerActivity;
import com.thea.admingreencheck3.View.CourseActivity;
import com.thea.admingreencheck3.View.CourseOfferingActivity;
import com.thea.admingreencheck3.View.FacultyActivity;
import com.thea.admingreencheck3.View.RoomActivity;
import com.thea.admingreencheck3.View.SubstituteActivity;
import com.thea.admingreencheck3.View.TermActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int currActivity;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Log.i("huh", "bye bye");
                    startActivity(new Intent(getBaseContext(), AdminSignInActivity.class));
                }
                else{
                    Log.i("huh", "not bye bye" + firebaseAuth.getCurrentUser().toString());
                }
            }
        };


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        
        Fragment fragment = new AttendanceActivity();


        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();

        }


        scheduleAlarm();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.i("huh", "nzz");
        Intent i;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(item.getItemId() == R.id.action_add){
            switch (currActivity){
                //add = 0
                //edit = 1
                case R.id.nav_faculty:
                    i = new Intent(getBaseContext(), AddEditFacultyActivity.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;
                case R.id.nav_course:
                    i = new Intent(getBaseContext(), AddEditCourseActivity.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;
                case R.id.nav_course_offering:
                    i = new Intent(getBaseContext(), AddEditCourseOffering.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;
                case R.id.nav_building:
                    i = new Intent(getBaseContext(), AddEditBldgActivity.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;
                case R.id.nav_room:
                    i = new Intent(getBaseContext(), AddEditRoomActivity.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;
                case R.id.nav_checker:
                    i = new Intent(getBaseContext(), AddEditChecker.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;

            }

            Log.i("huh", "huh");

            if(fragment != null){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.commit();

            }
            //startActivity(new Intent(getBaseContext(), AddActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
    */

     private void displaySelectedScreen(int id){
         Fragment fragment = null;
         switch (id){
             case R.id.nav_attendance:
                 currActivity = R.id.nav_attendance;
                 navigationView.getMenu().getItem(0).setChecked(true);
                 fragment = new AttendanceActivity();
                 break;
             case R.id.nav_faculty:
                 currActivity = R.id.nav_faculty;
                 navigationView.getMenu().getItem(1).setChecked(true);
                 fragment = new FacultyActivity();
                 break;
             case R.id.nav_checker:
                 currActivity = R.id.nav_checker;
                 navigationView.getMenu().getItem(2).setChecked(true);
                 fragment = new CheckerActivity();
                 break;
             case R.id.nav_course:
                 currActivity = R.id.nav_course;
                 navigationView.getMenu().getItem(3).setChecked(true);
                 fragment = new CourseActivity();
                 break;
             case R.id.nav_course_offering:
                 currActivity = R.id.nav_course_offering;
                 navigationView.getMenu().getItem(4).setChecked(true);
                 fragment = new CourseOfferingActivity();
                 break;
             case R.id.nav_building:
                 currActivity = R.id.nav_building;
                 navigationView.getMenu().getItem(5).setChecked(true);
                 fragment = new BuildingActivity();
                 break;
             case R.id.nav_room:
                 currActivity = R.id.nav_room;
                 navigationView.getMenu().getItem(6).setChecked(true);
                 fragment = new RoomActivity();
                 break;

             case R.id.nav_logout:
                 currActivity = R.id.nav_logout;
                 navigationView.getMenu().getItem(7).setChecked(true);
                 mAuth.signOut();
                 Log.i("huh", "Logged Out");
                 break;

             default:
                 currActivity = R.id.nav_faculty;
                 navigationView.getMenu().getItem(0).setChecked(true);
                 fragment = new FacultyActivity();
                 break;

         }

         if(fragment != null){
             FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
             ft.replace(R.id.content_main, fragment);
             ft.commit();

         }

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);
     }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);

        return true;
    }

    public void scheduleAlarm()
    {
        //Long time = new GregorianCalendar().getTimeInMillis()+24*60*60*1000;
        Long settime = new GregorianCalendar().getTimeInMillis()+1000;
        /*

        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                23,
                27,
                0);
        calendar.set(Calendar.MILLISECOND, 0);
        Long settime = calendar.getTimeInMillis();
        */


        Log.i("huh", "Starting");
        Intent intentAlarm = new Intent(this, Receiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP, settime, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for Tommrrow", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
