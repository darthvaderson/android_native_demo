package com.example.testapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ConcertActivity extends AppCompatActivity {
    //main(String[] args)
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> keywords;
    private ArrayList<String> dates;
    private ArrayList<String> genres;
    private ArrayList<String> places;
    private ConcertDBManager mDBmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert);

        Intent intent = getIntent();
        keywords = intent.getExtras().getStringArrayList("keywords");
        dates = intent.getExtras().getStringArrayList("dates");
        genres = intent.getExtras().getStringArrayList("genres");
        places = intent.getExtras().getStringArrayList("places");

        mDBmanager = ConcertDBManager.getInstance(getApplicationContext());

        Cursor cursor = makeQuery();

        arrayList = new ArrayList<>();

        setArrayList(cursor);

        recyclerView = findViewById(R.id.rv);

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter = new MainAdapter(arrayList, getApplicationContext());

        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MainData mainData = arrayList.get(position);
                int _id = mainData.get_id();

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

                intent.putExtra("_ID",_id);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mainAdapter);


    }

    public Cursor makeQuery(){

        String SQL = "select * from "+ConcertDBManager.TABLE_CONCERT+" where ";



        Log.d("dates null","dates nulllllllllllllllllll");
        for (int i = 0; i < keywords.size(); i++) {
            String temp = "search like " + "'%" + keywords.get(i) + "%'";
            SQL += temp;
            if (i == keywords.size() - 1) continue;
            SQL += " and ";
        }

        if(dates != null){
            Log.d("dates not null","dates not null therefore reflected in query");

            SQL += " and ";

            SQL += "(start_date between '"+dates.get(0)+"' and '"+dates.get(1)+"' or ";
            SQL += "end_date between '"+dates.get(0)+"' and '"+dates.get(1)+"')";
        }
        if(genres != null){
            Log.d("genres not null","genres not null therefore reflected in query");

            SQL += " and (";

            for(int i = 0 ; i < genres.size() ; i++){
                String temp = "genre like " + "'%"+genres.get(i) + "%'";
                SQL += temp;
                if(i == genres.size() -1) continue;
                SQL += " or ";
            }
            SQL += ")";
        }
        if(places != null){
            Log.d("places not null","places not null therefore reflected in query");

            SQL += " and (";

            for(int i = 0 ; i <places.size() ; i++){
                String temp = "place like " + "'%" + places.get(i) + "%'";
                SQL += temp;
                if(i == places.size() -1) continue;
                SQL += " or ";
            }
            SQL += ")";
        }

        Log.d("make search query", SQL);
        Log.d("make search query", SQL);

        Cursor cursor = mDBmanager.getReadableDatabase().rawQuery(SQL,null);

        return cursor;
    }

    public void setArrayList(Cursor cursor){

        if(cursor!=null) {
            while (cursor.moveToNext()) {

                int _id;
                String tv_title;
                String iv_poster;
                String tv_start_date;
                String tv_end_date;
                String tv_genre;
                String tv_place;
                String tv_theater;

                _id = cursor.getInt(cursor.getColumnIndex("_id"));
                tv_title = cursor.getString(cursor.getColumnIndex("name"));
                iv_poster = cursor.getString(cursor.getColumnIndex("img"));
                tv_start_date = cursor.getString(cursor.getColumnIndex("start_date"));
                tv_end_date = cursor.getString(cursor.getColumnIndex("end_date"));
                tv_genre = cursor.getString(cursor.getColumnIndex("genre"));
                tv_place = cursor.getString(cursor.getColumnIndex("place"));
                tv_theater = cursor.getString(cursor.getColumnIndex("theater"));

                Log.d("setArrayList ID print",String.valueOf(_id));
                arrayList.add(new MainData(_id, iv_poster, tv_title, tv_start_date, tv_end_date, tv_genre, tv_place, tv_theater));

            }
            cursor.close();
        }
    }

}
