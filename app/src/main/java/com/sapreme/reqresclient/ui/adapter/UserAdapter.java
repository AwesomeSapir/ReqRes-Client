package com.sapreme.reqresclient.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.databinding.ItemUserBinding;
import com.sapreme.reqresclient.ui.bottomsheet.userform.AddUserBottomSheetFragment;
import com.sapreme.reqresclient.ui.bottomsheet.userform.EditUserBottomSheetFragment;
import com.sapreme.reqresclient.ui.viewmodel.UserViewModel;
import com.sapreme.reqresclient.utility.AvatarBuilder;
import com.sapreme.reqresclient.utility.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {

    private final UserViewModel userViewModel;
    private final FragmentManager fragmentManager;

    public UserAdapter(UserViewModel userViewModel, FragmentManager fragmentManager) {
        super(DIFF_CALLBACK );
        this.userViewModel = userViewModel;
        this.fragmentManager = fragmentManager;
    }

    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK  = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldUser, @NonNull User newUser) {
            return oldUser.equals(newUser);
        }
    };

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUserBinding binding = ItemUserBinding.inflate(layoutInflater, parent, false);
        return new UserViewHolder(binding, userViewModel, fragmentManager);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final ItemUserBinding binding;
        private final UserViewModel userViewModel;
        private final FragmentManager fragmentManager;

        public UserViewHolder(ItemUserBinding binding, UserViewModel userViewModel, FragmentManager fragmentManager) {
            super(binding.getRoot());
            this.binding = binding;
            this.userViewModel = userViewModel;
            this.fragmentManager = fragmentManager;
        }

        public void bind(User user) {
            Log.i(this.toString(), "Binding user: " + user.getId() + " - " + user.getFullName());
            binding.nameTextView.setText(user.getFullName());
            binding.emailTextView.setText(user.getEmail());
            String avatarUrl = user.getAvatarUrl();
            if (avatarUrl == null) {
                avatarUrl = AvatarBuilder.withName(user.getFullName()).buildUrl();
            }
            Glide.with(binding.avatarView.getContext())
                    .load(avatarUrl)
                    .circleCrop()
                    .into(binding.avatarView);
            binding.editButton.setOnClickListener(view -> {
                EditUserBottomSheetFragment editUserBottomSheetFragment = new EditUserBottomSheetFragment(user);
                editUserBottomSheetFragment.show(fragmentManager, editUserBottomSheetFragment.getTag());
            });
            binding.deleteButton.setOnClickListener(view -> {
                DialogUtil.showDeleteUserConfirmation(user, view.getContext(), confirmed -> {
                    if(confirmed){
                        userViewModel.deleteUser(user);
                        Snackbar snackbar = Snackbar.make(view, "User deleted", Snackbar.LENGTH_LONG);
                        snackbar.setAction("Undo", v -> userViewModel.addUser(user));
                        snackbar.show();
                    }
                });
            });
        }
    }
}
