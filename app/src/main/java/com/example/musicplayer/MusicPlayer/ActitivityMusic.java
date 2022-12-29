package com.example.musicplayer.MusicPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.databinding.ActivityActitivityMusicBinding;

import java.io.IOException;

public class ActitivityMusic extends AppCompatActivity {
ActivityActitivityMusicBinding musicBinding;
MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicBinding=ActivityActitivityMusicBinding.inflate(getLayoutInflater());
        setContentView(musicBinding.getRoot());

        //Static MediaPlayer
//        mediaPlayer=MediaPlayer.create(this,R.raw.osathi);


        //Dyanamic Mediaplayer
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://cdn.pagalworld.us/songs/bollywood/Shab%20-%20O%20Saathi.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(ActitivityMusic.this, "Playing...", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                //Uses Same Class by taking new instance
                Updateseekbar updateseekbar=new Updateseekbar();
                new Handler().post(updateseekbar);

                //Seekbar
                musicBinding.seekbar.setMax(mediaPlayer.getDuration());
                musicBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        if(b){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                mediaPlayer.seekTo(i);

                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

            }
        });
        mediaPlayer.prepareAsync();
        musicBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();

            }
        });
        musicBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });



    }
    //Create Class with runnable mehtod
    public class Updateseekbar implements Runnable{

        @Override
        public void run() {
            musicBinding.seekbar.setProgress(mediaPlayer.getCurrentPosition());
            new Handler().postDelayed(this,1000);
        }
    }
}