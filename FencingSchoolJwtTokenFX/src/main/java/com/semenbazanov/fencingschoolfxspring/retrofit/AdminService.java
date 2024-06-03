package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.model.Admin;
import retrofit2.Call;
import retrofit2.http.*;

public interface AdminService {
    @POST("admin")
    Call<ResponseResult<Admin>> post(@Body Admin admin);

    @GET("admin/{id}")
    Call<ResponseResult<Admin>> get(@Path("id") long id);

    @GET("admin/search")
    Call<ResponseResult<Admin>> get(@Query("login") String login, @Query("password") String password);

    @DELETE("admin/{id}")
    Call<ResponseResult<Admin>> delete(@Path("id") long id);
}
