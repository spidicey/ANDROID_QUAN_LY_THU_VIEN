package com.library.intent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.library.R;
import com.library.intent.adapter.book.BookAdapter;
import com.library.intent.form.book.FormBookCreate;
import com.library.intent.form.book.FormBookModify;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.utilities.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntentBook extends AppCompatActivity implements BookAdapter.ItemInterface {
    private final List<Book> dataStorage = new ArrayList<>();
    private SearchView svSearchBar;
    private RecyclerView rvMainDisplay;
    private FloatingActionButton btnCreate;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_book);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("QUẢN LÝ SÁCH");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setControl();
        setData();
        setEvent();
    }

    private void setControl() {
        this.btnCreate = findViewById(R.id.btnCreate);
        this.svSearchBar = findViewById(R.id.SearchBar);
        this.rvMainDisplay = findViewById(R.id.rvMainDisplay);
        this.adapter = new BookAdapter(this);
    }

    private void setData() {
        rvMainDisplay.addItemDecoration(new SpaceItemDecoration(30));
        rvMainDisplay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMainDisplay.setAdapter(adapter);
    }

    private void setEvent() {
        this.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FormBookCreate.class));
            }
        });

        this.svSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String s) {
                String kw = s.toLowerCase();
                adapter.setData(dataStorage.stream()
                        .filter(book -> book.getTitle().contains(kw))
                        .collect(Collectors.toList()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // REFRESH.
        BookService.listBook(adapter, dataStorage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) finish();
        else if (itemId == R.id.mReload) BookService.listBook(adapter, dataStorage);

        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), FormBookModify.class);
        intent.putExtra("item", adapter.getItem(position));
        startActivity(intent);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void OnItemLongClick(View view, int position) {
        Book book = adapter.getItem(position);
        if (book.getQuantity() != 0 && book.getInventory() != 0) {
            Toast.makeText(this, "Sách đã được đưa vào sử dụng!", Toast.LENGTH_SHORT).show();
            return;
        }

        view.setBackgroundResource(R.drawable.bg_item_selected);
        Dialog dialog = getDialog(view);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btnComplete = dialog.findViewById(R.id.btnComplete);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setText("XÓA SÁCH");

        tvTitle.setText(String.format("SÁCH #%d", book.getId()));
        btnComplete.setVisibility(View.GONE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookService.deleteBook(book.getId());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @NonNull
    private Dialog getDialog(View view) {
        Dialog dialog = new Dialog(IntentBook.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sell_form_item);
        dialog.setCancelable(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // REFRESH.
                view.setBackgroundResource(R.drawable.bg_item_unselected);
                BookService.listBook(adapter, dataStorage);
            }
        });
        return dialog;
    }
}