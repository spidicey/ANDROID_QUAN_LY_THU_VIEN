package com.library.main.fragment.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerHomeAdapter.ItemViewHolder> {
    private final String[] TITLES = {"QUẢN LÝ NHÂN VIÊN", "KIỂM DUYỆT PHIẾU", "BÁO CÁO - THỐNG KÊ"};
    private final int[] IMAGES = {R.drawable.menu_03_01, R.drawable.menu_03_02, R.drawable.menu_03_03};
    private final OnItemClickListener onItemClickListener;

    public RecyclerHomeAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ItemViewHolder(view);
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
