package com.smartdappev.shortstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
TextView textView,textView1;
ImageView textMsg;
NestedScrollView nestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.text);
        textView1 = findViewById(R.id.text1);
        textMsg = findViewById(R.id.imageview);
        nestedScrollView = findViewById(R.id.homeRelative);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSelected(true);
        textView1.setSelected(true);

        //FadeIn();
        textMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maximDialog();

                }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item4:
                Intent info = new Intent(Home.this,AppHelpPageTwo.class);
                startActivity(info);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ShortStory(View view) {
        Intent intent = new Intent(Home.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void LongStory(View view) {
        Intent intent = new Intent(Home.this,FireBaseImage.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    private void showExitDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_app);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(R.color.tran);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.findViewById(R.id.stay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                overridePendingTransition(0,android.R.anim.fade_out);

            }
        });

        dialog.show();

    }

    private void maximDialog(){
        @SuppressLint("ResourceType")
        Dialog dialog = new Dialog(this,android.R.anim.linear_interpolator);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.maxim);
        dialog.getWindow().setBackgroundDrawableResource(R.color.tran);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    private  int getwindowwidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showExitDialog();
    }

    public void FadeIn(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        nestedScrollView.setAnimation(animation);
    }
    public void FadeOut(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        nestedScrollView.setAnimation(animation);

    }
}