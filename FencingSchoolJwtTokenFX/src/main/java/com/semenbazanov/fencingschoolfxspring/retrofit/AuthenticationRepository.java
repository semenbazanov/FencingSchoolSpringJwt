package com.semenbazanov.fencingschoolfxspring.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.semenbazanov.fencingschoolfxspring.dto.ResponseResult;
import com.semenbazanov.fencingschoolfxspring.security.BasicAuthInterceptor;
import com.semenbazanov.fencingschoolfxspring.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class AuthenticationRepository {
    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    public AuthenticationRepository(String token) {
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

        this.authenticationService = retrofit.create(AuthenticationService.class);
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

    public String authentication(String login, String password) throws IOException {
        Response<ResponseResult<String>> execute =
                this.authenticationService.authentication(login, password).execute();
        return getData(execute);
    }
}
