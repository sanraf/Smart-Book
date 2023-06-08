package com.smartdappev.shortstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FireBaseImage extends AppCompatActivity {
    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    pl.droidsonroids.gif.GifImageView progressBar;
    TextView connectText;
    ShimmerFrameLayout shimmerFrameLayout;
    androidx.core.widget.NestedScrollView nestedScrollView;
    androidx.coordinatorlayout.widget.CoordinatorLayout  linearLayout;
    Button reload;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<BookModel> options;
    //SearchView searchText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_image);


        recyclerView = findViewById(R.id.recycler);

        reload = findViewById(R.id.refresh);
        connectText = findViewById(R.id.connectText);
        nestedScrollView = findViewById(R.id.nested);
        linearLayout = findViewById(R.id.linearLayout);
        progressBar = findViewById(R.id.progressbar);
        shimmerFrameLayout = findViewById(R.id.shimmer);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("book");
        databaseReference.keepSynced(true);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.GONE);
                    connectText.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }else {
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    connectText.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);

                }
                bookAdapter.startListening();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(FireBaseImage.this, "Cancelled", Toast.LENGTH_SHORT).show();

            }

        });


        options =new FirebaseRecyclerOptions.Builder<BookModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("book"),BookModel.class)
                .build();

        bookAdapter = new BookAdapter(options,this);
        recyclerView.setAdapter(bookAdapter);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        loadInterstitialAd();



    }

    public void loadInterstitialAd(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                //Toast.makeText(FireBaseImage.this, "fail to load ad", Toast.LENGTH_SHORT).show();
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                interstitialAd.show(FireBaseImage.this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        //Toast.makeText(FireBaseImage.this, "clicked", Toast.LENGTH_SHORT).show();
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        //Toast.makeText(FireBaseImage.this, "dismissed", Toast.LENGTH_SHORT).show();
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        //Toast.makeText(FireBaseImage.this, "fail to show fullscreen", Toast.LENGTH_SHORT).show();
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        //Toast.makeText(FireBaseImage.this, "impression", Toast.LENGTH_SHORT).show();
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        //Toast.makeText(FireBaseImage.this, "fon fullscreen", Toast.LENGTH_SHORT).show();
                        super.onAdShowedFullScreenContent();
                    }
                });
                super.onAdLoaded(interstitialAd);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


   @Override
    protected void onStart() {
        super.onStart();
       bookAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        bookAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search_mode);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Book Author");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textSearch(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void textSearch(String searchText){
        FirebaseRecyclerOptions<BookModel> options =
                new FirebaseRecyclerOptions.Builder<BookModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("book")
                        .orderByChild("author").startAt(searchText).endAt(searchText+"\uf8ff"),BookModel.class)
                        .build();
        bookAdapter = new BookAdapter(options,this);
        bookAdapter.startListening();

        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();

//tools:listitem="@layout/pdf_model"
    }
}//PDF id ca-app-pub-7881857902310901/3520065039
//<a href="https://www.freepik.com/free-vector/african-savannah-landscape-wild-nature-africa_21376678.htm#page=3&query=africa&position=17&from_view=search">Image by upklyak</a> on Freepik
//<a href="https://www.freepik.com/free-vector/ethnic-people-african-tribes-wild-animal_9721341.htm#page=2&query=african%20map%20lion%20human&position=21&from_view=search&track=ais">Image by brgfx</a> on Freepik
//<a href="https://www.freepik.com/free-photo/blank-catalog-magazines-book-mock-up-blue-background_1255169.htm#page=5&query=book%20background&position=4&from_view=search">Image by jannoon028</a> on Freepik
//<a href="https://www.freepik.com/free-photo/3d-halloween-background-with-zombie-hand-bursting-out-ground_17308320.htm#query=horror%20background&position=2&from_view=search">Image by kjpargeter</a> on Freepik
//Image by <a href="https://www.freepik.com/free-vector/flat-hand-drawn-hygge-lifestyle-illustration_12301251.htm#page=2&query=book%20reading%20illustrastion&position=31&from_view=search&track=ais">Freepik</a>
//<a href="https://www.freepik.com/free-vector/house-bookshelves-concept-illustration_13429940.htm#page=4&query=book%20reading%20illustration&position=29&from_view=search">Image by storyset</a> on Freepik
//<a href="https://www.freepik.com/free-vector/study-online-during-coronavirus_24382772.htm#query=book%20and%20sunsat%20reading%20illustrationand&position=23&from_view=search&track=ais">Image by rawpixel.com</a> on Freepik
//<a href="https://www.freepik.com/free-vector/autumn-watercolor-background-with-leaves_9221810.htm?query=dry%20leaves&collectionId=1698&page=2&position=29&from_view=collections">Image by pikisuperstar</a> on Freepik
//<a href="https://www.freepik.com/free-vector/offline-concept-illustration_18352146.htm#query=lost%20internet%20connection&position=4&from_view=search&track=sph">Image by storyset</a> on Freepik
//<a href="https://www.freepik.com/free-photo/close-up-studio-shot-fresh-green-basil-herb-leaves-isolated-white-background-sweet-genovese-basil_1187514.htm#query=leaf&position=20&from_view=keyword">Image by dashu83</a> on Freepik
//<a href="https://www.freepik.com/free-vector/elephant-office-with-working-businesspeople_32069087.htm#query=idiom&position=15&from_view=search&track=sph">Image by upklyak</a> on Freepik
//<a href="https://www.freepik.com/free-photo/stack-books-black-wooden-table_21708612.htm#query=school%20books%20library%20bookshelf&position=44&from_view=search&track=sph">Image by fabrikasimf</a> on Freepik
//Image by <a href="https://www.freepik.com/free-photo/halloween-wallpaper-with-cemetery-night_19055598.htm#page=2&query=horror%20background&position=10&from_view=search&track=sph">Freepik</a>
//<a href="https://www.freepik.com/free-vector/isometric-icon-with-colorful-books-white-background-vector-illustration_23580825.htm#query=blue%20book%20background&position=33&from_view=search&track=sph">Image by macrovector</a> on Freepik
//<a href="https://www.freepik.com/free-vector/sunset-ocean-red-sky-with-sun-going-down-sea-surrounded-with-mountains-beautiful-nature-scenic-landscape-background-evening-heaven-view-gulls-flying-water-cartoon-vector-illustration_17995521.htm#query=orange%20sky%20sunset%20ocean%20wave&position=41&from_view=search&track=sph">Image by upklyak</a> on Freepik