package com.smartdappev.shortstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowBook extends AppCompatActivity implements View.OnTouchListener{
    TextView textView,textView1,textView2,title,genre;
    private  Settings settings;
    private SwitchMaterial themeSwitch;
    private View line,line1,layout,layout1;
    ScrollView scroll;
    TextToSpeech mTTs;
    ImageButton speech;
    ImageView storyImage;
    int baseDistance;
    float ratio = 1.0f;
    float baseRatio;
    final  static  float step = 200;

    private  static final  String SHARE_PREF_NAME = "myPref";
    private  static final  String FAMILY_FONT = "family_font";
    private  static final  String FONT_STYLE = "fontStyle";
    private  static final  String KEY_FONT = "keyFontStyle";
    SharedPreferences.Editor fontEditor;
    SharedPreferences FontsharedPreferences;

    SharedPreferences.Editor editor1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);

        FontsharedPreferences= getSharedPreferences(FONT_STYLE,MODE_PRIVATE);
        fontEditor= FontsharedPreferences.edit();

        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
         editor1 =sharedPreferences.edit();

        iniWidgets();



        textView1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView1.setSelected(true);
        textView2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView2.setSelected(true);




        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        mTTs = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status !=TextToSpeech.ERROR){
                    mTTs.setLanguage(Locale.US);

                }else{
                    Toast.makeText(ShowBook.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


        settings = (Settings) getApplication();


        laodSharePreferences();
        //matrixSharePreferences();
        iniSwitchListener();

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            int getImage_id = bundle.getInt("image");
            storyImage.setImageResource(getImage_id);
        }


        String details = getIntent().getStringExtra("detail");
        String storyName = getIntent().getStringExtra("title");
        String authorName = getIntent().getStringExtra("author");
        String bookGenre = getIntent().getStringExtra("genre");
        textView1.setText(storyName);
        textView.setText(details);
        textView2.setText(authorName);
        genre.setText(bookGenre);


        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mTTs.isSpeaking()) {

                    mTTs.stop();
                    speech.setBackground(getResources().getDrawable(R.drawable.ic_baseline_volume_up_24));
                    Toast.makeText(ShowBook.this, "Speech Stopped", Toast.LENGTH_SHORT).show();

                }else {

                    String toSpeak = textView.getText().toString().trim();

                    Toast.makeText(ShowBook.this, toSpeak, Toast.LENGTH_SHORT).show();
                    speech(toSpeak);

                    speech.setBackground(getResources().getDrawable(R.drawable.ic_baseline_volume_off_24));

                }
            }
        });

    }
    @Override
    protected void onPause() {
        if (mTTs != null || mTTs.isSpeaking()) {
            mTTs.stop();
        }
        super.onPause();
    }


    private void speech(String charSequence) {
        ///
        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(charSequence);
        /////
        int position=0 ;
        int sizeOfChar= charSequence.length();
        String testStri= charSequence.substring(position,sizeOfChar);
        while(reMatcher.find()) {
            String temp="";

            try {

                temp = testStri.substring(charSequence.lastIndexOf(reMatcher.group())
                        , charSequence.indexOf(reMatcher.group())+reMatcher.group().length());
                mTTs.speak(temp, TextToSpeech.QUEUE_ADD, null,"speak");


            } catch (Exception e) {
                temp = testStri.substring(0, testStri.length());
                mTTs.speak(temp, TextToSpeech.QUEUE_ADD, null);
                break;

            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.showbook_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.reset:

                Settings.reset(getApplicationContext());
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(this, "DEFAULT MODE", Toast.LENGTH_SHORT).show();

                return true;
              case R.id.matrix:

                  settings.setCustomTheme(Settings.MATRIX_THEME);
                  Toast.makeText(this, "MATRIX VIEW", Toast.LENGTH_SHORT).show();
                  SharedPreferences.Editor editor = getSharedPreferences(Settings.PREFERENCE,MODE_PRIVATE).edit();
                  editor.putString(Settings.CUSTOM_THEME,settings.getCustomTheme());
                  editor.apply();
                  updateView();

                return true;
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        }

        return super.onOptionsItemSelected(item);
    }

    private  void storeFont(int typeface){
        SharedPreferences sharedPreferences = getSharedPreferences(FONT_STYLE, Context.MODE_PRIVATE);
        fontEditor = sharedPreferences.edit();
        fontEditor.putInt(KEY_FONT, typeface);
        fontEditor.apply();
    }




    private void iniSwitchListener() {
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    settings.setCustomTheme(Settings.DARK_THEME);
                    Toast.makeText(getApplicationContext(), "DARK MODE", Toast.LENGTH_SHORT).show();

                }else {
                    settings.setCustomTheme(Settings.LIGHT_THEME);
                    Toast.makeText(getApplicationContext(), "NIGHT MODE", Toast.LENGTH_SHORT).show();

                }
                SharedPreferences.Editor editor = getSharedPreferences(Settings.PREFERENCE,MODE_PRIVATE).edit();
                editor.putString(Settings.CUSTOM_THEME,settings.getCustomTheme());
                editor.apply();
                updateView();

            }
        });
    }

    private void MatrixView(){
        final int black = ContextCompat.getColor(this,R.color.black);
        final int matrix_color = ContextCompat.getColor(this,R.color.matrix);
        settings.getCustomTheme().equals(Settings.MATRIX_THEME);

            textView.setTextColor(matrix_color);
            line.setBackgroundColor(matrix_color);
            line1.setBackgroundColor(matrix_color);
            textView1.setTextColor(matrix_color);
            textView2.setTextColor(matrix_color);
            title.setTextColor(matrix_color);
            scroll.setBackgroundColor(black);
            layout.setBackgroundColor(black);
            layout1.setBackgroundColor(black);

            textView.setTypeface(Typeface.create("sans-serif-smallcaps",Typeface.NORMAL));
            editor1.putInt(FAMILY_FONT,R.id.item8);
            editor1.apply();
            Toast.makeText(this, "matrix view", Toast.LENGTH_SHORT).show();





    }

    private void updateView() {
        final int night = ContextCompat.getColor(this,R.color.night);
        final int white = ContextCompat.getColor(this,R.color.light_mode);

        if(settings.getCustomTheme().equals(Settings.DARK_THEME)){
            textView.setTextColor(white);
            line.setBackgroundColor(white);
            line1.setBackgroundColor(white);
            textView1.setTextColor(white);
            textView2.setTextColor(white);
            title.setTextColor(white);
            scroll.setBackgroundColor(night);
            layout.setBackgroundColor(night);
            layout1.setBackgroundColor(night);

            themeSwitch.setChecked(true);
        }else if(settings.getCustomTheme().equals(Settings.LIGHT_THEME)){
            textView.setTextColor(night);
            line.setBackgroundColor(night);
            line1.setBackgroundColor(night);
            textView1.setTextColor(night);
            textView2.setTextColor(night);
            title.setTextColor(night);
            scroll.setBackgroundColor(white);
            layout.setBackgroundColor(white);
            layout1.setBackgroundColor(white);

            themeSwitch.setChecked(false);
        }else if(settings.getCustomTheme().equals(Settings.MATRIX_THEME)){

            final int black = ContextCompat.getColor(this,R.color.black);
            final int matrix_color = ContextCompat.getColor(this,R.color.matrix);


            textView.setTextColor(matrix_color);
            line.setBackgroundColor(matrix_color);
            line1.setBackgroundColor(matrix_color);
            textView1.setTextColor(matrix_color);
            textView2.setTextColor(matrix_color);
            title.setTextColor(matrix_color);
            scroll.setBackgroundColor(black);
            layout.setBackgroundColor(black);
            layout1.setBackgroundColor(black);

            textView.setTypeface(Typeface.create("sans-serif-smallcaps",Typeface.NORMAL));



        }
    }

    private void laodSharePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.PREFERENCE,MODE_PRIVATE);
        String theme = sharedPreferences.getString(Settings.CUSTOM_THEME,Settings.CUSTOM_THEME);
        settings.setCustomTheme(theme);
        updateView();
    }

    private void iniWidgets() {

        themeSwitch = findViewById(R.id.themeSwitch);
        textView = findViewById(R.id.text1);
        textView1 = findViewById(R.id.booktitle);
        textView2 = findViewById(R.id.authorName);
        line = findViewById(R.id.view);
        line1 = findViewById(R.id.view1);
        title = findViewById(R.id.bookdetails);
        layout = findViewById(R.id.relativelayout);
        layout1 = findViewById(R.id.relativelayout1);
        scroll = findViewById(R.id.scroll);
        speech = findViewById(R.id.speech);
        genre = findViewById(R.id.book_genre);
        storyImage = findViewById(R.id.storyImage);


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0,android.R.anim.fade_out);
    }
    public boolean onTo( MotionEvent event) {

        return true;
    }
    int getDistance(MotionEvent motionEvent){
        int dx = (int)(motionEvent.getX(0)-motionEvent.getX(1));
        int dy = (int)(motionEvent.getY(0)-motionEvent.getY(1));
        return (int)Math.sqrt(dx*dx + dy*dy);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount()==2){
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            if (pureaction== MotionEvent.ACTION_POINTER_DOWN){
                baseDistance = getDistance(event);
                baseRatio = ratio;
            }else{
                float delta = (getDistance(event)-baseDistance)/step;
                float multi = (float) Math.pow(2,delta);
                ratio = Math.min(1024.0f,Math.max(0.1f,multi*baseRatio));
                textView.setTextSize(ratio+13);


            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}

