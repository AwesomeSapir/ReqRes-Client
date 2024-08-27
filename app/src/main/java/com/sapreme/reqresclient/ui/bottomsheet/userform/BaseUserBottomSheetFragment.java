package com.sapreme.reqresclient.ui.bottomsheet.userform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.databinding.BottomSheetUserFormBinding;
import com.sapreme.reqresclient.ui.viewmodel.UserViewModel;

import java.util.Objects;

public abstract class BaseUserBottomSheetFragment extends BottomSheetDialogFragment {

    protected BottomSheetUserFormBinding binding;
    protected UserViewModel userViewModel;
    private User user;
    protected String buttonText;
    protected int buttonIconResId;

    public BaseUserBottomSheetFragment(@Nullable User user, String buttonText, int buttonIconResId) {
        this.user = user;
        this.buttonText = buttonText;
        this.buttonIconResId = buttonIconResId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetUserFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if(user != null) {
            Objects.requireNonNull(binding.emailTextInput.getEditText()).setText(user.getEmail());
            Objects.requireNonNull(binding.firstNameTextInput.getEditText()).setText(user.getFirstName());
            Objects.requireNonNull(binding.lastNameTextInput.getEditText()).setText(user.getLastName());
            Glide.with(view.getContext())
                    .load(user.getAvatarUrl())
                    .circleCrop()
                    .into(binding.avatarImageView);
        } else {
            user = new User();
        }
        binding.addButton.setText(buttonText);
        binding.addButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                ResourcesCompat.getDrawable(getResources(), buttonIconResId, null),
                null,
                null,
                null);
        binding.addButton.setOnClickListener(v -> submitAndDismiss());
        Objects.requireNonNull(binding.lastNameTextInput.getEditText()).setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                submitAndDismiss();
                return true;
            }
            return false;
        });
    }

    private void submitAndDismiss() {
        String firstName = binding.firstNameTextInput.getEditText().getText().toString();
        String lastName = binding.lastNameTextInput.getEditText().getText().toString();
        String email = binding.emailTextInput.getEditText().getText().toString();
        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
        }
        submit(user);
        dismiss();
    }

    protected abstract void submit(User user);
}
