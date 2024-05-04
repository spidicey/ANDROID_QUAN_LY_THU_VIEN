package com.library.intent.form.import_form;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.intent.adapter.import_form.BookListCreateAdapter;
import com.library.model.ImportForm;
import com.library.model.ImportFormDetail;
import com.library.service.BookService;
import com.library.service.ImportFormService;
import com.library.utilities.DateFormatter;
import com.library.utilities.SpaceItemDecoration;

import java.util.Calendar;
import java.util.List;

public class FormImportFormCreate extends AppCompatActivity {
    private EditText edtSupplier;
    private TextView tvDate, tvTotal;
    private Button btnConfirm, btnCancel;
    private RecyclerView rvBookList;
    private BookListCreateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_import_form_create);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setControl();
        setData();
        setEvent();
    }

    private void setControl() {
        this.edtSupplier = findViewById(R.id.edtSupplier);
        this.btnConfirm = findViewById(R.id.btnConfirm);
        this.btnCancel = findViewById(R.id.btnCancel);
        this.tvDate = findViewById(R.id.tvDate);
        this.tvTotal = findViewById(R.id.tvTotal);
        this.adapter = new BookListCreateAdapter(tvTotal);
        this.rvBookList = findViewById(R.id.rvBookList);
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        // DEFAULT TEXT.
        tvDate.setText(String.format("Ngày lập: %s", DateFormatter.format(Calendar.getInstance().getTime())));
        tvTotal.setText("0 VNĐ");
        // SETUP ADAPTER.
        BookService.listBook(adapter);
        // SETUP RECYCLERVIEW.
        rvBookList.addItemDecoration(new SpaceItemDecoration(30));
        rvBookList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBookList.setAdapter(adapter);
    }

    private void setEvent() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String supplier = edtSupplier.getText().toString();
                if (supplier.isEmpty()) {
                    Toast.makeText(FormImportFormCreate.this, "Thông tin nhà xuất bản không thể trống!", Toast.LENGTH_SHORT).show();
                }

                ImportForm form = new ImportForm(supplier);
                List<ImportFormDetail> details = adapter.getDetails();

                ImportFormService.createImportForm(form, details);
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
