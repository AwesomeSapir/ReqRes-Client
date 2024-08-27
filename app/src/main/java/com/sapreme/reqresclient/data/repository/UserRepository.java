package com.sapreme.reqresclient.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sapreme.reqresclient.data.api.reqres.ApiService;
import com.sapreme.reqresclient.data.api.reqres.RetrofitClient;
import com.sapreme.reqresclient.data.db.AppDatabase;
import com.sapreme.reqresclient.data.db.UserDao;
import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.data.model.UserBundle;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final UserDao userDao;
    private final ApiService apiService;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
        apiService = RetrofitClient.getApiService();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<User>> getUsers() {
        return userDao.getAll();
    }

    public LiveData<User> getUser(int id) {
        return userDao.getById(id);
    }

    public void clearUsers() {
        executorService.execute(userDao::clear);
    }

    public void addUsers(Collection<User> users) {
        executorService.execute(() -> userDao.add(users));
    }

    public void updateUser(User user){
        executorService.execute(() -> userDao.update(user));
    }

    public void addUser(User user) {
        executorService.execute(() -> userDao.add(user));
    }

    public void fetchUsersFromApi() {
        apiService.getUsers(null, null).enqueue(new Callback<UserBundle>() {
            @Override
            public void onResponse(Call<UserBundle> call, Response<UserBundle> response) {
                if(response.isSuccessful() && response.body() != null) {
                    addUsers(response.body().getUsers());
                }
            }

            @Override
            public void onFailure(Call<UserBundle> call, Throwable throwable) {
                //TODO
            }
        });
    }
}
