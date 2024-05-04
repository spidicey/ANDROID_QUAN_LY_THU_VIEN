package com.library.intent.adapter.validate_form;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.model.ImportForm;
import com.library.model.SellForm;
import com.library.service.LibrarianService;

import java.util.ArrayList;
import java.util.List;

public class ValidateFormAdapter extends RecyclerView.Adapter<ValidateFormAdapter.ItemViewHolder> {
    private final OnItemClickListener onItemClickListener;
    private List<Object> data;

    public ValidateFormAdapter(OnItemClickListener onItemClickListener) {
        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_validate_form, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Object item = data.get(position);
        if (item instanceof ImportForm) {
            ImportForm form = (ImportForm) item;
            // BIND DATA.
            LibrarianService.setLibrarianTextView(holder.tvLibrarian, form.getLibrarianId());
            holder.tvDate.setText(form.getCreatedDate());
            holder.tvTotal.setText(String.format("%.0f VNĐ", form.getTotal()));
        } else {
            SellForm form = (SellForm) item;
            // BIND DATA.
            LibrarianService.setLibrarianTextView(holder.tvLibrarian, form.getLibrarianId());
            holder.tvDate.setText(form.getCreatedDate());
            holder.tvTotal.setText(String.format("%.0f VNĐ", form.getTotal()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Object> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Object> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvLibrarian, tvDate, tvTotal;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvLibrarian = itemView.findViewById(R.id.tvLibrarian);
            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvTotal = itemView.findViewById(R.id.tvTotal);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
