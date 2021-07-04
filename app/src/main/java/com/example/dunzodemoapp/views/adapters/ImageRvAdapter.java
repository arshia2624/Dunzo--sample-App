package com.example.dunzodemoapp.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dunzodemoapp.R;
import com.example.dunzodemoapp.databinding.ItemImageRvBinding;
import com.example.dunzodemoapp.models.PhotosList;

public class ImageRvAdapter extends PagedListAdapter<PhotosList.Photo, ImageRvAdapter.MyViewHolder> {

    public ImageRvAdapter() {
        super(PhotosList.Photo.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageRvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_image_rv, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.binding.setData(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemImageRvBinding binding;

        MyViewHolder(ItemImageRvBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}
