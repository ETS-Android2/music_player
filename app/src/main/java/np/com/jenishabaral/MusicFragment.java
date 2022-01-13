package np.com.jenishabaral;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.SyncFailedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MusicFragment extends Fragment {


    private static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    MediaPlayer mediaPlayer;
    ArrayList<Music> music_list,music_story_list;
    private static final String TAG = "MusicFragment";
    FloatingActionButton floatingButton ;
    BottomNavigationView bottomNavigationView;
    Timer timer;
    int i=0;
    RotateAnimation anim;

    TextView  playListSong,playListSongArtist;
    ImageView playListImage ;
    ImageView cancelAudioPlaying,pausePlay;
    MusicAdapter musicAdapter;
    int count =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout,container,false);

    }

    RecyclerView recycler,recyclerStory;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler =  view.findViewById(R.id.recycler);
        floatingButton = view.findViewById(R.id.floatingButton);
        playListSong = view.findViewById(R.id.playListSong);
        playListSongArtist = view.findViewById(R.id.playListSongArtist);
        playListImage = view.findViewById(R.id.playListImage);
        bottomNavigationView = view.findViewById(R.id.bottomNavBar);
        cancelAudioPlaying = view.findViewById(R.id.cancelAudioPlaying);
        pausePlay = view.findViewById(R.id.playAudioPlaying);


        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mediaPlayer = new MediaPlayer();
        timer = new Timer();
        bottomNavigationView.setVisibility(View.INVISIBLE);


        recyclerStory=view.findViewById(R.id.recyclerStory);
        recyclerStory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE_PERMISSION);

    }



    public  void load_music(){
        // fetching musics from local storage.

        music_list = new ArrayList<>();
        Uri uri          = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0" ;
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor    = getContext().getContentResolver().query(uri,null,selection,null,sortOrder);


        if(cursor !=null){
            if(cursor.moveToFirst()){

                do {
                    String name   = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));


                // this one will be played as a song or not?
                    String uri_data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));


                    Music musics = new Music(name,artist,uri_data);
                    music_list.add(musics);

                }while(cursor.moveToNext());
            }
            cursor.close();
        }
         musicAdapter = new MusicAdapter(music_list);

        if(musicAdapter.getItemCount() > 0){
            recycler.setAdapter(musicAdapter);
            musicAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getActivity(), "Oops....No musics found!", Toast.LENGTH_SHORT).show();
            noMusicItemActivity();

        }
    }




    public  void load_story_music(){
        // fetching musics from local storage.

        music_story_list = new ArrayList<>();


        Uri uri          = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0" ;
        Cursor cursor    = getContext().getContentResolver().query(uri,null,selection,null,null);


        if(cursor !=null){
            if(cursor.moveToFirst()){

                do {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String uri_data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));


                    music_story_list.add(new Music(name,artist,uri_data));

                }while(cursor.moveToNext());
            }
            cursor.close();
        }


        MusicStoryAdapter musicStoryAdapter = new MusicStoryAdapter(music_story_list);
        if(musicStoryAdapter.getItemCount() > 0){
            recyclerStory.setAdapter(musicStoryAdapter);
            musicStoryAdapter.notifyDataSetChanged();
        }
    }





    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
            load_music();
            load_story_music();


            this.floatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    floatingButton.hide();
                    animateWhilePlaying();

                    playListImage.setAnimation(anim);

                    try {

                        if(musicAdapter.getItemCount() > 0){
                            String musicSingleItem =  music_list.get(0).getMySongs();
                            mediaPlayer.setDataSource(getActivity(),Uri.parse(musicSingleItem));
                            mediaPlayer.prepare();
                            mediaPlayer.start();

                            String artistName =  music_list.get(0).getArtist();
                            String musicName =  music_list.get(0).getSong();

                            playListSong.setText(musicName);
                            playListSongArtist.setText(artistName);
                        }else{
                            noMusicItemActivity();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    if (musicAdapter.getItemCount() > 1){
                        playNext();
                    }else{
                        showPlayingDataItems();
                    }
                }
            });



            cancelAudioPlaying.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                    floatingButton.show();
                }
            });




            pausePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.pause();

                    if (count % 2 == 0){
                        mediaPlayer.start();
                        pausePlay.setImageResource(R.drawable.ic_pause_icon_white);
                        playListImage.startAnimation(anim);


                    }
                    else {
                        mediaPlayer.pause();
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        playListImage.setAnimation(null);
                    }
                    count++;
                }
            });

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getActivity(), "Read External Storage Permission Granted", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(getActivity(), "Read External Storage Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void playNext() {


        Toast.makeText(getActivity(), "Music first completed and second is in array", Toast.LENGTH_SHORT).show();
                try {
                    String musicSingleItem =  music_list.get(++i).getMySongs();
                    mediaPlayer.setDataSource(getActivity(),Uri.parse(musicSingleItem));
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    String artistName =  music_list.get(++i).getArtist();
                    String musicName =  music_list.get(++i).getSong();

                    playListSong.setText(musicName);
                    playListSongArtist.setText(artistName);


                } catch (IOException e) {
                    e.printStackTrace();
                }
    }




    @Override
    public void onDestroy() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        timer.cancel();
        super.onDestroy();
    }




    public void  animateWhilePlaying(){
        anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(2500);

    }



    public  void noMusicItemActivity(){
        Intent intent = new Intent(getActivity(), NoItemActivity.class);
        startActivity(intent);
    }


    public void showPlayingDataItems(){
        mediaPlayer.stop();
        mediaPlayer.reset();
        bottomNavigationView.setVisibility(View.INVISIBLE);
        floatingButton.show();
    }


}
