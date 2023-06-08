package com.smartdappev.shortstories;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookAdapter extends FirebaseRecyclerAdapter<BookModel,BookAdapter.myViewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BookAdapter(@NonNull FirebaseRecyclerOptions<BookModel> options,Context context) {

        super(options);
        this.context=context;



    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull BookModel bookModel) {

        myViewHolder.title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        myViewHolder.title.setSelected(true);
        myViewHolder.author.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        myViewHolder.author.setSelected(true);

        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#c0c0c0"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#e7e7e7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();

        ShimmerDrawable shimmerDrawable =new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);


        Glide.with(myViewHolder.image.getContext())
                .load(bookModel.getImageuri())
                .placeholder(shimmerDrawable)
                .error(R.drawable.errorimage)
                .into(myViewHolder.image);

        myViewHolder.author.setText(bookModel.getAuthor());
        myViewHolder.title.setText(bookModel.getTitle());

        myViewHolder.holdColor.setCardBackgroundColor(myViewHolder
                .holdColor.getContext()
                .getResources().getColor(getRandomColor()));


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,BookDescription.class);
                intent.putExtra("bookTitle",bookModel.getTitle());
                intent.putExtra("bookDescription",bookModel.getDescription());
                intent.putExtra("imageURI",bookModel.getImageuri());
                intent.putExtra("bookURI",bookModel.getBookuri());
                intent.putExtra("bookAuthor",bookModel.getAuthor());
                context.startActivity(intent);
                //((Activity)context).finish();
                ((Activity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });


    }


    private int getRandomColor() {
        List<Integer> ColorCode = new ArrayList<>();
        ColorCode.add(R.color.A);

        ColorCode.add(R.color.C);
        ColorCode.add(R.color.D);
        ColorCode.add(R.color.H);
        ColorCode.add(R.color.K);
        ColorCode.add(R.color.M);
        ColorCode.add(R.color.N);
        ColorCode.add(R.color.L);
        ColorCode.add(R.color.khaki);
        ColorCode.add(R.color.E);
        ColorCode.add(R.color.F);
        ColorCode.add(R.color.J);
        ColorCode.add(R.color.brown);
        ColorCode.add(R.color.G);
        ColorCode.add(R.color.M);

        ColorCode.add(R.color.P);
        ColorCode.add(R.color.R);
        ColorCode.add(R.color.yellow);
        ColorCode.add(R.color.teal_200);

        ColorCode.add(R.color.T);

        Random randomColor = new Random();
        int ColorNumber = randomColor.nextInt(ColorCode.size());
        return ColorCode.get(ColorNumber);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_model,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView author;
        TextView title;
        TextView description;
        TextView bookuri;
        CardView holdColor;




        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            image =itemView.findViewById(R.id.imageview);
            author = itemView.findViewById(R.id.authorName);
            title = itemView.findViewById(R.id.booktitle);
            description = itemView.findViewById(R.id.description);
            //bookuri = itemView.findViewById(R.id.bookuri);
            holdColor = itemView.findViewById(R.id.holdcolor);



        }

    }

    @Override
    public void onDataChanged() {

        super.onDataChanged();
    }
}
