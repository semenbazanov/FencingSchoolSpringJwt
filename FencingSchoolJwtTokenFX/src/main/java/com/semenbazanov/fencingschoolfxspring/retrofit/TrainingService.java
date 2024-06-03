package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.model.Training;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TrainingService {
    @POST("training/{trainerId}/{apprenticeId}")
    Call<ResponseResult<Training>> post(@Path("trainerId") long trainerId,
                                        @Path("apprenticeId") long apprenticeId,
                                        @Body Training training);

    @GET("training/apprentice/{apprenticeId}")
    Call<ResponseResult<List<Training>>> get(@Path("apprenticeId") long apprenticeId);

    @GET("training/time/{trainerId}")
    Call<ResponseResult<List<LocalTime>>> get(@Path("trainerId") long trainerId,
                                              @Query("date") String date);

    @GET("training/date/{trainerId}")
    Call<ResponseResult<List<LocalDate>>> getFreeDate(@Path("trainerId") long trainerId);

    @DELETE("training/{id}")
    Call<ResponseResult<Training>> delete(@Path("id") long id);
}
