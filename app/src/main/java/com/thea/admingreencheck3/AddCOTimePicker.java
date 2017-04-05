package com.thea.admingreencheck3;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.thea.admingreencheck3.Add.AddEditCourseOffering;

public class AddCOTimePicker extends AppCompatActivity {
    TimePicker timePicker;
    Button btn_timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cotime_picker);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        btn_timepicker = (Button) findViewById(R.id.btn_timepicker);

        btn_timepicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddEditCourseOffering.class);
                int currentApiVersion = Build.VERSION.SDK_INT;
                int hour;
                int min;

                if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1){
                    hour = timePicker.getHour();
                    min = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    min = timePicker.getCurrentMinute();
                }

                i.putExtra("time", hour + " : " + min);
                i.putExtra("hour", hour + "");
                i.putExtra("min", min + "");

                setResult(RESULT_OK, i);
                finish();

            }
        });
    }
}
