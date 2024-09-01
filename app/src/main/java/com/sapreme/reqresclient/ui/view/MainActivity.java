package com.sapreme.reqresclient.ui.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sapreme.reqresclient.R;
import com.sapreme.reqresclient.databinding.ActivityMainBinding;
import com.sapreme.reqresclient.ui.adapter.UserAdapter;
import com.sapreme.reqresclient.ui.bottomsheet.userform.AddUserBottomSheetFragment;
import com.sapreme.reqresclient.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UserAdapter userAdapter;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userAdapter = new UserAdapter();
        binding.usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.usersRecyclerView.setAdapter(userAdapter);

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, users -> userAdapter.submitList(users));

        binding.fetchUsersFab.setOnClickListener(view -> userViewModel.refreshUsers());
        //binding.clearUsersFab.setOnClickListener(view -> userViewModel.clearUsers());
        binding.addUserFab.setOnClickListener(view -> {
            AddUserBottomSheetFragment bottomSheet = new AddUserBottomSheetFragment();
            bottomSheet.show(getSupportFragmentManager(), "AddUserBottomSheet");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}