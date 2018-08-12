package rechnrer.cookies.com.rezeptapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class Timer extends AppCompatActivity {
private Spinner spinnerHours, spinnerMin, spinnerSec;
private Button startButton, resetButton, setTimeButton;
private TextView timerShow;
private CountDownTimer cdTimer;
private Boolean running;
private long timeLeft;
int totalTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        running = false;
        setup();
        updateCDView();

    }

    private void setup(){
        spinnerHours = (Spinner) findViewById(R.id.spinnerHour);
        spinnerMin = (Spinner) findViewById(R.id.spinnerMinutes);
        spinnerSec = (Spinner) findViewById(R.id.spinnerSeconds);
        startButton = (Button) findViewById(R.id.startButton);
        resetButton = (Button) findViewById(R.id.deleteButton);
        timerShow = (TextView) findViewById(R.id.timerShow);
        resetButton.setEnabled(false);
        setTimeButton = (Button) findViewById(R.id.setTime);

        setupSpinner(spinnerHours,R.array.hours);
        setupSpinner(spinnerMin,R.array.minutes);
        setupSpinner(spinnerSec,R.array.seconds);
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCountdownTime();
                timeLeft = totalTime;
                updateCDView();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running){
                    getCountdownTime();
                    running = true;
                    startButton.setText("Pause");
                    countdownStart();
                    resetButton.setEnabled(true);
                    setTimeButton.setEnabled(false);

                }
                else{
                    pauseTimer();
                    startButton.setText("Weiter");
                    running = false;

                }

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                resetButton.setEnabled(false);
                setTimeButton.setEnabled(true);
                startButton.setText("Start");
                cdTimer.cancel();

            }
        });

    }

    private void resetTimer() {
        timeLeft = totalTime;
        updateCDView();
    }

    private void pauseTimer() {
        cdTimer.cancel();
    }

    private void countdownStart() {
        cdTimer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCDView();

            }

            @Override
            public void onFinish() {
                running = false;
                startButton.setText("Start");
                resetButton.setEnabled(false);

            }
        }.start();
    }

    private void updateCDView() {
        int hoursLeft = (int) timeLeft / 1000 / 60 / 60;
        int minLeft = (int) timeLeft / 1000 / 60 % 60;
        int secLeft = (int) timeLeft / 1000 % 60 % 60;
        String hours = Integer.toString(hoursLeft);
        String minutes = Integer.toString(minLeft);
        String seconds = Integer.toString(secLeft);
        if (hoursLeft<10){
            hours = "0"+hours;
        }
        if (minLeft<10){
            minutes = "0"+ minutes;
        }
        if (secLeft<10){
            seconds = "0" + seconds;
        }
        String viewTime = hours + ":" + minutes + ":" + seconds;
        timerShow.setText(viewTime);
    }

    private void setupSpinner(Spinner spinner, int array) {
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(Timer.this,array,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
    }
    private int getCountdownTime(){
        int hours = Integer.parseInt(spinnerHours.getSelectedItem().toString());
        int sec = Integer.parseInt(spinnerSec.getSelectedItem().toString());
        int min = Integer.parseInt(spinnerMin.getSelectedItem().toString());
        totalTime = hours*3600000 + min*60000 + sec*1000;
        return totalTime;
    }

}