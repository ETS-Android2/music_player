package np.com.jenishabaral;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                  if(mMusic!=null)
                  {
//                      Toast.makeText(itemView.getContext(), "Clicked:\t" +mMusic.getSong(), Toast.LENGTH_SHORT).show();
                      Intent intent= new Intent(itemView.getContext(),ActivityMusic.class);
                      intent.putExtra("selected", mMusic);
                      intent.putExtra("musicP", mMusic.mySongs);
                      itemView.getContext().startActivity(intent);
                  }

                  else
                  {
                      Toast.makeText(itemView.getContext(), "No content to show", Toast.LENGTH_SHORT).show();

                  }
              }
          });


    }


    public void setdata1(Music musicStory)

    {
        text1.setText(musicStory.song);
        text2.setText(musicStory.artist);

    }

    public void setdata2(Music music)
    {
        mMusic=music;
        text1.setText(music.song);
        text2.setText(music.artist);

       // another = MediaPlayer.create(itemView.getContext(),R.raw.neno);
    }
}
