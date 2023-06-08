package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.snackbar.Snackbar;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PDFBooks extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {

   pl.droidsonroids.gif.GifImageView pdfViewProgressBar;
   ShimmerFrameLayout shimmerFrameLayout;
   TextView textMsg;
     PDFView pdfView;
     public static  final String Share_Pref ="sharePref";
     public static  final String CURRENT_PAGE ="currentPage";
    private RelativeLayout pdfHolder_Layout;
    private RelativeLayout network_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfbooks);
        pdfView = findViewById(R.id.pdfviewer);
        pdfViewProgressBar = findViewById(R.id.progressbar);
        shimmerFrameLayout = findViewById(R.id.bookShimmer);
        textMsg = findViewById(R.id.text1);
        pdfHolder_Layout = findViewById(R.id.pdfHolderLayout);
        network_layout = findViewById(R.id.network_layout);




        //pdfViewProgressBar.setVisibility(View.GONE);
        pdfView.showContextMenu();



        Intent intent=this.getIntent();
        final String path=intent.getExtras().getString("PATH");
        FileLoader.with(this)
                .load(path,false)
                .fromDirectory("MY_PDFs",FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        pdfViewProgressBar.setVisibility(View.GONE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textMsg.setVisibility(View.GONE);
                        pdfView.setVisibility(View.VISIBLE);
                        File pdfFile =response.getBody();
                        try {
                            pdfView.fromFile(pdfFile)
                                    .defaultPage(loadPage())
                                    .enableSwipe(true)
                                    .swipeHorizontal(true)
                                    .enableAnnotationRendering(true)
                                    .onLoad(PDFBooks.this)
                                    .scrollHandle(new DefaultScrollHandle(PDFBooks.this))
                                    .spacing(0)
                                    .enableDoubletap(true)
                                    .onPageError(PDFBooks.this)
                                    .load();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if(networkInfo !=null && networkInfo.isConnected()){

                            pdfViewProgressBar.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.GONE);
                            textMsg.setVisibility(View.GONE);
                            shimmerFrameLayout.stopShimmer();
                            Snackbar snackbar = Snackbar.make(pdfHolder_Layout,"SOMETHING WENT WRONG",Snackbar.LENGTH_INDEFINITE);
                            snackbar.setTextColor(getResources().getColor(R.color.lite_red));
                            snackbar.setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.show();

                        }else{
                            pdfViewProgressBar.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.GONE);
                            textMsg.setVisibility(View.GONE);
                            shimmerFrameLayout.stopShimmer();
                            network_layout.setVisibility(View.VISIBLE);
                        }


                    }

                });
    }



    public void savePage(){

        SharedPreferences sharedPreferences = getSharedPreferences(Share_Pref,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_PAGE,pdfView.getCurrentPage());
        editor.apply();
    }
    public int loadPage(){
        SharedPreferences sharedPreferences = getSharedPreferences(Share_Pref,MODE_PRIVATE);
        return sharedPreferences.getInt(CURRENT_PAGE,0);
    }

    @Override
    public void loadComplete(int nbPages) {
        pdfViewProgressBar.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.GONE);
        textMsg.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        //Toast.makeText(this, String.valueOf(nbPages)+" Pages", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPageError(int page, Throwable t) {
        pdfViewProgressBar.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.GONE);
        textMsg.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(this,FireBaseImage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        savePage();
        //Toast.makeText(this, Integer.toString(pdfView.getCurrentPage()), Toast.LENGTH_SHORT).show();
        finish();*/
        savePage();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


        super.onBackPressed();
    }
}