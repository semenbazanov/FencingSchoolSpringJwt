package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.model.TrainerSchedule;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalTime;

public interface TrainerScheduleService {
    @POST("trainer_schedule/{id}")
    Call<ResponseResult<TrainerSchedule>> post(@Path("id") long id, @Query("dayWeek") String dayWeek,
                                               @Query("start") LocalTime start, @Query("end") LocalTime end);

    @GET("trainer_schedule/{id}")
    Call<ResponseResult<TrainerSchedule>> get(@Path("id") long id);

    @DELETE("trainer_schedule/{id}")
    Call<ResponseResult<TrainerSchedule>> delete(@Path("id") long id,
                                                 @Query("dayWeek") String dayWeek);
}
