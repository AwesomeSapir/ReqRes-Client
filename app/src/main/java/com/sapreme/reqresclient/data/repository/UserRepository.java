package com.sapreme.reqresclient.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sapreme.reqresclient.data.api.reqres.ApiService;
import com.sapreme.reqresclient.data.api.reqres.RetrofitClient;
import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.data.model.UserBundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final ApiService apiService;
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();

    public UserRepository(Application application) {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void clearUsers() {
        users.setValue(new ArrayList<>());
    }

    public LiveData<List<User>> getUsersFromApi() {
        apiService.getUsers(null, null).enqueue(new Callback<UserBundle>() {
            @Override
            public void onResponse(Call<UserBundle> call, Response<UserBundle> response) {
                if(response.isSuccessful() && response.body() != null) {
                    users.setValue(response.body().getUsers());
                }
            }

            @Override
            public void onFailure(Call<UserBundle> call, Throwable throwable) {
                //TODO
            }
        });
        return users;
    }
}
