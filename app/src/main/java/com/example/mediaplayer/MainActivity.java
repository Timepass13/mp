package com.example.mediaplayer;


import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button btnback,btnfor,btnpause,btnplay;
    private MediaPlayer mediaplayer;
    private TextView txt;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnback = findViewById(R.id.buttonbackward);
        btnfor = findViewById(R.id.buttonforward);
        btnpause = findViewById(R.id.buttonpause);
        btnplay = findViewById(R.id.buttonplay);
        txt = findViewById(R.id.textView4);
        txt.setText("Song.mp3");
        seekbar = findViewById(R.id.seekBar2);
        seekbar.setClickable(true);
        btnpause.setEnabled(false);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Playing sound",Toast.LENGTH_SHORT).show();
                if(mediaplayer == null)
                {
                    mediaplayer = MediaPlayer.create(getBaseContext(), R.raw.music);
                    finalTime = mediaplayer.getDuration();
                    startTime = mediaplayer.getCurrentPosition();
                    seekbar.setMax((int) finalTime);
                }
                mediaplayer.start();
                seekbar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);
                btnpause.setEnabled(true);
                btnplay.setEnabled(false);
            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                mediaplayer.pause();
                btnpause.setEnabled(false);
                btnplay.setEnabled(true);

            }
        });

        btnfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaplayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaplayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaplayer.getCurrentPosition();
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
}