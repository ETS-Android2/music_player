package np.com.jenishabaral;

import android.media.MediaPlayer;
import android.os.Parcelable;

import java.io.Serializable;

public class Music implements Serializable {

    String song ;
    String artist;
    String mySongs;


    public Music(String song , String artist,String mySongs) {
        this.song  = song ;
        this.artist = artist;
        this.mySongs = mySongs;


    }


    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMySongs() {
        return mySongs;
    }

    public void setMySongs(String mySongs) {
        this.mySongs = mySongs;
    }
}
