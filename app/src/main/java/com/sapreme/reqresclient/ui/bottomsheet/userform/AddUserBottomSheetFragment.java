package com.sapreme.reqresclient.ui.bottomsheet.userform;

import androidx.annotation.Nullable;

import com.sapreme.reqresclient.R;
import com.sapreme.reqresclient.data.model.User;

public class AddUserBottomSheetFragment extends BaseUserBottomSheetFragment{

    public AddUserBottomSheetFragment() {
        super(null, "Add", R.drawable.baseline_add_circle_24);
    }

    @Override
    protected void submit(User user) {
        userViewModel.addUser(user);
    }
}
