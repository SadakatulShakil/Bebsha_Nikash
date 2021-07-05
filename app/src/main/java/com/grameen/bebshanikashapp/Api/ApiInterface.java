package com.grameen.bebshanikashapp.Api;

import com.grameen.bebshanikashapp.Model.AllProducts.AllProducts;
import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.Model.AsignRole.AsignRole;
import com.grameen.bebshanikashapp.Model.Categories.Categories;
import com.grameen.bebshanikashapp.Model.LogIn.LogIn;
import com.grameen.bebshanikashapp.Model.SignUp.SignUp;
import com.grameen.bebshanikashapp.Model.UploadProduct.UploadProduct;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @Headers("futureskykey: futuresky_2020@$#ltd")
    @FormUrlEncoded
    @POST("signup")
    Call<SignUp> postBySignUp(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @Headers("futureskykey: futuresky_2020@$#ltd, accept: application/json, content-type: application/json")
    @FormUrlEncoded
    @POST("authenticate")
    Call<LogIn> postByLogIn(
            @Field("email") String email,
            @Field("password") String password);

    @Headers("futureskykey: futuresky_2020@$#ltd, accept: application/json, content-type: application/json")
    @FormUrlEncoded
    @POST("assign-role")
    Call<AsignRole> postByAsignRole(
            @Field("phone") String phone,
            @Field("role") String role
    );

    @Headers("accept: application/json, content-type: multipart/form-data")
    @GET("categories")
    Call<Categories> getByCategory(
            @Header("Authorization") String token
    );

    @Headers("accept: application/json, content-type: multipart/form-data")
    @GET("products")
    Call<AllProducts> getByProduct(
            @Header("Authorization") String token
    );

    @Headers("accept: application/json, content-type: multipart/form-data")
    @Multipart
    @POST("products")
    Call<UploadProduct> postByUploadProduct(
            @Header("Authorization") String token,
            @Part("product_name") RequestBody product_name,
            @Part("description") RequestBody description,
            @Part("buying_price") RequestBody buying_price,
            @Part("selling_price") RequestBody selling_price,
            @Part("sku") RequestBody sku,
            @Part("quantity") RequestBody quantity,
            @Part("unit") RequestBody unit,
            @Part("category") RequestBody category,
            @Part("photo1\"; filename=\"myProfile.jpg\" " ) RequestBody photo1,
            @Part("photo2\"; filename=\"myProfile2.jpg\" " ) RequestBody photo2
    );

}


