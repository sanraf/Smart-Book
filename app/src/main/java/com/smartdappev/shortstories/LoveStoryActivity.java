package com.smartdappev.shortstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 public class LoveStoryActivity extends AppCompatActivity {
     private static final String TAG = "LoveStory";
     private ListView listView;
     ArrayAdapter<String> adapterForAuthorName;
     SimpleAdapter simpleAdapter;
     private AssetManager assetManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_story);

        assetManager = getAssets();

        listView = (ListView) findViewById(R.id.listviewid1);
        String[] storyName = getResources().getStringArray(R.array.story_names);
        final String [] story = getResources().getStringArray(R.array.stories);
        String[] authorname = getResources().getStringArray(R.array.Author_Name);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        try{
            String [] imagePath = assetManager.list("loveimages");

            for(int i =0;i< imagePath.length; i++){
                InputStream inputStream = assetManager.open("loveimages/"+imagePath[i]);
                Log.d(TAG,imagePath[i]);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            }
        }catch (IOException e){
            Log.e(TAG,e.getMessage());
        }


       List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        for(int x = 0;x<storyName.length;x++){
            HashMap<String,String>hashMap = new HashMap<String,String>();
            hashMap.put("story_names",storyName[x]);
            hashMap.put("author_names",authorname[x]);
            aList.add(hashMap);
        }

        String[] from = {"story_names","author_names"};
        int[] to = {R.id.textviewId,R.id.name};

         simpleAdapter = new SimpleAdapter(getBaseContext(),aList,R.layout.sample_view,from,to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String iss = story[i];
                String title = storyName[i];
                String author = authorname[i];
                String genre = "From Love Stories";
                Intent intent = new Intent(LoveStoryActivity.this,ShowBook.class);
                intent.putExtra("detail",iss);
                intent.putExtra("title",title);
                intent.putExtra("author",author);
                intent.putExtra("genre",genre);
                intent.putExtra("image",R.drawable.love);
                startActivity(intent);
                overridePendingTransition(0,android.R.anim.fade_out);

            }
        });

    }
    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search_mode);
         SearchView searchView = (SearchView) menuItem.getActionView();
         searchView.setQueryHint("Search author or title");
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {

                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 simpleAdapter.getFilter().filter(newText.toLowerCase());


                 return false;
             }
         });

         return super.onCreateOptionsMenu(menu);
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         finish();
         overridePendingTransition(0,android.R.anim.fade_out);

     }
 }