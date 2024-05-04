package com.library.intent.form.import_form;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.intent.adapter.import_form.BookListModifyAdapter;
import com.library.model.ImportForm;
import com.library.model.ImportFormDetail;
import com.library.service.ImportFormDetailService;
import com.library.service.ImportFormService;
import com.library.utilities.SpaceItemDecoration;

import java.util.List;

public class FormImportFormModify extends AppCompatActivity {
    private ImportForm form;
    private boolean isValidate;
    private EditText edtSupplier;
    private TextView tvTitle, tvDate, tvTotal;
    private Button btnConfirm, btnCancel;
    private RecyclerView rvBookList;
    private BookListModifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_import_form_modify);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getDataIntent();
        setControl();
        setData();
        setEvent();
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        this.form = (ImportForm) intent.getSerializableExtra("item");
        this.isValidate = intent.getBooleanExtra("action", false);
    }

    private void setControl() {
        this.edtSupplier = findViewById(R.id.edtSupplier);
        this.btnConfirm = findViewById(R.id.btnConfirm);
        this.btnCancel = findViewById(R.id.btnCancel);
        this.tvTitle = findViewById(R.id.tvTitle);
        this.tvDate = findViewById(R.id.tvDate);
        this.tvTotal = findViewById(R.id.tvTotal);
        this.rvBookList = findViewById(R.id.rvBookList);
        this.adapter = new BookListModifyAdapter(tvTotal, isValidate ? Integer.MAX_VALUE : form.getState());
    }

    @SuppressLint("DefaultLocale")
    private void setData() {
        // SETUP DATA FROM INTENT.
        edtSupplier.setText(form.getSupplier());
        tvTitle.setText(String.format("PHIẾU NHẬP #%d", form.getId()));
        tvDate.setText(String.format("Ngày lập: %s", form.getCreatedDate()));
        tvTotal.setText(String.format("%.0f VNĐ", form.getTotal()));
        // ONLY MODIFY WHEN IN PENDING OR PROCESSING STATE.
        edtSupplier.setEnabled(form.getState() <= 1 && !isValidate);
        btnConfirm.setVisibility(form.getState() <= 1 ? View.VISIBLE : View.GONE);
        // SETUP ADAPTER.
        ImportFormDetailService.loadDataAdapter(form.getId(), adapter, isValidate);
        // SETUP RECYCLERVIEW.
        rvBookList.addItemDecoration(new SpaceItemDecoration(30));
        rvBookList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBookList.setAdapter(adapter);
    }

    private void setEvent() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate) {
                    form.setSupplier(edtSupplier.getText().toString());
                    List<ImportFormDetail> details = adapter.getDetails();

                    ImportFormService.updateImportForm(form, details);
                } else {
                    ImportFormService.validateForm(form.getId());
                }
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}