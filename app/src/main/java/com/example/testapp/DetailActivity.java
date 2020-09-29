package com.example.testapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    //main(String[] args)

    private ConcertDBManager mDBmanager;
    private SQLiteDatabase db;
    private ImageView iv_poster;
    private TextView tv_title;
    private TextView tv_genre;
    private TextView tv_place;
    private TextView tv_theater;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_playtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDBmanager = ConcertDBManager.getInstance(this);
        db = mDBmanager.getReadableDatabase();

        iv_poster = findViewById(R.id.iv_poster);
        tv_title = findViewById(R.id.tv_title);
        tv_genre = findViewById(R.id.tv_genre);
        tv_place = findViewById(R.id.tv_place);
        tv_theater = findViewById(R.id.tv_theater);
        tv_start_date = findViewById(R.id.tv_start_date);
        tv_end_date = findViewById(R.id.tv_end_date);
        tv_playtime = findViewById(R.id.tv_playtime);


        Intent intent = getIntent();
        int _id =  intent.getIntExtra("_ID",0);
        Log.d("PASSED _id VALUE", String.valueOf(_id));
        Log.d("PASSED _id VALUE", String.valueOf(_id));
        setDetail(_id);


    }

    protected void setDetail(int _id){


        Cursor cursor = db.query(ConcertDBManager.TABLE_CONCERT, new String[]{"img","name","genre","place","theater","start_date", "end_date", "playtime"},"_id = ?", new String[]{String.valueOf(_id)}, null, null, null);
        if(cursor!=null) {
            cursor.moveToNext();
            Glide.with(getApplicationContext()).load(cursor.getString(cursor.getColumnIndex("img"))).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_poster);
            tv_title.setText(cursor.getString(cursor.getColumnIndex("name")));
            tv_genre.setText(cursor.getString(cursor.getColumnIndex("genre")));
            tv_place.setText(cursor.getString(cursor.getColumnIndex("place")));
            tv_theater.setText(cursor.getString(cursor.getColumnIndex("theater")));
            tv_start_date.setText(cursor.getString(cursor.getColumnIndex("start_date")));
            tv_end_date.setText(cursor.getString(cursor.getColumnIndex("end_date")));
            tv_playtime.setText(cursor.getString(cursor.getColumnIndex("playtime")));
        }
        cursor.close();
    }
}
