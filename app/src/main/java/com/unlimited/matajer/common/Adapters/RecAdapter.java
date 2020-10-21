package com.unlimited.matajer.common.Adapters;

import android.content.Context;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;
import com.unlimited.matajer.Trying.DetailesActivity;
import com.unlimited.matajer.common.Moudules.Remove_favorite.RemoveFavorite;
import com.unlimited.matajer.common.Moudules.items.iremesresult;
import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.Moudules.Add_favorite.AddFavorite;
import com.unlimited.matajer.common.WebServiceClint;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.Recviewholder> {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Bitmap bitmap;
    SharedManeger sharedManeger;
    Intent intent;
Context context;
List<iremesresult> arrayList;
    iremesresult recModule=new iremesresult();


    public RecAdapter(Context context, List<iremesresult> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Recviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recitem,parent,false);
        Recviewholder recviewholder=new Recviewholder(view);
        return recviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Recviewholder holder, final int position) {
holder.mTextViewnamerec.setText(arrayList.get(position).getName());
holder.mTextViewpricerec.setText("3000");
if (arrayList.get(position).getHasFavorite()){
    holder.mImageViewfavrec.setImageResource(R.drawable.ic_favorite_black_24dp);

}
else {
    holder.mImageViewfavrec.setImageResource(R.drawable.ic_baseline_favorite_border_24);

}
       try {
           Picasso.get().load(arrayList.get(position).getImage()).into(holder.mImageViewrec);
       }catch (Exception e){

       }
       preferences =context.getSharedPreferences("fav",MODE_PRIVATE);
        editor = preferences.edit();
        editor.commit();
        preferences = context.getSharedPreferences("fav",MODE_PRIVATE);
        // If value for key not exist then return second param value - In this case "..."
        String img_str=preferences.getString("favphoto", "");
        if (!img_str.equals("")){
            //decode string to image
            String base=img_str;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            holder.mImageViewfavrec.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
            holder.mImageViewfavrec.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
        }

        holder.mImageViewfavrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavourite = readStae();

                if (isFavourite) {
                    arrayList.get(position).getHasFavorite();
                    holder.mImageViewfavrec.setImageResource(R.drawable.ic_favorite_black_24dp);




                    //code image to string
                    holder.mImageViewfavrec.buildDrawingCache();
                    bitmap = holder.mImageViewfavrec.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] image=stream.toByteArray();
                    //System.out.println("byte array:"+image);
                    //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
                    //System.out.println("string:"+img_str);
                    String img_str = Base64.encodeToString(image, 0);
                    //decode string to image
                    String base=img_str;
                    byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                    holder.mImageViewfavrec.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length)
                    );
                    //save in sharedpreferences
                    SharedPreferences preferences = context.getSharedPreferences("fav",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("favphoto",img_str);
                    editor.commit();





                    sharedManeger=new SharedManeger(context);
                    APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
                    Call<AddFavorite> call=apiService.getaddfav(sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID),arrayList.get(position).getId());
                    call.enqueue(new Callback<AddFavorite>() {
                        @Override
                        public void onResponse(Call<AddFavorite> call, Response<AddFavorite> response) {

                            Toast.makeText(context, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AddFavorite> call, Throwable t) {
                            Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    isFavourite = false;
                    saveStae(isFavourite);

                }
                else {
                    arrayList.get(position).getHasFavorite();
                    holder.mImageViewfavrec.setImageResource(R.drawable.ic_baseline_favorite_border_24);


                   //code image to string
                    holder.mImageViewfavrec.buildDrawingCache();
                    bitmap = holder.mImageViewfavrec.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] image=stream.toByteArray();
                    //System.out.println("byte array:"+image);
                    //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
                    //System.out.println("string:"+img_str);
                    String img_str = Base64.encodeToString(image, 0);
                    //decode string to image
                    String base=img_str;
                    byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                    holder.mImageViewfavrec.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length)
                    );
                    //save in sharedpreferences
                    SharedPreferences preferences = context.getSharedPreferences("fav",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("favphoto",img_str);
                    editor.commit();


                    sharedManeger=new SharedManeger(context);
                    APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
                    Call<RemoveFavorite> call=apiService.getremovefav(sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID),arrayList.get(position).getId());
                    call.enqueue(new Callback<RemoveFavorite>() {
                        @Override
                        public void onResponse(Call<RemoveFavorite> call, Response<RemoveFavorite> response) {

                            Toast.makeText(context, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<RemoveFavorite> call, Throwable t) {
                            Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    isFavourite = true;
                    saveStae(isFavourite);

                }



            }
        });



holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        recModule=arrayList.get(position);
        Intent intent=new Intent(context, DetailesActivity.class);
        intent.putExtra("name",recModule.getName());
        intent.putExtra("price",3000);
        context.startActivity(intent);
        Intent intent1=new Intent(context,DetailesActivity.class);
        intent1.putExtra("id",arrayList.get(position).getId());
        context.startActivity(intent1);
    }
});
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Recviewholder extends RecyclerView.ViewHolder {
    ImageView mImageViewrec,mImageViewfavrec;
    TextView mTextViewpricerec,mTextViewnamerec;
        public Recviewholder(@NonNull View itemView) {
        super(itemView);
        mImageViewfavrec=itemView.findViewById(R.id.faverec);
        mImageViewrec=itemView.findViewById(R.id.photorec);
        mTextViewnamerec=itemView.findViewById(R.id.namerec);
        mTextViewpricerec=itemView.findViewById(R.id.pricerec);
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
