package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApprenticeService {
    @POST("apprentice")
    Call<ResponseResult<Apprentice>> post(@Body Apprentice apprentice);

    @GET("apprentice/{id}")
    Call<ResponseResult<Apprentice>> get(@Path("id") long id);

    @GET("apprentice")
    Call<ResponseResult<List<Apprentice>>> get();

    @DELETE("apprentice/{id}")
    Call<ResponseResult<Apprentice>> delete(@Path("id") long id);

    @PUT("apprentice")
    Call<ResponseResult<Apprentice>> update(@Body Apprentice apprentice);
}
