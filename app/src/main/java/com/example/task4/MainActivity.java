package com.example.task4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    EditText workoutTime,restTime;
    long workoutTimeInMillis,restTimeInMills;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workoutTime = findViewById(R.id.workoutTime);
        restTime = findViewById(R.id.restTime);

        startButton = findViewById(R.id.StartButton);

        Intent newIntent = new Intent(MainActivity.this,TimerScreen.class);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workoutTime.length()!=0)
                {
                    workoutTimeInMillis= Long.parseLong(workoutTime.getText().toString())*60000; // convert to milliseconds
                    restTimeInMills = Long.parseLong(restTime.getText().toString())*1000; // convert to seconds

                    if(workoutTimeInMillis == 0 || restTimeInMills ==0 )
                    {
                        Toast.makeText(MainActivity.this, "Enter proper input", Toast.LENGTH_SHORT).show();
                        //checking if they are not given will put a toast message

                    }

                    newIntent.putExtra("workoutTime", Long.parseLong(workoutTime.getText().toString())*60000);
                    newIntent.putExtra("restTime",Long.parseLong(restTime.getText().toString())*1000);

                    // passing values given to the countdown

                    startActivity(newIntent);


                }


            }
        });

    }
}