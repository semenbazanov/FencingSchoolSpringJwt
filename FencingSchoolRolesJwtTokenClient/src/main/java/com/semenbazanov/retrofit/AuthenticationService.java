package com.semenbazanov.retrofit;

import com.semenbazanov.dto.ResponseResult;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthenticationService {
    @POST("/auth")
    Call<ResponseResult<String>> authentication(@Query("username") String username,
                                                @Query("password") String password);
}
