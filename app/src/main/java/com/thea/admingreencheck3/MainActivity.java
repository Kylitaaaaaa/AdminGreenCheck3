package com.thea.admingreencheck3;

import android.content.Intent;
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

import com.thea.admingreencheck3.Add.AddEditAcademicYearActivity;
import com.thea.admingreencheck3.Add.AddEditBldgActivity;
import com.thea.admingreencheck3.Add.AddEditCourseActivity;
import com.thea.admingreencheck3.Add.AddEditCourseOffering;
import com.thea.admingreencheck3.Add.AddEditFacultyActivity;
import com.thea.admingreencheck3.Add.AddEditRoomActivity;
import com.thea.admingreencheck3.Add.AddEditTermActivity;
import com.thea.admingreencheck3.Add.AddFacultyActivity;
import com.thea.admingreencheck3.View.AcademicYearActivity;
import com.thea.admingreencheck3.View.BuildingActivity;
import com.thea.admingreencheck3.View.CourseActivity;
import com.thea.admingreencheck3.View.CourseOfferingActivity;
import com.thea.admingreencheck3.View.FacultyActivity;
import com.thea.admingreencheck3.View.RoomActivity;
import com.thea.admingreencheck3.View.SubstituteActivity;
import com.thea.admingreencheck3.View.TermActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int currActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        currActivity = R.id.nav_faculty;
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
                case R.id.nav_substitute:
                    fragment = new SubstituteActivity();
                    break;
                case R.id.nav_makeup_class:
                    break;
                case R.id.nav_unscheduled_class:
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
                case R.id.nav_academic_year:
                    i = new Intent(getBaseContext(), AddEditAcademicYearActivity.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;
                case R.id.nav_term:
                    i = new Intent(getBaseContext(), AddEditTermActivity.class);
                    i.putExtra("currProcess", 0 );
                    startActivity(i);
                    break;
                case R.id.nav_checker:
                    break;
                case R.id.nav_rotation:
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

     private void displaySelectedScreen(int id){
         Fragment fragment = null;
         switch (id){
             case R.id.nav_faculty:
                 currActivity = R.id.nav_faculty;
                 fragment = new FacultyActivity();
                 break;
             case R.id.nav_substitute:
                 currActivity = R.id.nav_substitute;
                 fragment = new SubstituteActivity();
                 break;
             case R.id.nav_makeup_class:
                 break;
             case R.id.nav_unscheduled_class:
                 break;
             case R.id.nav_course:
                 currActivity = R.id.nav_course;
                 fragment = new CourseActivity();
                 break;
             case R.id.nav_course_offering:
                 currActivity = R.id.nav_course_offering;
                 fragment = new CourseOfferingActivity();
                 break;
             case R.id.nav_building:
                 currActivity = R.id.nav_building;
                 fragment = new BuildingActivity();
                 break;
             case R.id.nav_room:
                 currActivity = R.id.nav_room;
                 fragment = new RoomActivity();

//                 Intent i = new Intent(getBaseContext(), AddEditRoomActivity.class);
//                 i.putExtra("currProcess", 0 );
//                 startActivity(i);
                 break;
             case R.id.nav_academic_year:
                 currActivity = R.id.nav_academic_year;
                 fragment = new AcademicYearActivity();
                 break;
             case R.id.nav_term:
                 currActivity = R.id.nav_term;
                 fragment = new TermActivity();
                 break;
             case R.id.nav_checker:
                 break;
             case R.id.nav_rotation:
                 break;

         }

         /*
         if (id == R.id.nav_faculty) {

         } else if (id == R.id.nav_substitute) {

         } else if (id == R.id.nav_makeup_class) {

         } else if (id == R.id.nav_unscheduled_class) {

         } else if (id == R.id.nav_course) {

         } else if (id == R.id.nav_course_offering) {

         } else if (id == R.id.nav_building) {

         } else if (id == R.id.nav_room) {

         } else if (id == R.id.nav_academic_year) {

         } else if (id == R.id.nav_term) {

         } else if (id == R.id.nav_checker) {

         } else if (id == R.id.nav_rotation) {

         } */

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



}
