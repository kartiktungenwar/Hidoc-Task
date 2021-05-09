package com.hidoc_task.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hidoc_task.R;
import com.hidoc_task.model.Item;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private static int VIEW_TYPE = 0;
    private MainActivity activity;
    private ArrayList<Item> articles;

    public BooksAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public void setListArray(ArrayList<Item> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (VIEW_TYPE == 0)
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_news_even, parent, false);
        else
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_news_odd, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        onViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            VIEW_TYPE = 0;
        else
            VIEW_TYPE = 1;

        return VIEW_TYPE;
    }

    private void onViewHolder(MyViewHolder holder, int position) {
        holder.txtNewsAuthor.setText(articles.get(position).getVolumeInfo().getTitle());
        holder.txtNewsTitle.setText(articles.get(position).getVolumeInfo().getDescription());
        holder.txtNewsDate.setText(articles.get(position).getSaleInfo().getSaleability());
        if(!articles.get(position).getSaleInfo().getSaleability().equalsIgnoreCase("free")){
            try {
                holder.txtNewsDate.setText("At " + articles.get(position).getSaleInfo().getListPrice().getCurrencyCode() + " " + articles.get(position).getSaleInfo().getListPrice().getAmount() + "/-");
            }catch (NullPointerException e){
                try {
                    holder.txtNewsDate.setText("At INR" + String.valueOf(articles.get(position).getSaleInfo().getListPrice().getAmount()) + "/-");
                } catch (NullPointerException ex){
                    holder.txtNewsDate.setText("At INR --.--/-");
                }
            }
            if(articles.get(position).getSaleInfo().getSaleability().equalsIgnoreCase("NOT_FOR_SALE")){
                holder.txtNewsDate.setText("NOT FOR SALE");
            }
        }
        try {
        Glide.with(activity)
                .load(articles.get(position).getVolumeInfo().getImageLinks().getSmallThumbnail())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imNewsImage);
        } catch (NullPointerException ex) {
            holder.imNewsImage.setImageResource(R.mipmap.ic_launcher);
        }

        holder.txtNewsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri webpage = Uri.parse(articles.get(position).getSaleInfo().getBuyLink());
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, webpage));
                }catch (ActivityNotFoundException|NullPointerException e){
                    Toasty.error(activity,"This is not a valid link").show();
                }
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNewsAuthor, txtNewsTitle, txtNewsDate;
        public ImageView imNewsImage;
        public LinearLayout txtNewsLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNewsLinearLayout = itemView.findViewById(R.id.txtNewsLinearLayout);
            txtNewsAuthor = itemView.findViewById(R.id.txtNewsAuthor);
            txtNewsTitle = itemView.findViewById(R.id.txtNewsTitle);
            txtNewsDate = itemView.findViewById(R.id.txtNewsDate);
            imNewsImage = itemView.findViewById(R.id.imNewsImage);
        }
    }
}
