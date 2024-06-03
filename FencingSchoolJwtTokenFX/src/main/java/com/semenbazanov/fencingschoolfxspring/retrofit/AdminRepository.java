package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.model.Admin;
import com.semenbazanov.fencingschoolfxspring.security.BasicAuthInterceptor;
import com.semenbazanov.fencingschoolfxspring.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class AdminRepository {

    private final ObjectMapper objectMapper;
    private AdminService adminService;

    public AdminRepository(String token) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (token != null){
            builder.addInterceptor(new BasicAuthInterceptor(token));
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.adminService = retrofit.create(AdminService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() != 200) {
            String message = objectMapper.readValue(execute.errorBody().string(),
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }

    public Admin post(Admin admin) throws IOException {
        Response<ResponseResult<Admin>> execute = this.adminService.post(admin).execute();
        return getData(execute);
    }

    public Admin delete(long id) throws IOException {
        Response<ResponseResult<Admin>> execute = this.adminService.delete(id).execute();
        return getData(execute);
    }
}
