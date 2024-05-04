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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.library.R;
import com.library.intent.adapter.sell_form.SellFormAdapter;
import com.library.intent.form.sell_form.FormSellFormCreate;
import com.library.intent.form.sell_form.FormSellFormModify;
import com.library.model.SellForm;
import com.library.service.SellFormService;
import com.library.utilities.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntentSellForm extends AppCompatActivity implements SellFormAdapter.OnItemClickListener {
    private final List<SellForm> dataStorage = new ArrayList<>();
    private SearchView svSearchBar;
    private FloatingActionButton btnCreate;
    private RecyclerView rvMainDisplay;
    private SellFormAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_sell_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("QUẢN LÝ THANH LÝ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setControl();
        setData();
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
        else if (itemId == R.id.mReload) SellFormService.listSellForm(adapter, dataStorage);

        return true;
    }

    private void setControl() {
        this.svSearchBar = findViewById(R.id.SearchBar);
        this.btnCreate = findViewById(R.id.btnCreate);
        this.adapter = new SellFormAdapter(this);
        this.rvMainDisplay = findViewById(R.id.rvMainDisplay);
    }

    private void setData() {
        // SETUP RECYCLERVIEW.
        rvMainDisplay.addItemDecoration(new SpaceItemDecoration(30));
        rvMainDisplay.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvMainDisplay.setAdapter(adapter);
    }

    private void setEvent() {
        this.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FormSellFormCreate.class));
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
        SellFormService.listSellForm(adapter, dataStorage);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), FormSellFormModify.class);
        intent.putExtra("item", adapter.getItem(position));
        startActivity(intent);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onItemLongClick(View view, int position) {
        SellForm form = adapter.getItem(position);
        if (form.getState() >= 2) return;

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

        tvTitle.setText(String.format("PHIẾU THANH LÝ #%d", form.getId()));
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellFormService.completeSellForm(form.getId());
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellFormService.deleteSellForm(form.getId());
                dialog.dismiss();
            }
        });

        if (form.getState() == 0) btnComplete.setVisibility(View.GONE);

        dialog.show();
    }

    @NonNull
    private Dialog getDialog(View view) {
        Dialog dialog = new Dialog(IntentSellForm.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sell_form_item);
        dialog.setCancelable(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // REFRESH.
                view.setBackgroundResource(R.drawable.bg_item_unselected);
                SellFormService.listSellForm(adapter, dataStorage);
            }
        });
        return dialog;
    }
}