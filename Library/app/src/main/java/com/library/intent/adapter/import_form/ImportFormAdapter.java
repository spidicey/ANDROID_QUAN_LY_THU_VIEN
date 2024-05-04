package com.library.intent.adapter.import_form;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.model.ImportForm;
import com.library.service.LibrarianService;

import java.util.ArrayList;
import java.util.List;

public class ImportFormAdapter extends RecyclerView.Adapter<ImportFormAdapter.ItemViewHolder> {
    private final OnItemClickListener onItemClickListener;
    private List<ImportForm> data;

    public ImportFormAdapter(OnItemClickListener onItemClickListener) {
        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ImportFormAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_import_form, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ImportFormAdapter.ItemViewHolder holder, int position) {
        ImportForm item = data.get(position);
        // BIND DATA.
        holder.ivState.setImageResource(item.getStateImage());
        LibrarianService.setLibrarianTextView(holder.tvLibrarian, item.getLibrarianId());
        holder.tvDate.setText(item.getCreatedDate());
        holder.tvTotal.setText(String.format("%.0f VNĐ", item.getTotal()));
        holder.tvState.setText(item.getStateName());
        holder.tvState.setBackgroundResource(item.getStateBackground());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<ImportForm> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ImportForm> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ImportForm getItem(int position) {
        return data.get(position);
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
