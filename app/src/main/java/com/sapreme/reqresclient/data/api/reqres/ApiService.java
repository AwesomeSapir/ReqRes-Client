package com.sapreme.reqresclient.data.api.reqres;

import com.sapreme.reqresclient.data.model.UserBundle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Call<UserBundle> getUsers(
            @Query("page") Integer page,
            @Query("per_page") Integer perPage
    );

}
