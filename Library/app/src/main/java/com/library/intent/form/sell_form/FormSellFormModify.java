package com.library.intent.form.sell_form;

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
import com.library.intent.adapter.sell_form.BookListModifyAdapter;
import com.library.model.SellForm;
import com.library.model.SellFormDetail;
import com.library.service.SellFormDetailService;
import com.library.service.SellFormService;
import com.library.utilities.SpaceItemDecoration;

import java.util.List;

public class FormSellFormModify extends AppCompatActivity {
    private SellForm form;
    private boolean isValidate;
    private EditText edtReason;
    private TextView tvTitle, tvDate, tvTotal;
    private Button btnConfirm, btnCancel;
    private RecyclerView rvBookList;
    private BookListModifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_sell_form_modify);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getDataIntent();
        setControl();
        setData();
        setEvent();
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        this.form = (SellForm) intent.getSerializableExtra("item");
        this.isValidate = intent.getBooleanExtra("action", false);
    }

    private void setControl() {
        this.edtReason = findViewById(R.id.edtReason);
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
        edtReason.setText(form.getReason());
        tvTitle.setText(String.format("PHIẾU THANH LÝ #%d", form.getId()));
        tvDate.setText(String.format("Ngày lập: %s", form.getCreatedDate()));
        tvTotal.setText(String.format("%.0f VMĐ", form.getTotal()));
        // ONLY MODIFY WHEN IN PENDING OR PROCESSING STATE.
        edtReason.setEnabled(form.getState() <= 1 && !isValidate);
        btnConfirm.setVisibility(form.getState() <= 1 ? View.VISIBLE : View.GONE);
        // SETUP ADAPTER.
        SellFormDetailService.loadDataAdapter(form.getId(), adapter, isValidate);
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
                    form.setReason(edtReason.getText().toString());
                    List<SellFormDetail> details = adapter.getDetails();

                    SellFormService.updateSellForm(form, details);
                } else {
                    SellFormService.validateForm(form.getId());
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