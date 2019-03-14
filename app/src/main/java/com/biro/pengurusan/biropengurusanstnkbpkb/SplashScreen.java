package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private TextView tv;
    /*private ImageView iv;*/

    ImageView tulisan;
    Animation frombottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tulisan = (ImageView) findViewById(R.id.tulisan);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.mytransition);

        tulisan.setAnimation(frombottom);
        /*iv = (ImageView) findViewById(R.id.gambar);*/
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        /*iv.startAnimation(myanim);*/
        final Intent i = new Intent(this,Login.class);
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
