package com.unlimited.matajer.common.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unlimited.matajer.common.Moudules.CategoriesRespons.responce;
import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.Moudules.Remove_favorite.RemoveFavorite;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.Moudules.Favorites_by_user.Fav_Result;
import com.unlimited.matajer.common.WebServiceClint;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FaveViewHolder> {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Bitmap bitmap;
    SharedManeger sharedManeger;
    Context context;
    List<Fav_Result> arrayList;
    responce responce=new responce();

    public FavAdapter(Context context, List<Fav_Result> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favitem,parent,false);
        FaveViewHolder faveViewHolder=new FaveViewHolder(view);
        return faveViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FaveViewHolder holder, int position) {
        holder.mTextViewitem.setText(arrayList.get(position).getName());
        holder.mImageViewFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        try {
            Glide.with(context).load(arrayList.get(position).getImage()).into(holder.mImageViewitem);
        }catch (Exception e){
            Glide.with(context).load(R.drawable.sheep).into(holder.mImageViewitem);
        }

        holder.mImageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavourite = readStae();
                if (!isFavourite) {
                    holder.mImageViewFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);


                    //code image to string
                    holder.mImageViewFav.buildDrawingCache();
                    bitmap = holder.mImageViewFav.getDrawingCache();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] image = stream.toByteArray();
                    //System.out.println("byte array:"+image);
                    //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
                    //System.out.println("string:"+img_str);
                    String img_str = Base64.encodeToString(image, 0);
                    //decode string to image
                    String base = img_str;
                    byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                    holder.mImageViewFav.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                    );
                    //save in sharedpreferences
                    SharedPreferences preferences = context.getSharedPreferences("fav", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("favphoto", img_str);
                    editor.commit();


                    sharedManeger = new SharedManeger(context);
                    APIService apiService = WebServiceClint.getRetrofit().create(APIService.class);
                    Call<RemoveFavorite> call = apiService.getremovefav(sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID), arrayList.get(position).getId());
                    call.enqueue(new Callback<RemoveFavorite>() {
                        @Override
                        public void onResponse(Call<RemoveFavorite> call, Response<RemoveFavorite> response) {

                            Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<RemoveFavorite> call, Throwable t) {
                            Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    isFavourite = true;
                    saveStae(isFavourite);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class FaveViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewitem,mImageViewFav;
        TextView mTextViewitem;
        public FaveViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewFav=itemView.findViewById(R.id.faverecfav);
            mImageViewitem=itemView.findViewById(R.id.photorecfav);
            mTextViewitem=itemView.findViewById(R.id.namerecfav);
        }
    }


    private void saveStae(boolean isFavourite) {
        SharedPreferences aSharedPreferenes = context.getSharedPreferences(
                "Favourite", MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferenesEdit = aSharedPreferenes
                .edit();
        aSharedPreferenesEdit.putBoolean("State", isFavourite);
        aSharedPreferenesEdit.commit();
    }

    private boolean readStae() {
        SharedPreferences aSharedPreferenes = context.getSharedPreferences(
                "Favourite", MODE_PRIVATE);
        return aSharedPreferenes.getBoolean("State", true);
    }
}
