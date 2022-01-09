package np.com.jenishabaral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        Thread thread= new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent= new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        thread.start();
    }
}
