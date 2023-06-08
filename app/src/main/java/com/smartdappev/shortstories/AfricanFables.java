package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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

public class AfricanFables extends AppCompatActivity {
    private ListView listView;

    SimpleAdapter simpleAdapter;
    ArrayAdapter<String> adapter,adapterForAuthorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_african_fables);
        listView = (ListView) findViewById(R.id.listviewid2);


        String[] storyName = getResources().getStringArray(R.array.story_names1);
        final String [] story = getResources().getStringArray(R.array.stories1);
        String[] authorname = getResources().getStringArray(R.array.Author_Name1);
        Toolbar toolbar = findViewById(R.id.toolbar1);
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

        simpleAdapter = new SimpleAdapter(getBaseContext(),aList,R.layout.sample_view,from,to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String iss = story[i];
                String title = storyName[i];
                String author = authorname[i];
                Intent intent = new Intent(AfricanFables.this,ShowBook.class);
                intent.putExtra("detail",iss);
                intent.putExtra("title",title);
                intent.putExtra("author",author);
                String genre = "From African Fables";
                intent.putExtra("genre",genre);
                intent.putExtra("image",R.drawable.frican);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String iss = story[position];
                String title = storyName[position];
                String author = authorname[position];
                Intent intent = new Intent(AfricanFables.this,ShowBook.class);
                intent.putExtra("detail",iss);
                intent.putExtra("title",title);
                intent.putExtra("author",author);
                String genre = "From African Fables";
                intent.putExtra("genre",genre);
                intent.putExtra("image",R.drawable.frican);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                return false;
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
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}