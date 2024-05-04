package com.library.main.fragment.staff.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerHomeAdapter.ItemViewHolder> {
    private final String[] TITLES;
    private final int[] IMAGES;
    private final OnItemClickListener onItemClickListener;

    public RecyclerHomeAdapter(String[] titles, int[] images, OnItemClickListener onItemClickListener) {
        this.TITLES = titles;
        this.IMAGES = images;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new RecyclerHomeAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // BIND DATA.
        holder.ivImage.setImageResource(IMAGES[position]);
        holder.tvTitle.setText(TITLES[position]);
    }

    @Override
    public int getItemCount() {
        return IMAGES.length;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvTitle;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }
}
