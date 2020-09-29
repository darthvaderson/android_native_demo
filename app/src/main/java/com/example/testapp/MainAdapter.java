package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder>{

    public ArrayList<MainData> arrayList;
    public Context context;

    public MainAdapter(ArrayList<MainData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_concert,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.CustomViewHolder holder, int position) {

        holder.tv_id.setText(String.valueOf(arrayList.get(position).get_id()));
        holder.tv_title.setText(arrayList.get(position).getTv_title());
        holder.tv_start_date.setText(arrayList.get(position).getTv_start_date());
        holder.tv_end_date.setText(arrayList.get(position).getTv_end_date());
        holder.tv_genre.setText(arrayList.get(position).getTv_genre());
        holder.tv_place.setText(arrayList.get(position).getTv_place());
        holder.tv_theater.setText(arrayList.get(position).getTv_theater());

        Glide.with(context).load(arrayList.get(position).getIv_poster()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_poster);

        holder.itemView.setTag(position);


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_id;
        protected TextView tv_title;
        protected TextView tv_start_date;
        protected TextView tv_end_date;
        protected TextView tv_genre;
        protected TextView tv_place;
        protected TextView tv_theater;
        protected ImageView iv_poster;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_id = (TextView) itemView.findViewById((R.id.tv_id));
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_start_date = (TextView) itemView.findViewById(R.id.tv_start_date);
            this.tv_end_date = (TextView) itemView.findViewById(R.id.tv_end_date);
            this.tv_genre = (TextView) itemView.findViewById(R.id.tv_genre);
            this.tv_place = (TextView) itemView.findViewById(R.id.tv_place);
            this.tv_theater = (TextView) itemView.findViewById(R.id.tv_theater);
            this.iv_poster = (ImageView) itemView.findViewById(R.id.iv_poster);


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
