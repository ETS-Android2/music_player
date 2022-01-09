package np.com.jenishabaral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.net.LocalServerSocket;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.strings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(this, "This is setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(this, "This is search", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_exit:
                System.exit(0);
                break;

            case R.id.action_favourite:
                Toast.makeText(this, "This is favourite", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_help:

                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(" We will be back soon!");
                alertDialog.setMessage("Sorry,users all the features is completely closed before the completion of this app.");
                alertDialog.setIcon(R.drawable.ic_sentiment_dissatisfied);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;

            default:
                Toast.makeText(this, "Current process is optimal", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main, new MusicFragment()).commit();

    }
}

// mysongs=MediaPlayer.create(MainActivity.this,R.raw.bababoltahaye);
//        simpleVideoView=findViewById(R.id.simpleVideoView);
//        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.here));
//        simpleVideoView.start();

//        MediaController mediaController = new MediaController(this);
//
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                    simpleVideoView.pause();
//                    image.setImageResource(R.drawable.ic_play_arrow_black_24dp);
//            }
//        });
//