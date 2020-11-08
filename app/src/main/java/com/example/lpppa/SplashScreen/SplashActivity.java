package com.example.lpppa.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.lpppa.Login.LoginActivity;
import com.example.lpppa.MainActivity;
import com.example.lpppa.R;
import com.example.lpppa.menuHome.HomeFragment;

public class SplashActivity extends AppCompatActivity {

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv =  findViewById(R.id.iv_LogoSplash);
//        Animation myanim = new AnimationUtils().loadAnimation(this,R.anim.mytransition);
//        iv.startAnimation(myanim);
        final Intent i = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000) ;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }

            }
        };
        timer.start();
    }
}
