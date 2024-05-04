package com.library.intent.adapter.borrow_form;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.model.BorrowForm;
import com.library.service.LibrarianService;
import com.library.service.ReaderService;

import java.util.ArrayList;
import java.util.List;

public class BorrowFormAdapter extends RecyclerView.Adapter<BorrowFormAdapter.ItemViewHolder> {
    private final OnItemClickListener onItemClickListener;
    private List<BorrowForm> data;

    public BorrowFormAdapter(OnItemClickListener onItemClickListener) {
        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    public List<BorrowForm> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<BorrowForm> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public BorrowForm getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_borrow_form, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        BorrowForm item = data.get(position);
        // BIND DATA.
        holder.ivState.setImageResource(item.getStateImage());
        LibrarianService.setLibrarianTextView(holder.tvLibrarian, item.getLibrarianId());
        ReaderService.setReaderTextView(holder.tvReader, item.getReaderId());
        holder.tvCreatedDate.setText(item.getCreatedDate());
        holder.tvExpiredDate.setText(item.getExpiredDate());
        holder.tvState.setText(item.getStateName());
        holder.tvState.setBackgroundResource(item.getStateBackground());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivState;
        private final TextView tvLibrarian, tvReader, tvCreatedDate, tvExpiredDate, tvState;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivState = itemView.findViewById(R.id.ivState);
            this.tvLibrarian = itemView.findViewById(R.id.tvLibrarian);
            this.tvReader = itemView.findViewById(R.id.tvReader);
            this.tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
            this.tvExpiredDate = itemView.findViewById(R.id.tvExpiredDate);
            this.tvState = itemView.findViewById(R.id.tvState);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
