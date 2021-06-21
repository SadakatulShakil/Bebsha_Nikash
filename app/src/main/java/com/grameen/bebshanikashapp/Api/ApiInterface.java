package com.grameen.bebshanikashapp.Api;

import com.grameen.bebshanikashapp.Model.AsignRole.AsignRole;
import com.grameen.bebshanikashapp.Model.SignUp.SignUp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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

    @Headers("accept: application/json, content-type: application/json")
    @FormUrlEncoded
    @POST("authenticate")
    Call<String> postByLogIn(
            @Field("district_id") int district_id);

    @Headers("accept: application/json, content-type: application/json")
    @FormUrlEncoded
    @POST("assign-role")
    Call<AsignRole> postByAsignRole(
            @Field("phone") String phone,
            @Field("role") String role
    );

}


