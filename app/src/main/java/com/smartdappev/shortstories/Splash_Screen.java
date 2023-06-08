package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class  Splash_Screen extends AppCompatActivity {
    int value;
    ProgressBar progressBar;
    TextView textView,textView1;
    Handler handler = new Handler();
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.Pbar);
        relativeLayout = findViewById(R.id.myRelative);
       overridePendingTransition(R.anim.fade_in,0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startProgress();
            }
        });
        thread.start();
    }
    public void startProgress(){

        for (value =0 ;value<101;value++){
            try {
                Thread.sleep(40);
                progressBar.setProgress(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {

                @Override
                public void run() {
                    //textView.setText(String.valueOf(value-1 +"%"));
                    //textView1.setText("Preparing The Application");
                    switch (value){
                        case 1:

                            //textView.setTextColor(getResources().getColor(R.color.navy));
                            break;
                        case 66:

                            //textView.setTextColor(getResources().getColor(R.color.Bluer));
                            break;

                        case 101:
                            overridePendingTransition(0,R.anim.fade_out);
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();
                            break;
                        default:
                            break;

                    }

                }
            });
        }

    }
    public void FadeIn(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        relativeLayout.setAnimation(animation);
    }
    public void FadeOut(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        relativeLayout.setAnimation(animation);
    }
}