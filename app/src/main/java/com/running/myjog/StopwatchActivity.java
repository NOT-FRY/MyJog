package com.running.myjog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;
import android.widget.TextView;

public class StopwatchActivity extends Activity {

    // Use seconds, running and wasRunning respectively
    // to record the number of seconds passed,
    // whether the stopwatch is running and
    // whether the stopwatch was running
    // before the activity was paused.

    // Number of seconds displayed
    // on the stopwatch.
    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    private boolean wasRunning;

    private int corsa,camminata,ripetizioni,tempoTotale;

    private int corsaSecondi,camminataSecondi,tempoTotaleSecondi;

    private int secondiCorsi,secondiCamminati;
    private TextView testo;

    private boolean correndo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        testo= findViewById(R.id.testo);

        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
            secondiCorsi = savedInstanceState.getInt("SECONDI_CORSI");
            secondiCamminati = savedInstanceState.getInt("SECONDI_CAMMINATI");
        }
        running=true;
        correndo=false;
        Intent i = getIntent();
        corsa = i.getIntExtra("CORSA",0);
        camminata = i.getIntExtra("CAMMINATA",0);
        ripetizioni = i.getIntExtra("RIPETIZIONI",0 );
        tempoTotale = i.getIntExtra("TOTALE",0);

        tempoTotaleSecondi = tempoTotale * 60;

        corsaSecondi = corsa*60;
        camminataSecondi = camminata*60;

        secondiCorsi=0;
        secondiCamminati=0;
        runTimer();
    }

    // Save the state of the stopwatch
    // if it's about to be destroyed.
    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState)
    {
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
        savedInstanceState.putInt("SECONDI_CORSI",secondiCorsi);
        savedInstanceState.putInt("SECONDI_CAMMINATI",secondiCamminati);
    }

    // If the activity is paused,
    // stop the stopwatch.
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
        testo.setText("PAUSA");
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Start the stopwatch running
    // when the Start button is clicked.
    // Below method gets called
    // when the Start button is clicked.
    public void onClickStart(View view)
    {
        running = true;
    }

    // Stop the stopwatch running
    // when the Stop button is clicked.
    // Below method gets called
    // when the Stop button is clicked.
    public void onClickPause(View view)
    {
        running = false;
    }

    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    public void onClickStop(View view)
    {
        finish();
    }

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer()
    {

        // Get the text view.
        final TextView timeView
                = (TextView)findViewById(
                R.id.time_view);

        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                if(seconds<=tempoTotaleSecondi){
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;


                    // Format the seconds into hours, minutes,
                    // and seconds.
                    String time
                            = String
                            .format(Locale.getDefault(),
                                    "%d:%02d:%02d", hours,
                                    minutes, secs);

                    // Set the text view text.
                    timeView.setText(time);

                    // If running is true, increment the
                    // seconds variable.

                    if(secondiCamminati>=camminataSecondi){
                        testo.setText("CORRI !");
                        correndo=true;
                        secondiCamminati=0;
                    }

                    Log.d("DEBUG","camminataSecondi:"+camminataSecondi);
                    Log.d("DEBUG","corsaSecondi:"+corsaSecondi);

                    if(secondiCorsi>=corsaSecondi){
                        testo.setText("CAMMINA !");
                        correndo=false;
                        secondiCorsi=0;
                    }

                    if (running) {
                        seconds++;
                        if(correndo) {
                            secondiCorsi++;
                        }else{
                            secondiCamminati++;
                        }
                    }else{
                        testo.setText("PAUSA");
                    }

                    Log.d("DEBUG","secondiCamminati:"+secondiCamminati);
                    Log.d("DEBUG","secondiCorsi:"+secondiCorsi);
                    Log.d("DEBUG","secondiTotali:"+tempoTotaleSecondi);
                    Log.d("DEBUG","seconds:"+seconds);

                    // Post the code again
                    // with a delay of 1 second.
                    handler.postDelayed(this, 1000);
                }
                else{
                    testo.setText("FINITO ! premi stop");
                }
            }
        });
    }
}