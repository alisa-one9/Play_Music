package com.example.play_music.ui.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.play_music.AudioModel;
import com.example.play_music.R;
import com.example.play_music.databinding.FragmentGalleryBinding;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private FragmentGalleryBinding binding;
    MusicAdapter musicAdapter;
    List<AudioModel> list = new ArrayList<>();
    int[] images = {R.drawable.artist_weekend, R.drawable.ic_launcher_background,
            R.drawable.artist_weekend, R.drawable.ic_launcher_background,
            R.drawable.artist_weekend, R.drawable.ic_launcher_background,
            R.drawable.artist_weekend, R.drawable.ic_launcher_background,
            R.drawable.artist_weekend, R.drawable.ic_launcher_background};
    int[] path_list = {R.raw.adam_jurek,
            R.raw.enastra,
            R.raw.fone_music,
            R.raw.fone_music,
            R.raw.fone_music,
            R.raw.fone_music,
            R.raw.fone_music,
            R.raw.fone_music,
            R.raw.fone_music,
            R.raw.fone_music };



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        musicAdapter = new MusicAdapter();
        createList();
        musicAdapter.setList(list);
        binding.rvMusic.setAdapter(musicAdapter);
        return root;
    }

    private void createList() {
        list.add(new AudioModel(path_list[0], "track 1", images[0]));
        list.add(new AudioModel(path_list[1], "track 2", images[1]));
        list.add(new AudioModel(path_list[2], "track 3", images[2]));
        list.add(new AudioModel(path_list[3], "track 4", images[3]));
        list.add(new AudioModel(path_list[4], "track 5", images[4]));
        list.add(new AudioModel(path_list[5], "track 6", images[5]));
        list.add(new AudioModel(path_list[6], "track 7", images[6]));
        list.add(new AudioModel(path_list[7], "track 8", images[7]));
        list.add(new AudioModel(path_list[8], "track 9", images[8]));
        list.add(new AudioModel(path_list[9], "track 10", images[9]));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}