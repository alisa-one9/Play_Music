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

import com.airbnb.lottie.LottieAnimationView;
import com.example.play_music.AudioModel;
import com.example.play_music.R;
import com.example.play_music.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.Objects;

public class SlideshowFragment extends Fragment implements MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnBufferingUpdateListener {

    private FragmentSlideshowBinding binding;
    private SeekBar playerBar;
    private Handler handler;
    AudioModel n_audioModel;
    private MediaPlayer mediaPlayer;

    private final String KEY_M= "KEY_M";

    boolean fromUser= true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mediaPlayer =new MediaPlayer();
        handler = new Handler();
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
    }



    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        if (getArguments() != null) {

            AudioModel new_audioModel =(AudioModel)getArguments().getSerializable(KEY_M);

            n_audioModel = new_audioModel;

            binding.imagePlayPage.setImageResource(new_audioModel.getImage());
            binding.nameOfTrack.setText(new_audioModel.getTitle());


        } else {

            Toast.makeText(requireActivity(), " there are nothing", Toast.LENGTH_SHORT).show();

        }
        binding.playBar.setMax(100);
        prepareMediaPlayer();


        binding.playBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && !fromUser) {
                    long newPosition = (mediaPlayer.getDuration() * progress) / 100;
                    mediaPlayer.seekTo((int) newPosition);
                    binding.tvCurrentTime.setText(milliSecondsToTimer(newPosition));

                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                updateSeekBar();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.imagePlay.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                updateSeekBar();
                mediaPlayer.start();
                binding.imagePlay.setVisibility(View.INVISIBLE);
                binding.imagePause.setVisibility(View.VISIBLE);
            } else {
                mediaPlayer.start();
                updateSeekBar();
                binding.imagePlay.setVisibility(View.INVISIBLE);
                binding.imagePause.setVisibility(View.VISIBLE);
            }
        });

        binding.imagePause.setOnClickListener(v1 -> {
            mediaPlayer.pause();
            binding.imagePause.setVisibility(View.INVISIBLE);
            binding.imagePlay.setVisibility(View.VISIBLE);
        });


        binding.btnNextMusic.setOnClickListener(v2 -> {

            Toast.makeText(requireActivity(), "NEXT", Toast.LENGTH_SHORT).show();
        });
        binding.btnPreviousMusic.setOnClickListener(v3 -> {
            Toast.makeText(requireActivity(), "PREVIOUS", Toast.LENGTH_SHORT).show();
        });

    }
    private void prepareMediaPlayer() {

        mediaPlayer = MediaPlayer.create(requireActivity(), n_audioModel.getPath());
        binding.tvTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
        updateSeekBar();
    }
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        binding.playBar.setSecondaryProgress(percent);
        updateSeekBar();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        updateSeekBar();
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
        if (mediaPlayer.isPlaying()) {
            int progress = (int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100);
            binding.playBar.setProgress(progress);
            handler.postDelayed(updater, 20000);

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