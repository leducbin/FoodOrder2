package com.ldb.bin.foodorder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ldb.bin.foodorder.R;

import com.ldb.bin.foodorder.Model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> dataShops;

    public RecyclerViewAdapter(Context context,ArrayList<Category> dataShops) {
        this.context = context;
        this.dataShops = dataShops;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.menu_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.imgHinh.getContext()).load(dataShops.get(position).getImage()).into(holder.imgHinh);
        holder.txtViewName.setText(dataShops.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataShops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinh;
        TextView txtViewName,txtViewPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            imgHinh = (ImageView) itemView.findViewById(R.id.menu_image);
            txtViewName = (TextView) itemView.findViewById(R.id.menu_name);
        }
    }
}