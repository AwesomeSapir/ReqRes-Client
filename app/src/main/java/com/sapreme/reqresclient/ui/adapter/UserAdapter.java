package com.sapreme.reqresclient.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sapreme.reqresclient.data.model.User;
import com.sapreme.reqresclient.databinding.ItemUserBinding;
import com.sapreme.reqresclient.ui.bottomsheet.userform.AddUserBottomSheetFragment;
import com.sapreme.reqresclient.ui.bottomsheet.userform.EditUserBottomSheetFragment;
import com.sapreme.reqresclient.utility.AvatarBuilder;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {

    public UserAdapter() {
        super(DIFF_CALLBACK );
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
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final ItemUserBinding binding;

        public UserViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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
            binding.getRoot().setOnLongClickListener(view -> {
                EditUserBottomSheetFragment editUserBottomSheetFragment = new EditUserBottomSheetFragment(user);
                editUserBottomSheetFragment.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), editUserBottomSheetFragment.getTag());
                return true;
            });
        }
    }
}
