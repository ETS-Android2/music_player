package np.com.jenishabaral;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class MusicViewHolder extends RecyclerView.ViewHolder {

    TextView text1,text2;
    Music mMusic;
    ImageView image;

    public MusicViewHolder(@NonNull final View itemView) {
        super(itemView);

          text1=itemView.findViewById(R.id.text1);
          text2=itemView.findViewById(R.id.text2);
          image=itemView.findViewById(R.id.image);


          text1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                      Intent intent= new Intent(itemView.getContext(), PlayingMusicActivity.class);
                      intent.putExtra("selected", mMusic);
                      intent.putExtra("musicP", mMusic.mySongs);
                      itemView.getContext().startActivity(intent);

              }
          });


    }


    public void setDataMusicStory(Music musicStory)

    {
        text1.setText(musicStory.song);
        text2.setText(musicStory.artist);

    }

    public void setDataMusic(Music music)
    {
        mMusic=music;
        text1.setText(music.song);
        text2.setText(music.artist);

       // another = MediaPlayer.create(itemView.getContext(),R.raw.neno);
    }
}
