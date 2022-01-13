package np.com.jenishabaral;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicViewHolder>{


    ArrayList<Music>  music ;

    // passing the newly created array list to the constructor
    public MusicAdapter(ArrayList<Music>  music_data) {
        this.music = music_data;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout,parent,false);
        return new MusicViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.setDataMusic(music.get(position));
    }

    @Override
    public int getItemCount() {
        return music.size();
    }
}
