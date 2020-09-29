package com.example.testapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;


public class SearchActivity extends AppCompatActivity {
    //main(String[] args)
    EditText editText;
    Button button;
    String input_keywords = "";
    ArrayList<String> keywords = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<String> places = new ArrayList<>();

    ConcertDBManager mDBmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.ET_search);
        button = findViewById(R.id.button);
        tv_start_date = findViewById(R.id.tv_start_date);
        tv_end_date = findViewById(R.id.tv_end_date);


        mDBmanager = ConcertDBManager.getInstance(getApplicationContext());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_keywords = editText.getText().toString();



                if(input_keywords.length() < 2){

                    AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.setMessage("두 글자 이상 입력해주세요.");
                    alert.show();
                }
                else{
                    StringTokenizer st = new StringTokenizer(input_keywords);
                    while(st.hasMoreTokens()) {
                        keywords.add(st.nextToken());
                        Log.d("FFFFFFFFFFFFFFFFFF",String.valueOf(keywords.size()));
                    }


                    Intent intent = new Intent(getApplicationContext(), ConcertActivity.class);
                    intent.putStringArrayListExtra("keywords",keywords);

                    if(date_Validation()){
                        Log.d("date validation true", "date data put in");
                        dates.add(tv_start_date.getText().toString());
                        dates.add(tv_end_date.getText().toString());
                        intent.putStringArrayListExtra("dates",dates);
                        tv_start_date.setText("날짜선택");
                        tv_end_date.setText("날짜선택");
                    }
                    if(!genres.isEmpty()){
                        Log.d("Genres search data", "genre search keyword put in");
                        intent.putStringArrayListExtra("genres",genres);

                    }
                    if(!places.isEmpty()){
                        Log.d("Places search data", "place search keyword put in");
                        intent.putStringArrayListExtra("places",places);
                    }

                    startActivity(intent);
                    input_keywords = "";
                    keywords.clear();
                    dates.clear();

                }
            }
        });

    }

    public boolean date_Validation(){
        String start_date = tv_start_date.getText().toString();
        String end_date = tv_end_date.getText().toString();

        if(!start_date.equals("날짜선택")&&!end_date.equals("날짜선택")){
            return true;
        }
        else{
            return false;
        }

    }

    public void onGenreClick (View view)  {
        TextView textView = (TextView) view;

        if(textView.getCurrentTextColor() == getResources().getColor(R.color.colorDeepGrey)){
            textView.setTextColor(getResources().getColor(R.color.colorWhite));
            textView.setBackgroundColor(getResources().getColor(R.color.colorPurple));
            genres.add(textView.getText().toString());

        }
        else{
            textView.setTextColor(getResources().getColor(R.color.colorDeepGrey));
            textView.setBackgroundResource(R.drawable.scrollview1);
            genres.remove(textView.getText().toString());

        }

    }

    public void onPlaceClick(View view){
        TextView textView = (TextView) view;

        if(textView.getCurrentTextColor() == getResources().getColor(R.color.colorDeepGrey)){
            textView.setTextColor(getResources().getColor(R.color.colorWhite));
            textView.setBackgroundColor(getResources().getColor(R.color.colorPurple));
            places.add(textView.getText().toString().substring(0,2));

        }
        else{
            textView.setTextColor(getResources().getColor(R.color.colorDeepGrey));
            textView.setBackgroundResource(R.drawable.scrollview1);
            places.remove(textView.getText().toString().substring(0,2));

        }
    }

    private TextView tv_start_date;
    private TextView tv_end_date;


    public void onDateClick(View view){

        Date date = new Date();
        final SimpleDateFormat year = new SimpleDateFormat("yyyy");
        final SimpleDateFormat month = new SimpleDateFormat("MM");
        final SimpleDateFormat day = new SimpleDateFormat("dd");
        int cur_year = Integer.parseInt(year.format(date));
        int cur_month = Integer.parseInt(month.format(date));
        int cur_day = Integer.parseInt(day.format(date));

        final DecimalFormat d1 = new DecimalFormat("0000");
        final DecimalFormat d2 = new DecimalFormat("00");
        final DecimalFormat d3 = new DecimalFormat("00");

        switch(view.getId()){

            case R.id.tv_start_date:
            {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        String year = d1.format(i);
                        String month = d2.format(i1+1);
                        String day = d3.format(i2);
                        tv_start_date.setText(year+"-"+month+"-"+day);
                    }
                };

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(this,callback, cur_year, cur_month-1, cur_day);
                mDatePickerDialog.show();
                break;
            }
            case R.id.tv_end_date:
            {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        String year = d1.format(i);
                        String month = d2.format(i1+1);
                        String day = d3.format(i2);

                        tv_end_date.setText(year+"-"+month+"-"+day);
                    }
                };

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(this,callback, cur_year, cur_month-1, cur_day);
                mDatePickerDialog.show();
                break;
            }

        }
    }

}
