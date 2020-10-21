package com.unlimited.matajer.common;

import android.widget.ImageView;

import com.unlimited.matajer.common.Moudules.Authresponse.Authresponse;
import com.unlimited.matajer.common.Moudules.Favorites_by_user.FavoritesByUser;
import com.unlimited.matajer.common.Moudules.items.Items;
import com.unlimited.matajer.common.Moudules.CategoriesRespons.CategoriesRespons;
import com.unlimited.matajer.common.Moudules.EditProfile.EditProfile;
import com.unlimited.matajer.common.Moudules.UploadImage.UploadImage;
import com.unlimited.matajer.common.Moudules.Add_favorite.AddFavorite;
import com.unlimited.matajer.common.Moudules.itemDetails.ItemDetails;
import com.unlimited.matajer.common.Moudules.Remove_favorite.RemoveFavorite;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @POST("register")
    @FormUrlEncoded
    Call<Authresponse> register(@Field("first_name")String first_name,
                                @Field("last_name")String last_name,
                                @Field("phone")String phone,
                                @Field("email")String email,
                                @Field("password")String password);

    @POST("login")
    @FormUrlEncoded
    Call<Authresponse> login( @Field("email")String email,
                              @Field("password")String password);
@GET("categories?lang=")
    Call<CategoriesRespons> getCatrgotis(@Query("lang")String lang);


@GET("category/3/items?lang=&user=")
    Call<Items> getitemes(@Query("lang")String lang,@Query("user")String user);

@GET("item/{item_id}?lang=&user=")
    Call<ItemDetails> getitemdetails(@Path("item_id")String item_id,@Query("user")String user);

@POST("user/{user_id}/profile-edit")
    @FormUrlEncoded
    Call<EditProfile> getProfile(@Path("user_id")String id,
                                 @Field("first_name")String first_name,
                                 @Field("last_name")String last_name,
                                 @Field("phone")String phone,
                                 @Field("email")String email);





    @Multipart
@POST("user/{user_id}/upload-image")
    Call<UploadImage> getuploadimage(@Path("user_id")String id,
                                     @Part MultipartBody.Part image);







@GET("user/{user_id}/favorites?lang=en")
    Call<FavoritesByUser> getfave(@Path("user_id")String id);

@POST("favorite/add")
@FormUrlEncoded
    Call<AddFavorite> getaddfav( @Field("user")String user_id,
                                @Field("item")int item_id);
@POST("favorite/remove")
@FormUrlEncoded
    Call<RemoveFavorite> getremovefav(@Field("user")String user_id,
                                      @Field("item")int item_id);

}
