package com.example.play_music.ui.slideshow;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.play_music.AudioModel;
import com.example.play_music.R;
import com.example.play_music.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.Objects;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    public Button btn_play, btn_pause, btn_prev, btn_next;
    private TextView textCurrentTime, textTotalDuration;

    private SeekBar playerBar;
    private Handler handler;
    AudioModel n_audioModel;
    private MediaPlayer mediaPlayer;

    private final String KEY_PATH = "KEY_PATH";
    private final String KEY_TITLE = "KEY_TITLE";
    private final String KEY_IMAGE = "KEY_IMAGE";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mediaPlayer = new MediaPlayer();
        prepareMediaPlayer();
    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {

            AudioModel new_audioModel = new AudioModel(
                    getArguments().getInt(KEY_PATH),
                    getArguments().getString(KEY_TITLE),
                    getArguments().getInt(KEY_IMAGE));

            n_audioModel = new_audioModel;

            binding.imagePlayPage.setImageResource(new_audioModel.getImage());
            binding.nameOfTrack.setText(new_audioModel.getTitle());
        } else {
            Toast.makeText(requireActivity(), " there are nothing", Toast.LENGTH_SHORT).show();
        }
        binding.playBar.setMax(100);

        binding.imagePlay.setOnClickListener(v -> {

            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                mediaPlayer.start();
                binding.imagePlay.setVisibility(View.INVISIBLE);
                binding.imagePause.setVisibility(View.VISIBLE);
            } else {
                prepareMediaPlayer();
                mediaPlayer.start();
                binding.imagePlay.setVisibility(View.INVISIBLE);
                binding.imagePause.setVisibility(View.VISIBLE);
            }
        });
        binding.imagePause.setOnClickListener(v1 -> {
            mediaPlayer.pause();
            binding.imagePause.setVisibility(View.INVISIBLE);
            binding.imagePlay.setVisibility(View.VISIBLE);
        });

        binding.playBar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                binding.tvCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
//        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//            @Override
//            public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
//                playerBar.setSecondaryProgress(percent);
//
//            }
//        });

        binding.btnNextMusic.setOnClickListener(v2 -> {

            Toast.makeText(requireActivity(), "NEXT", Toast.LENGTH_SHORT).show();
        });
        binding.btnPreviousMusic.setOnClickListener(v3 -> {
            Toast.makeText(requireActivity(), "PREVIOUS", Toast.LENGTH_SHORT).show();
        });

        return root;
    }


    private void prepareMediaPlayer() {

                mediaPlayer = MediaPlayer.create(requireActivity(), n_audioModel.getPath());


            binding.tvTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));


    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
                    updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            binding.tvCurrentTime.setText(milliSecondsToTimer(currentDuration));

        }
    };
    private void updateSeekBar() {

        if(mediaPlayer.isPlaying()){
            playerBar.setProgress((int)(((float) mediaPlayer
                    .getCurrentPosition()/ mediaPlayer.getDuration())*100));
            handler.postDelayed(updater,1000);
        }

    }

    private String milliSecondsToTimer(long milliSeconds) {
        String timerString = "";
        String secondString;
        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else {
            secondString = "" + seconds;
        }
        timerString = timerString + minutes + ":" + secondString;
        return timerString;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediaPlayer.release();
        binding = null;
    }
}