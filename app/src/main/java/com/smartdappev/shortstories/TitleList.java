package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleList extends AppCompatActivity {
    TextView getTitle,getHeading;

    LinearLayout linearLayoutl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        linearLayoutl = findViewById(R.id.RL);
        getTitle = findViewById(R.id.title_info);
        getHeading = findViewById(R.id.heading);



        String storyName = getIntent().getStringExtra("title");
        String heading = getIntent().getStringExtra("heading");
        getTitle.setText(storyName);
        getHeading.setText(heading);




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y =-20;
        getWindow().setAttributes(params);
        
        
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        /*Intent intent = new Intent(Writerblog.this,Home.class);
        startActivity(intent);
        finish();*/
    }
}