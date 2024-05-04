package com.library.main.fragment.reader;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.library.R;
import com.library.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookSearchAdapter extends RecyclerView.Adapter<BookSearchAdapter.ItemViewHolder> {
    private List<Book> data;

    public BookSearchAdapter() {
        this.data = new ArrayList<>();
    }

    public List<Book> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Book> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Book item = data.get(position);
        // BIND DATA.
        Glide.with(holder.ivImage).load(item.getImage()).into(holder.ivImage);
        holder.tvTitle.setText(item.getTitle().toUpperCase());
        holder.tvAuthor.setText(item.getAuthor());
        holder.tvCategory.setText(item.getCategory());
        holder.tvPosition.setText(String.format("K%d ~ N%d", item.getShelf(), item.getBookshelf()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        if (item.getQuantity() == 0) {
            holder.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            holder.tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {
        private final ImageView ivImage;
        private final TextView tvTitle, tvAuthor, tvCategory, tvPosition, tvQuantity, tvEmpty;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivImage = itemView.findViewById(R.id.ivImage);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvAuthor = itemView.findViewById(R.id.tvAuthor);
            this.tvCategory = itemView.findViewById(R.id.tvCategory);
            this.tvPosition = itemView.findViewById(R.id.tvPosition);
            this.tvQuantity = itemView.findViewById(R.id.tvQuantity);
            this.tvEmpty = itemView.findViewById(R.id.tvEmpty);

            itemView.setClickable(true);
            itemView.setFocusable(true);
            itemView.setFocusableInTouchMode(true);
            itemView.setOnFocusChangeListener(this);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                tvTitle.setSelected(true);
                tvAuthor.setSelected(true);
                tvCategory.setSelected(true);
            } else {
                tvTitle.setSelected(false);
                tvAuthor.setSelected(false);
                tvCategory.setSelected(false);
            }
        }
    }
}
