package com.example.musicplayer.MusicPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.databinding.ActivityActitivityMusicBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActitivityMusic extends AppCompatActivity {
ActivityActitivityMusicBinding musicBinding;
MediaPlayer mediaPlayer;
String news;
    Snackbar snackbar;
public int i=0;

    ArrayList<String> songlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicBinding=ActivityActitivityMusicBinding.inflate(getLayoutInflater());
        setContentView(musicBinding.getRoot());
        songlist.add("https://pagalworld.com.se/files/download/id/6591");
        songlist.add("https://pagalworldl.com/files/download/id/10061");
        songlist.add("https://cdn.pagalworld.us/songs/old/Tum%20Mile-Tum%20Mile.mp3");
        songlist.add("https://pagalworld.com.se/files/download/id/64630");


        //Static MediaPlayer
//        mediaPlayer=MediaPlayer.create(this,R.raw.osathi);
        //Dyanamic Mediaplayer
        mediaPlayer=new MediaPlayer();
        news=songlist.get(i);
        music();
        allbutton();


    }

    private void allbutton() {
        play();
        pause();
        next();
        prev();
    }

    private void prev() {
        musicBinding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.reset();
                if(i==0){
                    snackbar.make(view, "No More Prev Song.. Playing First One..", Snackbar.LENGTH_LONG).show();
                    news=songlist.get(i);
                    music();
                }
                else{
                    news=songlist.get(i-1);
                    music();
                    i--;
                }
            }
        });
    }

    private void next() {
        musicBinding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               mediaPlayer.pause();
                mediaPlayer.reset();
                if(i==(songlist.size()-1)){
                    snackbar.make(view, "No More Next Song.. Playing Last One..", Snackbar.LENGTH_LONG).show();
                    news=songlist.get(i);
                    music();
                }
                else{
                    news=songlist.get(i+1);
                    music();
                    i++;
                }
//               mediaPlayer=new MediaPlayer();


//                news=songlist.get(1);
//                mediaPlayer.pause();
//                Toast.makeText(getApplicationContext(), "ok "+news, Toast.LENGTH_SHORT).show();
//                music();
            }
        });
    }

    private void pause() {
        musicBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();

            }
        });
    }

    private void play() {
        musicBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
    }

    public void music() {
        try {
            mediaPlayer.setDataSource(news);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                new CustomToast(ActitivityMusic.this,"Wait a moment.. Playing..").show();
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