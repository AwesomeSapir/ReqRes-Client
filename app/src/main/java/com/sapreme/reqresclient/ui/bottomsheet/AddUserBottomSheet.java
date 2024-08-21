package com.sapreme.reqresclient.ui.bottomsheet;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.databinding.BottomSheetAddUserBinding;
import com.sapreme.reqresclient.ui.viewmodel.UserViewModel;

public class AddUserBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetAddUserBinding binding;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        binding.addButton.setOnClickListener(v -> addUserAction());
        binding.lastNameTextInput.getEditText().setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_DONE) {
                addUserAction();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void addUserAction(){
        User user = new User();
        String firstName = binding.firstNameTextInput.getEditText().getText().toString();
        String lastName = binding.lastNameTextInput.getEditText().getText().toString();
        String email = binding.emailTextInput.getEditText().getText().toString();
        if(!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            userViewModel.addUser(user);
        }
        dismiss();
    }
}
