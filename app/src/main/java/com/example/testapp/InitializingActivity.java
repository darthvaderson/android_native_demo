package com.example.testapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InitializingActivity extends AppCompatActivity {
    //main(String[] args)

    private ConcertDBManager mDBmanager;
    private String url = "http://192.168.35.40:81/total_data.php";
    private String mJSONstring;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);
        tv = findViewById(R.id.tv);
        mDBmanager = ConcertDBManager.getInstance(this);
        mDBmanager.resetTable(mDBmanager.getWritableDatabase());

        GetDB task = new GetDB();
        task.execute(url,"");

    }

    private class GetDB extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            tv.setText("DB update on process...\n Please wait");

        }
        protected String doInBackground(String... Params){

            try {
                URL server = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) server.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                // Log.d("BurstIntoTears", urlConnection.getResponseMessage());

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuffer sb = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    urlConnection.disconnect();
                    return sb.toString();
                }
                return null;
            } catch (Exception e) {
                Log.e("HTTP", "Exception in retrieving data from server", e);
                e.getMessage();
                return null;
            }


        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(result == null){
                Log.e("HTTP", "No data gotten");
                tv.setText("Data Null Exception occured");
            }

            else {
                mJSONstring = result;
                Log.d("JSON STRING LENGTH",String.valueOf(mJSONstring.length()));
                setDBdata();

                tv.setText("DB update completed");

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        }
    }
    private void setDBdata(){

        String TAG_JSON = "hong";
        String TAG_NAME = "name";
        String TAG_IMG = "img";
        String TAG_START_DATE = "start";
        String TAG_END_DATE = "end";
        String TAG_TIME = "time";
        String TAG_PLACE = "place";
        String TAG_THEATER = "theater";
        String TAG_PLAYTIME = "playtime";
        String TAG_GENRE = "genre";
        String TAG_PRICE = "price";
        String TAG_SEARCH = "search";

        ContentValues[] contentValues;


        try{
            JSONObject jsonObject = new JSONObject(mJSONstring);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            contentValues = new ContentValues[jsonArray.length()];

            for(int i = 0 ; i < jsonArray.length() ; i++){
                ContentValues cv = new ContentValues();
                JSONObject item  = jsonArray.getJSONObject(i);

                cv.put(TAG_NAME, item.getString(TAG_NAME));
                cv.put(TAG_IMG, item.getString(TAG_IMG));
                cv.put("start_date", item.getString(TAG_START_DATE));
                cv.put("end_date", item.getString(TAG_END_DATE));
                cv.put(TAG_TIME, item.getString(TAG_TIME));
                cv.put(TAG_PLACE, item.getString(TAG_PLACE));
                cv.put(TAG_THEATER, item.getString(TAG_THEATER));
                cv.put(TAG_PLAYTIME, item.getString(TAG_PLAYTIME));
                cv.put(TAG_GENRE, item.getString(TAG_GENRE));
                cv.put(TAG_PRICE, item.getString(TAG_PRICE));
                cv.put(TAG_SEARCH, item.getString(TAG_SEARCH));

                contentValues[i] = cv;

            }
            mDBmanager.insertAll(contentValues);
        }
        catch(JSONException e){
            Log.d("JSONException", e.getMessage());

        }
    }

}
