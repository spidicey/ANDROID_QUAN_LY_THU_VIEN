package com.library.intent.form.fine_form;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.library.R;
import com.library.model.FineForm;
import com.library.service.FineFormService;
import com.library.service.ReaderService;
import com.library.utilities.DateFormatter;
import com.library.utilities.InputFilterMinMax;

import java.util.Calendar;

public class FormFineFormCreate extends AppCompatActivity {
    private AutoCompleteTextView actReader;
    private EditText edtReason, edtFee;
    private TextView tvDate;
    private Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_fine_form_create);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setControl();
        setData();
        setEvent();
    }

    private void setControl() {
        this.actReader = findViewById(R.id.actReader);
        this.edtReason = findViewById(R.id.edtReason);
        this.edtFee = findViewById(R.id.edtFee);
        this.tvDate = findViewById(R.id.tvDate);
        this.btnConfirm = findViewById(R.id.btnConfirm);
        this.btnCancel = findViewById(R.id.btnCancel);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setData() {
        // DEFAULT TEXT.
        tvDate.setText(String.format("Ngày lập: %s", DateFormatter.format(Calendar.getInstance().getTime())));
        // SET ADAPTER.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        actReader.setAdapter(adapter);
        ReaderService.listReader(adapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        actReader.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actReader.showDropDown();
                return false;
            }
        });
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
                if (actReader.getText().toString().isEmpty()) {
                    Toast.makeText(FormFineFormCreate.this, "Vui lòng chọn độc giả vi phạm!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int readerId = Integer.parseInt(actReader.getText().toString().split("#")[1]);
                String reason = edtReason.getText().toString();
                float fee = Float.parseFloat(edtFee.getText().toString());
                FineForm form = new FineForm(reason, fee, readerId);

                FineFormService.createFineForm(form);
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