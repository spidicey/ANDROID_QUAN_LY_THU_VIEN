package com.library.intent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.intent.adapter.validate_form.ValidateFormAdapter;
import com.library.intent.form.import_form.FormImportFormModify;
import com.library.intent.form.sell_form.FormSellFormModify;
import com.library.model.ImportForm;
import com.library.model.SellForm;
import com.library.service.ValidateFormService;
import com.library.utilities.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntentValidateForm extends AppCompatActivity implements ValidateFormAdapter.OnItemClickListener {
    private final List<SellForm> listSellForm = new ArrayList<>();
    private final List<ImportForm> listImportForm = new ArrayList<>();
    private ToggleButton tglSwitch;
    private RecyclerView rvMainDisplay;
    private ValidateFormAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_validate_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("KIỂM DUYỆT PHIẾU");
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
        else if (itemId == R.id.mReload)
            ValidateFormService.listValidateForm(listSellForm, listImportForm, adapter, tglSwitch.isChecked());

        return true;
    }

    private void setControl() {
        this.tglSwitch = findViewById(R.id.tglSwitch);
        this.rvMainDisplay = findViewById(R.id.rvMainDisplay);
        this.adapter = new ValidateFormAdapter(this);
    }

    private void setData() {
        // SETUP RECYCLER VIEW.
        rvMainDisplay.addItemDecoration(new SpaceItemDecoration(30));
        rvMainDisplay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMainDisplay.setAdapter(adapter);
    }

    private void setEvent() {
        tglSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tglSwitch.setBackground(getDrawable(R.drawable.bgs_canceled));
                    adapter.setData(listSellForm.stream().map(form -> (Object) form).collect(Collectors.toList()));
                } else {
                    tglSwitch.setBackground(getDrawable(R.drawable.bgs_processing));
                    adapter.setData(listImportForm.stream().map(form -> (Object) form).collect(Collectors.toList()));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //REFRESH.
        ValidateFormService.listValidateForm(listSellForm, listImportForm, adapter, tglSwitch.isChecked());
    }

    @Override
    public void onItemClick(View view, int position) {
        Object form = adapter.getItem(position);
        if (form instanceof SellForm) {
            Intent intent = new Intent(IntentValidateForm.this, FormSellFormModify.class);
            intent.putExtra("item", (SellForm) form);
            intent.putExtra("action", true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(IntentValidateForm.this, FormImportFormModify.class);
            intent.putExtra("item", (ImportForm) form);
            intent.putExtra("action", true);
            startActivity(intent);
        }
    }
}