package com.example.vet;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

//    @FormUrlEncoded
//    @POST("Android/v1/registerUser.php")
//    Call<ResponseBody> register(@Field("username") String username, @Field("password") String  password, @Field("email") String email, @Field("type") String type);

    @FormUrlEncoded
    @POST("Android/v1/getAppointment.php")
    Call<ArrayList> getappointment(@Field("username") String username, @Field("status") String status);
   // Call<appointModel> getappointment(@Field("username") String username);

    @FormUrlEncoded
    @POST("Android/v1/registerUser.php")
    Call<ResponseBody> registerUSER(@Field("username") String username, @Field("password") String  password, @Field("email") String email, @Field("type") String type);



//    @FormUrlEncoded
//    @POST("Android/v1/registerUser.php")
//    Call<ResponseBody> register(@Field("username") String username, @Field("password") String  password, @Field("email") String email, @Field("type") String type);

    @FormUrlEncoded
    @POST("Android/v1/userLogin.php")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String  password);

    @FormUrlEncoded
    @POST("Android/v1/vetLogin.php")
    Call<ResponseBody> vetlogin(@Field("username") String username, @Field("password") String  password);

    @FormUrlEncoded
    @POST("Android/v1/registerVet.php")
    Call<ResponseBody> registervet(@Field("username") String username, @Field("password") String  password, @Field("email") String email);





    @FormUrlEncoded
    @POST("imageupload/upload.php")
   // Call<ImageClass> uploadData(@Field("vetid") String username, @Field("image") String  Image);

    Call<ImageClass> uploadData(@Field("vetid") String username, @Field("image") String  Image, @Field("clinicname") String  clinicname, @Field("latitude") String  latitude, @Field("longitude") String  longitude);

    @FormUrlEncoded
    @POST("Android/v1/userid.php")
    Call<ResponseBody> vetid(@Field("username") String username);

    @FormUrlEncoded
    @POST("Android/v1/changeStatus.php")
    Call<ResponseBody> changestaus(@Field("id") String id, @Field("status") String status);


    @POST("Android/v1/getLocation.php")
    Call<ArrayList> location();
    @FormUrlEncoded
    @POST("Android/v1/appointment.php")
    Call<ResponseBody> appointment(@Field("username") String username,@Field("vetname") String vetname,@Field("date") String date,@Field("fromtime") String fromtime,@Field("totime") String totime,@Field("status") String status);
//
//    @FormUrlEncoded
//    @POST("Android/v1/getAppointment.php")
//    Call<appointModel> getappointment(@Field("username") String username);







}
