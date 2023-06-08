package com.smartdappev.shortstories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PDFHolder extends AppCompatActivity {

    public class PDFDoc{
        int id;
        String name;
        String category;
        String pdfURL;
        String pdfIconURL;

        public String getAuthor() {
            return category;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        String author;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPdfURL() {
            return pdfURL;
        }

        public void setPdfURL(String pdfURL) {
            this.pdfURL = pdfURL;
        }

        public String getPdfIconURL() {
            return pdfIconURL;
        }

        public void setPdfIconURL(String pdfIconURL) {
            this.pdfIconURL = pdfIconURL;
        }

    }


    public class ListViewAdapter extends BaseAdapter{
        Context c;
        ArrayList<PDFDoc> pdfDocuments;
        public ListViewAdapter(Context c , ArrayList<PDFDoc>pdfDocuments){
            this.c=c;
            this.pdfDocuments= pdfDocuments;
        }

        @Override
        public int getCount() {
            return pdfDocuments.size();
        }

        @Override
        public Object getItem(int position) {
            return pdfDocuments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView= LayoutInflater.from(c).inflate(R.layout.pdf_model,parent,false);
            }
            //TextView textName =convertView.findViewById(R.id.pdfname);
            TextView textAuthor = convertView.findViewById(R.id.authorName);
            ImageView pdfIcon = convertView.findViewById(R.id.imageview);
            CardView holdColor = convertView.findViewById(R.id.holdcolor);
            Button description = convertView.findViewById(R.id.description);

            final  PDFDoc pdf= (PDFDoc) this.getItem(position);
            //textName.setText(pdf.getName());
            textAuthor.setText(pdf.getAuthor());
             holdColor.setCardBackgroundColor(getResources().getColor(getRandomColor()));
            description.setBackgroundColor(getResources().getColor(getRandomColor()));

            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PDFHolder.this, "Sorry Still Under Development", Toast.LENGTH_LONG).show();
                }
            });

            if(pdf.getPdfURL() !=null && pdf.getPdfURL().length()>0){
                Picasso.get().load(pdf.getPdfIconURL()).placeholder(R.drawable.b).into(pdfIcon);
            }else{
                Toast.makeText(c, "Empty Image URL", Toast.LENGTH_SHORT).show();
                Picasso.get().load(R.drawable.b).into(pdfIcon);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, pdf.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(c ,PDFBooks.class);
                    intent.putExtra("PATH",pdf.getPdfURL());
                    c.startActivity(intent);
                }
            });
            return convertView;
        }

    }

    public class JSONDownloader{
        private static final  String PDF_SITE_URL="http://172.20.102.190/bookapp";
        private final  Context c;
        private ListViewAdapter adapter;//http://127.0.0.1//////

        public JSONDownloader(Context c) {
            this.c = c;
        }

        public void retrieve(final ListView gridView, final ProgressBar progressBar){
            final ArrayList<PDFDoc> pdfDocuments = new ArrayList<>();

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            AndroidNetworking.get(PDF_SITE_URL)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jsonObject;
                            PDFDoc pdfDoc;
                            try {
                                for(int i = 0;i<response.length();i++){
                                    jsonObject=response.getJSONObject(i);
                                    int id =jsonObject.getInt("id");
                                    String name = jsonObject.getString("name");
                                    String category = jsonObject.getString("category");
                                    String pdfURL=jsonObject.getString("pdf_uri");
                                    String pdfIconURL=jsonObject.getString("pdf_icon_uri");

                                    pdfDoc= new PDFDoc();
                                    pdfDoc.setId(id);
                                    pdfDoc.setName(name);
                                    pdfDoc.setCategory(category);
                                    pdfDoc.setPdfURL(PDF_SITE_URL+pdfURL);
                                    pdfDoc.setPdfIconURL(PDF_SITE_URL+pdfIconURL);

                                    pdfDocuments.add(pdfDoc);
                                }
                                adapter = new ListViewAdapter(c,pdfDocuments);
                                gridView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }catch (JSONException e){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(c, "Good response but java can't parse JSON it received"+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(c, anError.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfholder);
        final ListView gridView=findViewById(R.id.mygridview);
        final ProgressBar progressBar= findViewById(R.id.myprogressbar);
        Button reload = findViewById(R.id.refresh);




        new JSONDownloader(PDFHolder.this).retrieve(gridView,progressBar);

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new JSONDownloader(PDFHolder.this).retrieve(gridView,progressBar);
            }
        });


    }

    private int getRandomColor() {
        List<Integer> ColorCode = new ArrayList<>();
        ColorCode.add(R.color.A);
        ColorCode.add(R.color.B);
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
        ColorCode.add(R.color.Bluer);
        ColorCode.add(R.color.P);
        ColorCode.add(R.color.R);
        ColorCode.add(R.color.yellow);
        ColorCode.add(R.color.teal_200);
        ColorCode.add(R.color.S);
        ColorCode.add(R.color.T);

        Random randomColor = new Random();
        int ColorNumber = randomColor.nextInt(ColorCode.size());
        return ColorCode.get(ColorNumber);
    }
}
/*<GridView
        android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_weight="0.5"
                android:numColumns="auto_fit"
                android:id="@+id/mygridview"/>*/