package com.sapreme.reqresclient.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void refreshUsers() {
        userRepository.fetchUsersFromApi();
    }

    public LiveData<List<User>> getUsers() {
        return userRepository.getUsers();
    }

    public LiveData<User> getUser(int id) { return userRepository.getUser(id); }

    public void clearUsers() {
        userRepository.clearUsers();
    }

    public void addUser(User user){
        userRepository.addUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(User user) { userRepository.deleteUser(user); }
}
