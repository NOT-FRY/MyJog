package com.running.myjog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText corsa,camminata,ripetizioni;
    private TextView tempo,countdown;
    private int co,ca,rip,tempoTotale;

    private Spinner spinner;
    private static final String[] livelli = {"Tempo personalizzato","Livello 1","Livello 2","Livello 3","Livello 4","Livello 5","Livello 6","Livello 7","Livello 8","Livello 9","Livello 10"};

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

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,livelli);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                camminata.setText("");
                corsa.setText("");
                ripetizioni.setText("");
                break;
            case 1:
                camminata.setText(""+2);
                corsa.setText(""+1);
                ripetizioni.setText(""+8);
                break;
            case 2:
                camminata.setText(""+3);
                corsa.setText(""+2);
                ripetizioni.setText(""+6);
                break;
            case 3:
                camminata.setText(""+3);
                corsa.setText(""+4);
                ripetizioni.setText(""+6);
                break;
            case 4:
                camminata.setText(""+3);
                corsa.setText(""+6);
                ripetizioni.setText(""+5);
                break;
            case 5:
                camminata.setText(""+3);
                corsa.setText(""+10);
                ripetizioni.setText(""+4);
                break;
            case 6:
                camminata.setText(""+3);
                corsa.setText(""+15);
                ripetizioni.setText(""+3);
                break;
            case 7:
                camminata.setText(""+5);
                corsa.setText(""+25);
                ripetizioni.setText(""+2);
                break;
            case 8:
                camminata.setText(""+5);
                corsa.setText(""+40);
                ripetizioni.setText(""+1);
                break;
            case 9:
                camminata.setText(""+5);
                corsa.setText(""+45);
                ripetizioni.setText(""+1);
                break;
            case 10:
                camminata.setText(""+5);
                corsa.setText(""+50);
                ripetizioni.setText(""+1);
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
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
                        countdown.setText("");
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