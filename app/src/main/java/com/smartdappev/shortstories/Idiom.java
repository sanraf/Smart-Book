package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Idiom extends AppCompatActivity {

    private ListView listView;
    SimpleAdapter simpleAdapter;
    TextToSpeech mTTs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom);

        listView = (ListView) findViewById(R.id.listviewidiom);


        String[] idiomTittle = getResources().getStringArray(R.array.idiom_tittle);
        String[] idiomContent = getResources().getStringArray(R.array.idiom_content);





        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        for(int x = 0;x<idiomTittle.length;x++){
            HashMap<String,String>hashMap = new HashMap<String,String>();
            hashMap.put("idiom_tittle",idiomTittle[x]);
            hashMap.put("idiom_content",idiomContent[x]);
            aList.add(hashMap);
        }

        String[] from = {"idiom_tittle","idiom_content"};
        int[] to = {R.id.idiomTittleID,R.id.idiomContent};

        simpleAdapter = new SimpleAdapter(getBaseContext(),aList,R.layout.sample_idom,from,to);
        listView.setAdapter(simpleAdapter);

        mTTs = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status !=TextToSpeech.ERROR){
                    mTTs.setLanguage(Locale.US);

                }else{
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mTTs.isSpeaking()){
                    String textTittle = idiomTittle[position];
                    String textContenxt = idiomContent[position];
                    mTTs.speak(textTittle +".\n"+ textContenxt,TextToSpeech.QUEUE_FLUSH,null,null);
                    Toast.makeText(Idiom.this, textTittle+" "+textContenxt, Toast.LENGTH_SHORT).show();
                }else {
                    mTTs.stop();
                    Toast.makeText(Idiom.this, "Speech Paused", Toast.LENGTH_SHORT).show();

                }

            }
        });






    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search_mode);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search author or title");
        searchView.setBackgroundColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                simpleAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                simpleAdapter.getFilter().filter(newText);


                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        if (mTTs !=null){
            mTTs.stop();
            mTTs.shutdown();

        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mTTs.isSpeaking()){
            mTTs.stop();
        }
        overridePendingTransition(0,android.R.anim.fade_out);
        finish();
    }

}