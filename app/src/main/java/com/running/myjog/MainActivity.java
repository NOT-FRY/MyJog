package com.running.myjog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText corsa,camminata,ripetizioni;
    private TextView tempo,countdown;
    int co,ca,rip,tempoTotale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        corsa=findViewById(R.id.corsa);
        camminata=findViewById(R.id.camminata);
        ripetizioni=findViewById(R.id.ripetizioni);
        tempo=findViewById(R.id.tempo);
        countdown=findViewById(R.id.countdown);
        co=0;
        ca=0;
        rip=0;
        tempoTotale=0;
        if (savedInstanceState != null) {
            // if the activity has been
            // destroyed and recreated.
            co
                    = savedInstanceState
                    .getInt("CORSA");
            ca
                    = savedInstanceState
                    .getInt("CAMMINATA");
            rip
                    = savedInstanceState
                    .getInt("RIPETIZIONI");
            tempoTotale = savedInstanceState.getInt("TOTALE");
        }
        if(co!=0)
            corsa.setText(""+co);
        if(ca!=0)
            camminata.setText(""+ca);
        if(rip!=0)
            ripetizioni.setText(""+rip);
        if(tempoTotale!=0)
            tempo.setText(tempoTotale+" minuti");
    }

    public void start(View v){
        if(checkParameters(corsa)&&checkParameters(camminata)&&checkParameters(ripetizioni)){
            try{
                co = Integer.parseInt(corsa.getText().toString());
                ca = Integer.parseInt(camminata.getText().toString());
                tempoTotale = (co+ca)*Integer.parseInt(ripetizioni.getText().toString());
            }catch(Exception e){

            }
            tempo.setText(tempoTotale+" minuti");

            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                    countdown.setText("" + ((millisUntilFinished / 1000)+1));
                }

                public void onFinish() {
                    try {
                        Intent i = new Intent(getApplicationContext(), StopwatchActivity.class);
                        co = Integer.parseInt(corsa.getText().toString());
                        ca = Integer.parseInt(camminata.getText().toString());
                        int rip = Integer.parseInt(ripetizioni.getText().toString());
                        tempoTotale = (co+ca)*Integer.parseInt(ripetizioni.getText().toString());
                        i.putExtra("CORSA", co);
                        i.putExtra("CAMMINATA", ca);
                        i.putExtra("RIPETIZIONI", rip);
                        i.putExtra("TOTALE",tempoTotale);
                        startActivity(i);
                    }catch(Exception e){

                    }
                }
            }.start();

        }else{
            Toast.makeText(this,"Per favore, riempi tutti i campi",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkParameters(EditText e){
        if(e.getText()!=null && e.getText().toString()!=null && !e.getText().toString().trim().equals("") && e.getText().toString().matches("^[0-9]*$")) {
            return true;
        }
        else
            return false;
    }

    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("CORSA", co);
        savedInstanceState
                .putInt("CAMMINATA", ca);
        savedInstanceState
                .putInt("RIPETIZIONI", rip);
        savedInstanceState.putInt("TOTALE",tempoTotale);
    }
}