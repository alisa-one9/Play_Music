package com.example.play_music.ui.gallery;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_music.AudioModel;
import com.example.play_music.R;
import com.example.play_music.databinding.ItemMusicBinding;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    ItemMusicBinding binding;
    List<AudioModel> list = new ArrayList<>();
    NavController navController;
    private final String KEY_M = "KEY_M";


    public void setList(List<AudioModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMusicBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
        }

        public void onBind(AudioModel model) {

            binding.btnToHear.setOnClickListener(v -> {

                Bundle bundle = new Bundle();

                bundle.putSerializable(KEY_M, new AudioModel( model.getPath(),model.getTitle(), model.getImage()));

                navController = Navigation.findNavController((Activity)itemView.getContext(),
                        R.id.nav_host_fragment_content_main);

                try {
                    navController.navigate(R.id.action_nav_gallery_to_nav_slideshow, bundle);
                }catch (NullPointerException ex){

                    if (bundle==null){

                        navController.navigate(R.id.action_nav_gallery_to_nav_slideshow);
                    }
                }
            });
            binding.titleTrackCard.setText(model.getTitle());
            binding.imageTrackCard.setImageResource(model.getImage());

        }
    }
}
