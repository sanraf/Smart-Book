package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class BookDescription extends AppCompatActivity {
TextView bookTitle,bookDescription,author;
ImageView bookImage;
Button readButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        bookTitle = findViewById(R.id.Booktitle);
        bookDescription = findViewById(R.id.description);
        author = findViewById(R.id.author);
        bookImage = findViewById(R.id.bookimage);
        readButton = findViewById(R.id.readBook);

        Intent intent=this.getIntent();
        bookTitle.setText(getIntent().getExtras().getString("bookTitle"));
        final String bookAuthor = intent.getExtras().getString("bookAuthor");
        author.setText("By "+bookAuthor);
        bookDescription.setText(getIntent().getExtras().getString("bookDescription"));


        final String imageURI = intent.getExtras().getString("imageURI");
        final String bookURI = intent.getExtras().getString("bookURI");

        Glide.with(bookImage.getContext())
                .load(imageURI)
                .placeholder(R.drawable.errorimage)
                .error(R.drawable.errorimage)
                .into(bookImage);

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(c, pdf.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BookDescription.this,PDFBooks.class);
                intent.putExtra("PATH",bookURI);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                //finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();


    }
}