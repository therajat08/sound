package com.example.rajat.sound;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;
    //we need to be able to control android auto system
    AudioManager audioManager;
    public void playAudio(View view){


        mplayer.start();
    }

    public void pauseAudio(View view){
        mplayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer = MediaPlayer.create(this,R.raw.nade);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);//to get info about our volume
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //system's max volume
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// System's current volume

        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);


        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {



            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("seekbar value", Integer.toString(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0); //third parameter is flag to give more info which we dont wanna give so we put 0
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //getting the seekbar for controlling playtime of mp3
        final SeekBar scrubber =  (SeekBar) findViewById(R.id.scrubber);
        scrubber.setMax(mplayer.getDuration());//this line is to get duration of audio file

        //with below method we are trying to rum code within run() in  every 1000 period
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //code here is run periodically
                scrubber.setProgress(mplayer.getCurrentPosition());
                //with this code frequently we get where the audio file is in its timeline
            }
        },0, 100);//0 means it is run immediately for the first time

        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mplayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }




}
