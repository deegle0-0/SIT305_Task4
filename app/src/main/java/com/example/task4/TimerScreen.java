package com.example.task4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerScreen extends AppCompatActivity {
    //Initializing all the useable buttons and texts
    TextView countDownText,restView;
    Button startPause;
    Button reset;
    CountDownTimer mCountDownTimer;
    Boolean timerRunning;
    ProgressBar progressBar;
    int progress = 0 ;

    long workoutTimeinMills,restTimeinMills,tempWorkoutTime,tempRestTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);

        //Setting up using find view
        countDownText = findViewById(R.id.timeText);
        startPause = findViewById(R.id.startStopButton);
        reset = findViewById(R.id.resetbutton);
        progressBar = findViewById(R.id.progressBar);
        restView = findViewById(R.id.textView3);

        timerRunning = false; // setup to false to make sure timer is not running

        Intent valueFromIntent = getIntent(); //getting values from main activity

        workoutTimeinMills = valueFromIntent.getLongExtra("workoutTime",0);
        tempWorkoutTime = workoutTimeinMills; // saving to have value for the progress bar and reset
        restTimeinMills = valueFromIntent.getLongExtra("restTime",0);
        tempRestTime = restTimeinMills;

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(workoutTimeinMills!=0) // checking if they are not zero
                {
                    setTime(workoutTimeinMills); // setting time in Text view to the time given by user
                    //this is done to save the progress of time
                }

                if(timerRunning){
                    pauseTimer(); // if true it will pause if false it will start the timer
                }else {
                    startTimer();
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() { //reset button
            @Override
            public void onClick(View v) {
                resetTimer();
            } // this makes the reset button a reusable function
        });
        updateCountDownText(workoutTimeinMills); // updates the timer
    }

    private void setTime(long milliseconds)
    {
        workoutTimeinMills = milliseconds;
//        resetTimer();
    }

    private void resetTimer() {
        workoutTimeinMills =tempWorkoutTime; // resets to time given by user
        restTimeinMills = tempRestTime;
        progressBar.setProgress(0); // progress bar to zero to make sure it goes to zero when user clicks reset
        restView.setText("");
        updateCountDownText(workoutTimeinMills);
    }
    private void pauseTimer() {
        mCountDownTimer.cancel(); // cancel the timer when paused
        timerRunning=false;
        startPause.setText("Start");
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(workoutTimeinMills,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                workoutTimeinMills = millisUntilFinished; // to track progress
                progress++;
                int setup = (int)(tempWorkoutTime/1000); // test variable for progress bar
                Log.e("progress", String.valueOf(progress));
                progressBar.setProgress((int)(progress*100/setup)); //progress increases as time decreases

                updateCountDownText(workoutTimeinMills); //updates the text
            }

            @Override
            public void onFinish() {
                progress=0;
                restView.setText("REST"); // when workout if finished it goes to the rest timer
                startRestTimer();
            }

        }.start();
        timerRunning = true;
        startPause.setText("Pause");

    }

    private void startRestTimer() {
        mCountDownTimer = new CountDownTimer(restTimeinMills,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Same as workout timer
                restTimeinMills = millisUntilFinished;
                progress++;

                progressBar.setProgress((int)(progress*100/(tempRestTime/1000)));

                updateCountDownText(restTimeinMills);
            }

            @Override
            public void onFinish() {
                finish();
                startPause.setText("Start");
                timerRunning=false;
                restView.setText("");
            }
        }.start();
        timerRunning = true;
        startPause.setText("Pause");
    }

    private void updateCountDownText(long timeGiven) {

        int minutes = (int) (timeGiven/1000) / 60; //takes the mins
        int seconds = (int) (timeGiven/1000) % 60; // takes seconds

        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        //formats mins and seconds to show
        countDownText.setText(timeLeft);
    }


}