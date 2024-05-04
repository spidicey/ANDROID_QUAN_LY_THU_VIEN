package com.library.intent.adapter.borrow_form;

import android.annotation.SuppressLint;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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

public class BookListCreateAdapter extends RecyclerView.Adapter<BookListCreateAdapter.CreateBorrowBookDetailViewHolder> implements Filterable {
    private final List<BorrowFormDetail> borrowFormDetailList = new ArrayList<>();
    private List<Book> mListBorrowForm;
    private List<Book> mListBorrowFormOld;
    private boolean isUsedInBorrowFormDetails;

    public BookListCreateAdapter() {
    }

    public BookListCreateAdapter(boolean isUsedInBorrowFormDetails) {
        this.isUsedInBorrowFormDetails = isUsedInBorrowFormDetails;
    }

    public List<BorrowFormDetail> getBorrowFormDetailList() {
        return borrowFormDetailList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Book> list) {
        mListBorrowForm = list;
        mListBorrowFormOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreateBorrowBookDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_borrow_form_create, parent, false);
        return new CreateBorrowBookDetailViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CreateBorrowBookDetailViewHolder holder, int position) {
        Book book = mListBorrowForm.get(position);

        String url = book.getImage();
        Glide.with(holder.imageBook).load(url).into(holder.imageBook);
        holder.bookName.setText(book.getTitle());
        holder.textviewQuantity.setText(String.format("Số lượng: %d", book.getQuantity()));
        holder.quantity.setFilters(new InputFilter[]{new InputFilterMinMax(1, book.getQuantity())});

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.quantity.getText().toString().isEmpty()) {
                holder.checkBox.setChecked(false);
                return;
            }
            BorrowFormDetail borrowFormDetail = new BorrowFormDetail(book.getId(), Integer.parseInt(holder.quantity.getText().toString()));
            if (isChecked) {
                borrowFormDetailList.add(borrowFormDetail);
            } else {
                borrowFormDetailList.removeIf(borrowFormDetail1 -> borrowFormDetail1.getBookId() == book.getId());
            }
        });
        holder.checkBox.setEnabled(!isUsedInBorrowFormDetails);
        holder.quantity.setEnabled(!isUsedInBorrowFormDetails);
    }


    @Override
    public int getItemCount() {
        if (mListBorrowForm != null)
            return mListBorrowForm.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    mListBorrowForm = mListBorrowFormOld;
                } else {
                    List<Book> list = new ArrayList<>();
                    for (Book borrowedBook : mListBorrowFormOld) {
                        if (borrowedBook.getTitle().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(borrowedBook);
                        }
                    }
                    mListBorrowForm = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListBorrowForm;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListBorrowForm = (List<Book>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class CreateBorrowBookDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBook;
        TextView bookName;
        CheckBox checkBox;
        TextView textviewQuantity;
        EditText quantity;

        public CreateBorrowBookDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBook = itemView.findViewById(R.id.book_image);
            bookName = itemView.findViewById(R.id.book_name);
            checkBox = itemView.findViewById(R.id.receiptBookCheckBox);
            textviewQuantity = itemView.findViewById(R.id.textview_quantity);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
