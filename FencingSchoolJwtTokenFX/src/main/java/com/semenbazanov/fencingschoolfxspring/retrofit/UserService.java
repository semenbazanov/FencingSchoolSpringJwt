package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserService {
    @GET("user/all")
    Call<ResponseResult<List<User>>> getAll();

    @GET("user")
    Call<ResponseResult<User>> get();
}
