package com.example.gymtimer;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    boolean active=false;
    Button button;
    CountDownTimer countDownTimer;

    public void timer(int i) {


        if (i>0) {
            int min = i / 60;
            int sec = i % 60;
            //Log.i("hgh",Integer.toString(min)+":"+Integer.toString(sec));
            String minute = Integer.toString(min);
            String second = Integer.toString(sec);
            textView = (TextView) findViewById(R.id.text);
            if (min < 10) {
                minute = "0" + Integer.toString(min);
            }
            if (sec < 10) {
                second = "0" + Integer.toString(sec);
            }
            seekBar.setProgress(i);
            textView.setText(minute + ":" + second);

        }else{
            textView.setText("00" + ":" +"00");

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating seekbar  for seekbar
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(300);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void startCounter(View view) {
    //Start timer , disable seekbar,active to true ,change the button text
        if (active==false) {
            active = true;
            seekBar.setEnabled(false);
            //button text changes
            button=(Button)findViewById(R.id.button);
            button.setText("stop");


            countDownTimer=new CountDownTimer(seekBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    timer((int) l / 1000);
                    textView.animate().rotationY(360).setDuration(1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.airhorn);
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "Time's Up!!", Toast.LENGTH_SHORT).show();
                    button.setText("start");
                    active=false;
                    seekBar.setEnabled(true);
                    seekBar.setProgress(seekBar.getProgress());
                    countDownTimer.cancel();
                }
            }.start();
        }

        //pause the timer , change the button timer,active change to false,enable seekbar
        else {
            //set button as restart
            button.setText("start");
            active=false;
            seekBar.setEnabled(true);
            seekBar.setProgress(seekBar.getProgress());
            countDownTimer.cancel();
        }
    }
}
