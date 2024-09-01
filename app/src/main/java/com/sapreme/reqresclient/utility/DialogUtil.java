package com.sapreme.reqresclient.utility;

import android.app.AlertDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.ui.viewmodel.UserViewModel;

public class DialogUtil {

    public static void showDeleteUserConfirmation(User user, Context context, OnConfirmationListener listener){
        new AlertDialog.Builder(context)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete " + user.getFullName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> listener.onConfirmed(true))
                .setNegativeButton("Cancel", (dialog, which) -> listener.onConfirmed(false))
                .show();
    }

    public interface OnConfirmationListener {
        void onConfirmed(boolean confirmed);
    }
}
