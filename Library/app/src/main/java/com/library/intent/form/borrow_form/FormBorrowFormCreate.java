package com.library.intent.form.borrow_form;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanOptions;
import com.library.R;
import com.library.intent.adapter.borrow_form.BookListCreateAdapter;
import com.library.model.BorrowForm;
import com.library.model.BorrowFormDetail;
import com.library.model.Reader;
import com.library.service.BookService;
import com.library.service.BorrowFormService;
import com.library.service.ReaderService;
import com.library.utilities.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormBorrowFormCreate extends AppCompatActivity {
    private final Map<String, Integer> lastNameToIdMap = new HashMap<>();
    private BookListCreateAdapter createBorrowDetailsAdapter;
    private AutoCompleteTextView autoCompleteTextView;
//    private int selectedId = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_borrow_form_create);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setupRecycleView();
        setupSearchView();
        setupAutoCompleteTextView();

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            if (autoCompleteTextView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn độc giả!", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedId = Integer.parseInt(autoCompleteTextView.getText().toString().split(" ~ ")[1]);

            BorrowForm borrowForm = new BorrowForm(selectedId);
            List<BorrowFormDetail> borrowFormDetailList = createBorrowDetailsAdapter.getBorrowFormDetailList();

            BorrowFormService.createBorrowForm(borrowForm, borrowFormDetailList);
            finish();
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(FormBorrowFormCreate.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String userData = result.getContents();
//                selectedId = Integer.parseInt(userData.split(" ~ ")[1]);
                autoCompleteTextView.setText(userData);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupAutoCompleteTextView() {
        List<Reader> readerList = new ArrayList<>();
//        Map<String, Integer> lastNameToIdMap = new HashMap<>();
        List<String> lastNameList = new ArrayList<>();
        ReaderService.listReader(readerList, lastNameToIdMap, lastNameList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, lastNameList);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(adapter);
//        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedLastName = (String) parent.getItemAtPosition(position);
//            selectedId = Objects.requireNonNull(lastNameToIdMap.get(selectedLastName));
//        });

        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCompleteTextView.showDropDown();
                return false;
            }
        });
    }

    private void setupRecycleView() {
        RecyclerView rcView = findViewById(R.id.rcv_create_borrowform);
        createBorrowDetailsAdapter = new BookListCreateAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcView.addItemDecoration(new SpaceItemDecoration(30));
        rcView.setLayoutManager(linearLayoutManager);
        rcView.setAdapter(createBorrowDetailsAdapter);
        BookService.listBook(createBorrowDetailsAdapter);
    }

    private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.search_borrow_book_detail);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                createBorrowDetailsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                createBorrowDetailsAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}