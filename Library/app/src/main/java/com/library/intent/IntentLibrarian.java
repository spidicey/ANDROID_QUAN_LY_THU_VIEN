package com.library.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.library.R;
import com.library.intent.adapter.librarian.LibrarianAdapter;
import com.library.intent.form.librarian.FormLibrarianCreate;
import com.library.intent.form.librarian.FormLibrarianModify;
import com.library.model.Librarian;
import com.library.service.LibrarianService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntentLibrarian extends AppCompatActivity {
    private final List<Librarian> dataStorage = new ArrayList<>();
    private final List<Librarian> data = new ArrayList<>();
    private FloatingActionButton btnCreate;
    private SearchView svSearch;
    private ListView lvDisplay;
    private LibrarianAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_librarian);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("QUẢN LÝ THỦ THƯ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setControl();
        setData();
        setEvent();
    }

    private void setControl() {
        this.btnCreate = findViewById(R.id.btnCreate);
        this.svSearch = findViewById(R.id.SearchBar);
        this.lvDisplay = findViewById(R.id.lvDisplay);
    }

    private void setData() {
        adapter = new LibrarianAdapter(this, R.layout.item_user, data);
        this.lvDisplay.setAdapter(adapter);
    }

    private void setEvent() {
        this.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FormLibrarianCreate.class));
            }
        });

        this.lvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), FormLibrarianModify.class);
                intent.putExtra("item", adapter.getItem(position));
                startActivity(intent);
            }
        });

        this.svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                data.clear();
                data.addAll(dataStorage.stream()
                        .filter(librarian -> librarian.getFullName().toLowerCase().contains(s.toLowerCase()))
                        .collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // REFRESH.
        LibrarianService.listLibrarian(adapter, data, dataStorage);
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
        else if (itemId == R.id.mReload) LibrarianService.listLibrarian(adapter, data, dataStorage);

        return true;
    }
}