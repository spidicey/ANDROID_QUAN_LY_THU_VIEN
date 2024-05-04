package com.library.intent.adapter.sell_form;

import android.annotation.SuppressLint;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.library.R;
import com.library.model.Book;
import com.library.model.SellFormDetail;
import com.library.utilities.InputFilterMinMax;

import java.util.ArrayList;
import java.util.List;

public class BookListCreateAdapter extends RecyclerView.Adapter<BookListCreateAdapter.ItemViewHolder> {
    private final TextView tvTotal;
    private List<Book> data;
    private List<SellFormDetail> details;

    public BookListCreateAdapter(TextView tvTotal) {
        this.tvTotal = tvTotal;
        this.data = new ArrayList<>();
        this.details = new ArrayList<>();
    }

    public List<Book> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Book> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<SellFormDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SellFormDetail> details) {
        this.details = details;
    }

    private double getTotal() {
        return details.stream().mapToDouble(detail -> detail.getQuantity() * detail.getPrice()).sum();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sell_form_general, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Book item = data.get(position);
        // BIND DATA.
        Glide.with(holder.ivImage).load(item.getImage()).into(holder.ivImage);
        // QUANTITY & PRICE OF DETAIL.
        holder.tvMaximum.setText(String.format("Tối đa: %d", item.getQuantity()));
        holder.edtQuantity.setFilters(new InputFilter[]{new InputFilterMinMax(1, item.getQuantity())});
        holder.edtPrice.setFilters(new InputFilter[]{new InputFilterMinMax(0, Integer.MAX_VALUE)});

        holder.ckbChoice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (holder.edtQuantity.getText().toString().isEmpty())
                    holder.edtQuantity.setText(String.valueOf(1));
                if (holder.edtPrice.getText().toString().isEmpty())
                    holder.edtPrice.setText(String.valueOf(0));

                int quantity = Integer.parseInt(holder.edtQuantity.getText().toString());
                double price = Double.parseDouble(holder.edtPrice.getText().toString());
                details.add(new SellFormDetail(item.getId(), quantity, price));
            } else {
                details.removeIf(detail -> detail.getBookId() == item.getId());
            }
            // UPDATE STATE.
            boolean enabled = !holder.edtQuantity.isEnabled();
            holder.edtQuantity.setEnabled(enabled);
            holder.edtPrice.setEnabled(enabled);
            // UPDATE TOTAL.
            tvTotal.setText(String.format("%.0f VNĐ", getTotal()));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox ckbChoice;
        private final ImageView ivImage;
        private final TextView tvMaximum;
        private final EditText edtQuantity, edtPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ckbChoice = itemView.findViewById(R.id.ckbChoice);
            this.ivImage = itemView.findViewById(R.id.ivImage);
            this.tvMaximum = itemView.findViewById(R.id.tvMaximum);
            this.edtQuantity = itemView.findViewById(R.id.edtQuantity);
            this.edtPrice = itemView.findViewById(R.id.edtPrice);
        }
    }
}
