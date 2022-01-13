package np.com.jenishabaral;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicStoryAdapter extends RecyclerView.Adapter<MusicViewHolder>{


    ArrayList<Music> musicStory;

    public MusicStoryAdapter(ArrayList<Music> musicStory) {
        this.musicStory=musicStory;
    }


    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_horizontallayout,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.setDataMusicStory(musicStory.get(position));

    }

    @Override
    public int getItemCount() {
        return musicStory.size();
    }
}
