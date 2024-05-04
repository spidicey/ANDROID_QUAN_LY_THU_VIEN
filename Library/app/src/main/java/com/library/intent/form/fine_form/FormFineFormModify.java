package com.library.intent.form.fine_form;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.library.R;
import com.library.model.FineForm;
import com.library.service.FineFormService;
import com.library.service.ReaderService;
import com.library.utilities.InputFilterMinMax;

public class FormFineFormModify extends AppCompatActivity {
    private FineForm form;
    private AutoCompleteTextView actReader;
    private EditText edtReason, edtFee;
    private TextView tvTitle, tvDate;
    private Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_fine_form_modify);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getDataIntent();
        setControl();
        setData();
        setEvent();
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        this.form = (FineForm) intent.getSerializableExtra("item");
    }

    private void setControl() {
        this.actReader = findViewById(R.id.actReader);
        this.edtReason = findViewById(R.id.edtReason);
        this.edtFee = findViewById(R.id.edtFee);
        this.tvTitle = findViewById(R.id.tvTitle);
        this.tvDate = findViewById(R.id.tvDate);
        this.btnConfirm = findViewById(R.id.btnConfirm);
        this.btnCancel = findViewById(R.id.btnCancel);
    }

    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    private void setData() {
        // SETUP DATA FROM INTENT.
        tvTitle.setText(String.format("PHIẾU PHẠT #%d", form.getId()));
        edtFee.setText(String.valueOf(form.getFee()));
        edtReason.setText(form.getReason());
        // DEFAULT TEXT.
        tvDate.setText(String.format("Ngày lập: %s", form.getCreatedDate()));
        // SET ADAPTER.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        actReader.setAdapter(adapter);
        ReaderService.listReader(adapter, actReader, form.getReaderId());
        // ONLY MODIFY WHEN NOT DELETED.
        if (form.isDeleted()) {
            edtFee.setEnabled(false);
            edtReason.setEnabled(false);
            btnConfirm.setVisibility(View.GONE);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        edtFee.setFilters(new InputFilter[]{new InputFilterMinMax(0, Integer.MAX_VALUE)});
        edtReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtReason.getLineCount() > 5) {
                    String newText = s.toString().substring(0, s.length() - 1);
                    edtReason.setText(newText);
                    edtReason.setSelection(newText.length());
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form.setReason(edtReason.getText().toString());
                form.setFee(Float.parseFloat(edtFee.getText().toString()));

                FineFormService.updateFineForm(form);
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