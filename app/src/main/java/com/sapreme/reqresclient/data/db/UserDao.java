package com.sapreme.reqresclient.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sapreme.reqresclient.data.model.User;

import java.util.Collection;
import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void add(Collection<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void add(User user);

    @Delete
    public void delete(Collection<User> users);

    @Delete
    public void delete(User user);

    @Query("DELETE FROM users")
    void clear();

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

}
