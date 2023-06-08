package com.smartdappev.shortstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView lovebutton,afributton,horrbutton,poembutton,novbutton,adultbutton,fictbutton,idiombutton;

    NestedScrollView nestedScrollView;
    private  static final String MyPref ="preference";
    private  static final String Background ="background";
    SharedPreferences.Editor editor;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lovebutton = findViewById(R.id.button);
        afributton = findViewById(R.id.button1);
        horrbutton = findViewById(R.id.button2);
        poembutton = findViewById(R.id.button3);
        novbutton = findViewById(R.id.button4);
        adultbutton = findViewById(R.id.button5);
        fictbutton = findViewById(R.id.button6);
        idiombutton = findViewById(R.id.button7);
        nestedScrollView = findViewById(R.id.nestedscroll);


        lovebutton.setOnClickListener(this);
        afributton.setOnClickListener(this);
        horrbutton.setOnClickListener(this);
        poembutton.setOnClickListener(this);
        novbutton.setOnClickListener(this);
        adultbutton.setOnClickListener(this);
        fictbutton.setOnClickListener(this);
        idiombutton.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        loadInterstitialAd();



        if(getcolor() !=(getResources().getColor(R.color.ocean))){
            nestedScrollView.setBackgroundColor(getcolor());
        }

    }

    public void loadInterstitialAd(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                //Toast.makeText(MainActivity.this, "fail to load ad", Toast.LENGTH_SHORT).show();
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                interstitialAd.show(MainActivity.this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        //Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        //Toast.makeText(MainActivity.this, "dismissed", Toast.LENGTH_SHORT).show();
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        //Toast.makeText(MainActivity.this, "fail to show fullscreen", Toast.LENGTH_SHORT).show();
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        //Toast.makeText(MainActivity.this, "impression", Toast.LENGTH_SHORT).show();
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        //Toast.makeText(MainActivity.this, "fon fullscreen", Toast.LENGTH_SHORT).show();
                        super.onAdShowedFullScreenContent();
                    }
                });
                super.onAdLoaded(interstitialAd);
            }
        });
    }



    @Override
    public void onClick(View view) {

       switch (view.getId()){
            case R.id.button:
                Intent intent = new Intent(MainActivity.this,LoveStoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0,android.R.anim.fade_out);

                break;
            case R.id.button1:
                Intent intent1 = new Intent(MainActivity.this, AfricanFables.class);
                startActivity(intent1);
                overridePendingTransition(0,android.R.anim.fade_out);

                break;
            case R.id.button2:
                Intent intent2 = new Intent(MainActivity.this, HorrorStory.class);
                startActivity(intent2);
                overridePendingTransition(0,android.R.anim.fade_out);

                break;
            case R.id.button3:
                Intent intent3 = new Intent(MainActivity.this, Poems.class);
                startActivity(intent3);
                overridePendingTransition(0,android.R.anim.fade_out);

                break;
            case R.id.button4:
                Intent intent4 = new Intent(MainActivity.this, Novel.class);
                startActivity(intent4);
                overridePendingTransition(0,android.R.anim.fade_out);

                break;
            case R.id.button5:
                Intent intent6 = new Intent(MainActivity.this, Fiction.class);
                startActivity(intent6);
                overridePendingTransition(0,android.R.anim.fade_out);

                break;
            case R.id.button6:
                Intent intent5 = new Intent(MainActivity.this, Adult.class);
                startActivity(intent5);
                overridePendingTransition(0,android.R.anim.fade_out);

                break;
           case R.id.button7:
               Intent intent7 = new Intent(MainActivity.this, Idiom.class);
               startActivity(intent7);
               overridePendingTransition(0,android.R.anim.fade_out);

               break;

            default:
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                return true;
            case R.id.item2:
                return true;

            case R.id.item5:
                nestedScrollView.setBackgroundColor(getResources().getColor(R.color.purple_mode));
                storeColor(getResources().getColor(R.color.purple_mode));
                return true;
            case R.id.item6:
                nestedScrollView.setBackgroundColor(getResources().getColor(R.color.light_mode));
                storeColor(getResources().getColor(R.color.light_mode));
                return true;
            case R.id.item7:
                nestedScrollView.setBackgroundColor(getResources().getColor(R.color.dark_mode));
                storeColor(getResources().getColor(R.color.dark_mode));
                return true;
            case R.id.item8:
                nestedScrollView.setBackgroundColor(getResources().getColor(R.color.night_sky));
                storeColor(getResources().getColor(R.color.night_sky));
                return true;
            case R.id.item9:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.ocean));
                    storeColor(getResources().getColor(R.color.ocean));
                return true;
            case R.id.item10:
                nestedScrollView.setBackgroundColor(getResources().getColor(R.color.night_mode));
                storeColor(getResources().getColor(R.color.night_mode));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private  void storeColor(int color){
        SharedPreferences sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(Background, color);
        editor.apply();
    }
    public int getcolor(){
        SharedPreferences sharedPreferences = getSharedPreferences(MyPref,Context.MODE_PRIVATE);
        int selectedTheme = sharedPreferences.getInt(Background, getResources().getColor(R.color.ocean));

        return selectedTheme;
    }

    public void Adult(View view) {
        String Title = getResources().getString(R.string.Adult_Title);
        String heading = getResources().getString(R.string.Adult_Title_A);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Title);
        intent.putExtra("heading",heading);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void Fiction(View view) {
        String Title = getResources().getString(R.string.Fiction_Title);
        String heading = getResources().getString(R.string.Fiction_Title_A);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Title);
        intent.putExtra("heading",heading);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void Novel(View view) {
        String Adult_Title = getResources().getString(R.string.Novel_Title);
        String heading = getResources().getString(R.string.Novel_Title_A);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Adult_Title);
        intent.putExtra("heading",heading);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void Poem(View view) {
        String Title = getResources().getString(R.string.Poem_Title);
        String heading = getResources().getString(R.string.Poem_Title_A);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Title);
        intent.putExtra("heading",heading);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void Horror(View view) {
        String Title = getResources().getString(R.string.Horror_Title);
        String heading = getResources().getString(R.string.Horror_Title_A);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Title);
        intent.putExtra("heading",heading);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void Fable(View view) {
        String Title = getResources().getString(R.string.Fable_Title);
        String heading = getResources().getString(R.string.Fable_Title_A);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Title);
        intent.putExtra("heading",heading);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void Love(View view) {
        String Title = getResources().getString(R.string.Love_Title);
        String heading = getResources().getString(R.string.Love_Title_A);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Title);
        intent.putExtra("heading",heading);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0,android.R.anim.fade_out);


    }

    public void Idiom(View view) {
        String Title = getResources().getString(R.string.idiom_tittle_list);
        String heading = getResources().getString(R.string.idiom_tittle);
        Intent intent = new Intent(MainActivity.this, TitleList.class);
        intent.putExtra("title",Title);
        intent.putExtra("heading",heading);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
    private void maximDialog(){
        @SuppressLint("ResourceType")
        Dialog dialog = new Dialog(this,android.R.anim.linear_interpolator);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.maxim);
        dialog.getWindow().setBackgroundDrawableResource(R.color.tran);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }//main ID ca-app-pub-7881857902310901/6081761143

    public void Moto(View view) {

        maximDialog();
    }
}