package com.sapreme.reqresclient.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getUsers() {
        if(users == null) {
            users = userRepository.getUsersFromApi();
        }
        return users;
    }
}
