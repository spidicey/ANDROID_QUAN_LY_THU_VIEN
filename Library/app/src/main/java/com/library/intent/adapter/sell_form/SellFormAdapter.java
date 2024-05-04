package com.library.intent.adapter.sell_form;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.model.SellForm;
import com.library.service.LibrarianService;

import java.util.ArrayList;
import java.util.List;

public class SellFormAdapter extends RecyclerView.Adapter<SellFormAdapter.ItemViewHolder> {
    private final OnItemClickListener onItemClickListener;
    private List<SellForm> data;

    public SellFormAdapter(OnItemClickListener onItemClickListener) {
        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    public List<SellForm> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<SellForm> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public SellForm getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell_form, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SellForm item = data.get(position);
        // BIND DATA.
        holder.ivState.setImageResource(item.getStateImage());
        LibrarianService.setLibrarianTextView(holder.tvLibrarian, item.getLibrarianId());
        holder.tvDate.setText(item.getCreatedDate());
        holder.tvTotal.setText(String.format("%.0f VMĐ", item.getTotal()));
        holder.tvState.setText(item.getStateName());
        holder.tvState.setBackgroundResource(item.getStateBackground());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView ivState;
        private final TextView tvLibrarian, tvDate, tvTotal, tvState;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivState = itemView.findViewById(R.id.ivState);
            this.tvLibrarian = itemView.findViewById(R.id.tvLibrarian);
            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvTotal = itemView.findViewById(R.id.tvTotal);
            this.tvState = itemView.findViewById(R.id.tvState);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClickListener.onItemLongClick(v, getAdapterPosition());
            return true;
        }
    }
}
