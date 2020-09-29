package com.example.testapp;

public class MainData {

    private int _id;
    private String iv_poster;
    private String tv_title;
    private String tv_start_date;
    private String tv_end_date;
    private String tv_genre;
    private String tv_place;
    private String tv_theater;



    public MainData(int _id, String iv_poster, String tv_title, String tv_start_date, String tv_end_date, String tv_genre, String tv_place, String tv_theater) {
        this._id = _id;
        this.iv_poster = iv_poster;
        this.tv_title = tv_title;
        this.tv_start_date = tv_start_date;
        this.tv_end_date = tv_end_date;
        this.tv_genre = tv_genre;
        this.tv_place = tv_place;
        this.tv_theater = tv_theater;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getIv_poster() {
        return iv_poster;
    }

    public void setIv_poster(String iv_poster) {
        this.iv_poster = iv_poster;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String tv_title) {
        this.tv_title = tv_title;
    }

    public String getTv_start_date() {
        return tv_start_date;
    }

    public void setTv_start_date(String tv_start_date) {
        this.tv_start_date = tv_start_date;
    }

    public String getTv_end_date() {
        return tv_end_date;
    }

    public void setTv_end_date(String tv_end_date) {
        this.tv_end_date = tv_end_date;
    }

    public String getTv_genre() {
        return tv_genre;
    }

    public void setTv_genre(String tv_genre) {
        this.tv_genre = tv_genre;
    }

    public String getTv_place() {
        return tv_place;
    }

    public void setTv_place(String tv_place) {
        this.tv_place = tv_place;
    }

    public String getTv_theater() {
        return tv_theater;
    }

    public void setTv_theater(String tv_theater) {
        this.tv_theater = tv_theater;
    }
}
