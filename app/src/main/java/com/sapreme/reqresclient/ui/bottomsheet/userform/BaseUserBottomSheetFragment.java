package com.sapreme.reqresclient.ui.bottomsheet.userform;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sapreme.reqresclient.R;
import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.databinding.BottomSheetUserFormBinding;
import com.sapreme.reqresclient.ui.viewmodel.UserViewModel;
import com.sapreme.reqresclient.utility.AvatarBuilder;

import java.util.Objects;

public abstract class BaseUserBottomSheetFragment extends BottomSheetDialogFragment {

    protected BottomSheetUserFormBinding binding;
    protected UserViewModel userViewModel;
    private User user;
    protected String buttonText;

    public BaseUserBottomSheetFragment(@Nullable User user, String buttonText) {
        this.user = user;
        this.buttonText = buttonText;
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
        } else {
            user = new User();
        }

        if(user.getAvatarUrl() == null){
            Glide.with(view.getContext())
                    .load(AvatarBuilder.withName(null).buildUrl())
                    .circleCrop()
                    .into(binding.avatarImageView);
            Objects.requireNonNull(binding.firstNameTextInput.getEditText()).addTextChangedListener(textWatcher);
            Objects.requireNonNull(binding.lastNameTextInput.getEditText()).addTextChangedListener(textWatcher);
        } else {
            Glide.with(view.getContext())
                    .load(user.getAvatarUrl())
                    .circleCrop()
                    .into(binding.avatarImageView);
        }
        binding.actionButton.setText(buttonText);
        binding.actionButton.setOnClickListener(v -> submitAndDismiss());
        Objects.requireNonNull(binding.lastNameTextInput.getEditText()).setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                submitAndDismiss();
                return true;
            }
            return false;
        });


    }

    private final TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String firstName = Objects.requireNonNull(binding.firstNameTextInput.getEditText()).getText().toString();
            String lastName = Objects.requireNonNull(binding.lastNameTextInput.getEditText()).getText().toString();
            if (!firstName.isEmpty() && !lastName.isEmpty()) {
                String avatarUrl = AvatarBuilder.withName(firstName + " " + lastName)
                        .buildUrl();

                Glide.with(requireContext())
                        .load(avatarUrl)
                        .circleCrop()
                        .into(binding.avatarImageView);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

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
