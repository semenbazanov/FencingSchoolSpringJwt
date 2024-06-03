package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.model.Trainer;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface TrainerService {
    @POST("trainer")
    Call<ResponseResult<Trainer>> post(@Body Trainer trainer);

    @GET("trainer/{id}")
    Call<ResponseResult<Trainer>> get(@Path("id") long id);

    @GET("trainer")
    Call<ResponseResult<List<Trainer>>> get();

    @PUT("trainer")
    Call<ResponseResult<Trainer>> update(@Body Trainer trainer);

    @DELETE("trainer/{id}")
    Call<ResponseResult<Trainer>> delete(@Path("id") long id);
}
