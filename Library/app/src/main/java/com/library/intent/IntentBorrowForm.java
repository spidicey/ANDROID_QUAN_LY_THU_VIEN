package com.library.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.library.R;
import com.library.intent.adapter.borrow_form.BorrowFormAdapter;
import com.library.intent.form.borrow_form.FormBorrowFormCreate;
import com.library.intent.form.borrow_form.FormBorrowFormModify;
import com.library.model.BorrowForm;
import com.library.service.BorrowFormService;
import com.library.utilities.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntentBorrowForm extends AppCompatActivity implements BorrowFormAdapter.OnItemClickListener {
    private final List<BorrowForm> dataStorage = new ArrayList<>();
    private SearchView svSearchBar;
    private FloatingActionButton btnCreate;
    private BorrowFormAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_borrow_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("QUẢN LÝ MƯỢN - TRẢ SÁCH");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setControl();
        setEvent();
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
        else if (itemId == R.id.mReload) BorrowFormService.listBorrowForm(adapter, dataStorage);

        return true;
    }

    private void setControl() {
        this.btnCreate = findViewById(R.id.btnCreate);
        this.svSearchBar = findViewById(R.id.SearchBar);
        RecyclerView rvMainDisplay = findViewById(R.id.rvMainDisplay);
        this.adapter = new BorrowFormAdapter(this);
        // SETUP RECYCLERVIEW.
        rvMainDisplay.addItemDecoration(new SpaceItemDecoration(30));
        rvMainDisplay.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        rvMainDisplay.setAdapter(adapter);
    }

    private void setEvent() {
        this.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FormBorrowFormCreate.class));
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
                adapter.setData(dataStorage.stream()
                        .filter(form -> String.valueOf(form.getId()).contains(s))
                        .collect(Collectors.toList()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // REFRESH.
        BorrowFormService.listBorrowForm(adapter, dataStorage);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), FormBorrowFormModify.class);
        intent.putExtra("item", adapter.getItem(position));
        startActivity(intent);
    }
}