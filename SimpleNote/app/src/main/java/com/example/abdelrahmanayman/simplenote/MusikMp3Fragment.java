package com.example.abdelrahmanayman.simplenote;

import android.app.Fragment;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

public class MusikMp3Fragment extends Fragment {

    Button play;
    Button stop;
    Button pause;
    SeekBar seekBar ;
    MediaPlayer mediaPlayer ;
    int fProgress ;
    public MusikMp3Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_musik, container, false);

        play = view.findViewById(R.id.btnStart);
        stop = view.findViewById(R.id.btnStop);
        pause = view.findViewById(R.id.btnPause);

        seekBar = view.findViewById(R.id.seekBarMP3);
        try {
            mediaPlayer = MediaPlayer.create(getActivity() , R.raw.ms);
            mediaPlayer.prepare();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                fProgress = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(fProgress);
            }
        });
        myThread myth = new myThread();
        myth.start();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                }
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                }
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.ms);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                }
            }
        });
        return view ;
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(getActivity() , MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class myThread extends Thread {
        public void run() {

            while (mediaPlayer != null) {
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                }
                seekBar.post(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                });
            }


        }


    }
}