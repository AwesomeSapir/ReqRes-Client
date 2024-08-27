package com.sapreme.reqresclient.ui.bottomsheet.userform;

import androidx.annotation.NonNull;

import com.sapreme.reqresclient.R;
import com.sapreme.reqresclient.data.model.User;

public class EditUserBottomSheetFragment extends BaseUserBottomSheetFragment{

    public EditUserBottomSheetFragment(@NonNull User user) {
        super(user, "Save", R.drawable.baseline_save_24);
    }

    @Override
    protected void submit(User user) {
        userViewModel.updateUser(user);
    }
}
