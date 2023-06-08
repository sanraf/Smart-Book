package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adult extends AppCompatActivity {
    private ListView listView;
    SimpleAdapter simpleAdapter;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adult);

        listView = (ListView) findViewById(R.id.listviewid6);
        String[] storyName = getResources().getStringArray(R.array.story_names5);
        final String [] story = getResources().getStringArray(R.array.stories5);
        String[] authorname = getResources().getStringArray(R.array.Author_Name5);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        for(int x = 0;x<storyName.length;x++){
            HashMap<String,String>hashMap = new HashMap<String,String>();
            hashMap.put("story_names",storyName[x]);
            hashMap.put("author_names",authorname[x]);
            aList.add(hashMap);
        }

        String[] from = {"story_names","author_names"};
        int[] to = {R.id.textviewId,R.id.name};

       simpleAdapter = new SimpleAdapter(getBaseContext(),aList,R.layout.sample_adult,from,to);
        listView.setAdapter(simpleAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String iss = story[i];
                String title = storyName[i];
                String author = authorname[i];
                Intent intent = new Intent(Adult.this,ShowBook.class);
                intent.putExtra("detail",iss);
                intent.putExtra("title",title);
                intent.putExtra("author",author);
                String genre = "From Adults Fiction";
                intent.putExtra("genre",genre);
                intent.putExtra("image",R.drawable.adult);
                startActivity(intent);
                overridePendingTransition(0,android.R.anim.fade_out);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //int h = ((ColorDrawable) toolbar.getBackground()).getColor();
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search_mode);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search author or title");
        searchView.setBackgroundColor(getResources().getColor(R.color.adult));
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