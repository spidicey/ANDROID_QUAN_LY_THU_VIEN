package com.library.intent.adapter.borrow_form;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.library.R;
import com.library.model.Book;
import com.library.model.BorrowFormDetail;
import com.library.utilities.InputFilterMinMax;

import java.util.ArrayList;
import java.util.List;

public class BookListModifyAdapter extends RecyclerView.Adapter<BookListModifyAdapter.ItemViewHolder> {
    private final int formState;
    private List<Book> data;
    private List<BorrowFormDetail> details;

    public BookListModifyAdapter(int formState) {
        this.data = new ArrayList<>();
        this.details = new ArrayList<>();
        this.formState = formState;
    }

    public List<Book> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Book> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<BorrowFormDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BorrowFormDetail> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_borrow_form_modify, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Book item = data.get(position);
        // BIND DATA.
        Glide.with(holder.ivImage).load(item.getImage()).into(holder.ivImage);

        BorrowFormDetail detail = details.stream().filter(e -> e.getBookId() == item.getId()).findFirst().orElse(null);
        if (detail != null) {
            // QUANTITY OF DETAIL.
            int minValue = detail.getReturnedQuantity(), maxValue = detail.getQuantity();
            holder.tvMaximum.setText(String.format("Đã mượn: %d", maxValue));
            holder.edtQuantity.setText(String.valueOf(minValue));
            holder.edtQuantity.setFilters(new InputFilter[]{new InputFilterMinMax(minValue, maxValue)});

            holder.edtQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String numberText = s.toString();
                    if (!numberText.isEmpty()) {
                        int number = Integer.parseInt(numberText);
                        detail.setReturnedQuantity(number);
                        // FORCE STATE BASE ON QUANTITY.
                        if (number == maxValue) holder.spState.setSelection(1);
                    }
                }
            });
            // STATE OF DETAIL.
            holder.spState.setSelection(detail.getState());
            if (detail.getState() > 0) {
                holder.tvReturnedDate.setText(String.format("Ngày: %s", detail.getReturnedDate()));
                holder.tvReturnedDate.setVisibility(View.VISIBLE);
                holder.spState.setEnabled(false);
            }
            holder.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    detail.setState(position);
                    // FORCE QUANTITY BASE ON STATE.
                    holder.edtQuantity.setEnabled(formState != 1 && position != 1);
                    holder.edtQuantity.setText(String.valueOf(position != 1 ? minValue : maxValue));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvMaximum, tvReturnedDate;
        private final EditText edtQuantity;
        private final Spinner spState;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivImage = itemView.findViewById(R.id.ivImage);
            this.tvMaximum = itemView.findViewById(R.id.tvMaximum);
            this.edtQuantity = itemView.findViewById(R.id.edtQuantity);
            this.spState = itemView.findViewById(R.id.spState);
            this.tvReturnedDate = itemView.findViewById(R.id.tvReturnedDate);

            spState.setAdapter(new ArrayAdapter<>(itemView.getContext(),
                    R.layout.item_spinner_borrow_state, BorrowFormDetail.getStateNames()));
        }
    }
}
