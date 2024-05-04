package com.library.intent.form.reader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.library.R;
import com.library.model.Reader;
import com.library.service.ReaderService;
import com.library.utilities.DateFormatter;

import java.util.Calendar;

public class FormReaderModify extends AppCompatActivity {
    private Reader reader;
    private ImageView ivImage;
    private Spinner spinnerGioiTinh, spinnerTrangThai;
    private LinearLayout llNgaySinh;
    private EditText edtHo, edtTen, edtSDT, edtNgaySinh;
    private Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_reader_modify);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setDataIntent();
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

        return true;
    }

    private void setDataIntent() {
        Intent intent = getIntent();
        this.reader = (Reader) intent.getSerializableExtra("item");
    }

    private void setControl() {
        ivImage = findViewById(R.id.ivImage);
        spinnerGioiTinh = findViewById(R.id.spinnerGioiTinh);
        spinnerTrangThai = findViewById(R.id.spinnerTrangThai);
        llNgaySinh = findViewById(R.id.llNgaySinh);
        edtHo = findViewById(R.id.edtHo);
        edtTen = findViewById(R.id.edtTen);
        edtSDT = findViewById(R.id.edtSDT);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setData() {
        ivImage.setImageResource(reader.getGender() ? R.drawable.default_reader_male : R.drawable.default_reader_female);
        spinnerGioiTinh.setSelection(reader.getGender() ? 0 : 1);
        spinnerTrangThai.setSelection(reader.getState() ? 0 : 1);
        edtHo.setText(reader.getLastName());
        edtTen.setText(reader.getFirstName());
        edtSDT.setText(reader.getPhone());
        edtNgaySinh.setText(reader.getBirthdate());
    }

    private void setEvent() {
        llNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reader.setLastName(edtHo.getText().toString());
                reader.setFirstName(edtTen.getText().toString());
                reader.setBirthdate(edtNgaySinh.getText().toString());
                reader.setPhone(edtSDT.getText().toString());
                reader.setGender(spinnerGioiTinh.getSelectedItem().toString().equals("NAM"));
                reader.setState(spinnerTrangThai.getSelectedItem().toString().equals("HOẠT ĐỘNG"));

                ReaderService.updateReader(reader);
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

    private void chonNgay() {
        Calendar calendar = Calendar.getInstance();
        if (reader.getBirthdate() != null) {
            calendar.setTime(DateFormatter.parse(reader.getBirthdate()));
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                edtNgaySinh.setText(DateFormatter.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}