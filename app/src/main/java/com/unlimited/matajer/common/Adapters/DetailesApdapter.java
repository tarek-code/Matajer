package com.unlimited.matajer.common.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.Moudules.itemDetails.ResultResponse;

import java.util.List;

public class DetailesApdapter extends RecyclerView.Adapter<DetailesApdapter.Detailesviewholder> {
List<ResultResponse> arrayList;
Context context;

    public DetailesApdapter(List<ResultResponse> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Detailesviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detailes_item,parent,false);
        Detailesviewholder detailesviewholder=new Detailesviewholder(view);
        return detailesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Detailesviewholder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getImages()).into(holder.mSliderView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Detailesviewholder extends RecyclerView.ViewHolder {
ImageView mSliderView;
        public Detailesviewholder(@NonNull View itemView) {
        super(itemView);
        mSliderView=itemView.findViewById(R.id.imageSlider);
    }
}
}
