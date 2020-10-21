package com.unlimited.matajer.common.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unlimited.matajer.common.Moudules.CategoriesRespons.responce;
import com.unlimited.matajer.R;
import com.unlimited.matajer.Trying.RecActivity;


import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.homrviewholder> {
Context context;
List<responce> arrayList;
responce responce=new responce();

    public HomeAdapter(Context context, List<responce> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public homrviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        homrviewholder homrviewholder=new homrviewholder(view);
        return homrviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final homrviewholder holder, final int position) {
holder.mTextViewitem.setText(arrayList.get(position).getName());
try {
    Glide.with(context).load(arrayList.get(position).getImage()).into(holder.mImageViewitem);
}catch (Exception e){
    Glide.with(context).load(R.drawable.sheep).into(holder.mImageViewitem);
}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

responce=arrayList.get(position);
        Intent intent=new Intent(context, RecActivity.class);
        intent.putExtra("name",responce.getName());
        intent.putExtra("id",responce.getId());
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class  homrviewholder extends RecyclerView.ViewHolder {
        ImageView mImageViewitem;
        TextView mTextViewitem;
        public homrviewholder(@NonNull View itemView) {
            super(itemView);
         mImageViewitem=itemView.findViewById(R.id.imageitem);
         mTextViewitem=itemView.findViewById(R.id.nameitem);
        }
    }
}
