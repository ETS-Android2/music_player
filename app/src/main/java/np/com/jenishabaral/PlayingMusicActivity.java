package np.com.jenishabaral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class PlayingMusicActivity extends AppCompatActivity {
    private static final String TAG = "ActivityMusic";

    //declaring of class objects

    TextView      textView,textArtist,timerStart,timerEnd;
    ImageView     pause,imageViewMusic;
    MediaPlayer   mediaPlayer;
    Music music;
    RotateAnimation anim;
    private SeekBar seekBar;
    private Handler mHandler = new Handler();
    public  int count =0;

    //end declaring of class objects


    // main method of the activity started

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music);
        mediaPlayer = new MediaPlayer();

        seekBar  = findViewById(R.id.seekBar);
        imageViewMusic =  findViewById(R.id.imageView);
        timerStart = findViewById(R.id.timerStart);
        timerEnd = findViewById(R.id.timerEnd);



        Intent intent = getIntent();
        music = (Music) intent.getSerializableExtra("selected");


        String musicP = intent.getStringExtra("musicP");
        Log.d(TAG, "onCreate: "+musicP);


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                imageViewMusic.setAnimation(null);
                pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                Toast.makeText(getApplicationContext(), "Click again to play.", Toast.LENGTH_SHORT).show();
            }
        });


        try {
            mediaPlayer.setDataSource(musicP);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e){
            e.printStackTrace();
        }


        //Animation
        animateWhilePlaying();

        //seekbar
        setSeekBar();

        //declaring function
        declare();




        // try catch block for logical codes

        PlayingMusicActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition);

                    long milliseconds = mCurrentPosition;
                    long minutes = (milliseconds / 1000) / 60;
                    long seconds = (milliseconds / 1000) % 60;


                    if(seconds >=10){
                        timerStart.setText(minutes + ":" + seconds);
                    }else{
                        timerStart.setText(minutes + ":0" + seconds);
                    }


                }
                mHandler.postDelayed(this, 1000);
            }
        });



         try {

             textView.setText(music.song);
             textArtist.setText( music.artist);
             seekBar.setMax(mediaPlayer.getDuration());

             long milliseconds =mediaPlayer.getDuration();
             long minutes = (milliseconds / 1000) / 60;
             long seconds = (milliseconds / 1000) % 60;



             if(seconds >=10){
                 timerEnd.setText(minutes + ":" + seconds);
             }else{
                 timerEnd.setText(minutes + ":0" + seconds);
             }


             pause.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     mediaPlayer.pause();

                     if (count % 2 == 0){
                         mediaPlayer.start();
                         pause.setImageResource(R.drawable.ic_pause_black_24dp);
                         imageViewMusic.startAnimation(anim);


                     }
                     else {
                         mediaPlayer.pause();
                         pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                         imageViewMusic.setAnimation(null);
                     }
                     count++;
                 }
             });




         } catch (Exception e){

             e.printStackTrace();
             Log.d(TAG, "onCreate: "+e.getMessage());
         } // end of try and catch block

    }

    // end of main method


    public void declare(){

    //finding the xml content of id

        textView     =  findViewById(R.id.textView);
        textArtist   =  findViewById(R.id.textArtist);
        pause        =  findViewById(R.id.pause);


    } // end


    public void  animateWhilePlaying(){
        anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(2500);

        // Start animating the image
        imageViewMusic.startAnimation(anim);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }


    public  void setSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int x = (int) Math.ceil(progress / 1000f);

                if (x == 0 && mediaPlayer != null && !mediaPlayer.isPlaying()){
                    PlayingMusicActivity.this.seekBar.setProgress(0);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(seekBar.getProgress());
                    }
            }
        });

    }



}
