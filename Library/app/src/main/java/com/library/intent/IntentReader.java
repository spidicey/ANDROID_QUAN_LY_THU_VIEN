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

import com.library.R;
import com.library.intent.adapter.reader.ReaderAdapter;
import com.library.intent.form.reader.FormReaderModify;
import com.library.model.Reader;
import com.library.service.ReaderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntentReader extends AppCompatActivity {
    private final List<Reader> dataStorage = new ArrayList<>();
    private final List<Reader> data = new ArrayList<>();
    private SearchView svSearch;
    private ListView lvDisplay;
    private ReaderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_reader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("QUẢN LÝ ĐỘC GIẢ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setControl();
        setData();
        setEvent();
    }

    private void setControl() {
        this.svSearch = findViewById(R.id.SearchBar);
        this.lvDisplay = findViewById(R.id.lvDisplay);
    }

    private void setData() {
        adapter = new ReaderAdapter(this, R.layout.item_user, data);
        this.lvDisplay.setAdapter(adapter);
    }

    private void setEvent() {
        this.lvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), FormReaderModify.class);
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
                        .filter(reader -> reader.getFullName().toLowerCase().contains(s.toLowerCase()))
                        .collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // REFRESH.
        ReaderService.listReader(adapter, data, dataStorage);
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
        else if (itemId == R.id.mReload) ReaderService.listReader(adapter, data, dataStorage);

        return true;
    }
}