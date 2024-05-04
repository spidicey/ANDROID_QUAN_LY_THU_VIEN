package com.library.intent.adapter.book;

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

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ItemViewHolder> {
    private final ItemInterface itemInterface;
    private List<Book> data;

    public BookAdapter(ItemInterface itemInterface) {
        this.itemInterface = itemInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Book> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ItemViewHolder(v);
    }

    public Book getItem(int pos) {
        return data.get(pos);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Book book = data.get(position);

        Glide.with(holder.imgBook).load(book.getImage()).into(holder.imgBook);
        holder.tvTitle.setText(book.getTitle().toUpperCase());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvType.setText(book.getCategory());
        holder.tvBookPosition.setText(String.format("K%d ~ N%d", book.getShelf(), book.getBookshelf()));
        holder.tvQuantity.setText(String.format("%d/%d", book.getQuantity(), book.getInventory()));
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public interface ItemInterface {
        void onItemClick(View view, int position);

        void OnItemLongClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgBook;
        private final TextView tvTitle, tvAuthor, tvType, tvBookPosition, tvQuantity;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgBook = itemView.findViewById(R.id.ivImage);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvAuthor = itemView.findViewById(R.id.tvAuthor);
            this.tvType = itemView.findViewById(R.id.tvCategory);
            this.tvBookPosition = itemView.findViewById(R.id.tvPosition);
            this.tvQuantity = itemView.findViewById(R.id.tvQuantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemInterface.onItemClick(v, getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemInterface.OnItemLongClick(v, getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
