package com.library.intent.form.borrow_form;

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
import com.library.intent.adapter.borrow_form.BookListModifyAdapter;
import com.library.model.BorrowForm;
import com.library.model.BorrowFormDetail;
import com.library.service.BorrowFormDetailService;
import com.library.service.BorrowFormService;
import com.library.service.ReaderService;
import com.library.utilities.SpaceItemDecoration;

import java.util.List;

public class FormBorrowFormModify extends AppCompatActivity {
    private BorrowForm form;
    private TextView tvTitle;
    private EditText edtReader, edtCreatedDate, edtExpiredDate;
    private Button btnConfirm, btnCancel;
    private RecyclerView rvBookList;
    private BookListModifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_borrow_form_modify);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setDataIntent();
        setControl();
        setData();
        setEvent();
    }

    private void setDataIntent() {
        Intent intent = getIntent();
        this.form = (BorrowForm) intent.getSerializableExtra("item");
    }

    private void setControl() {
        this.tvTitle = findViewById(R.id.tvTitle);
        this.btnConfirm = findViewById(R.id.btnConfirm);
        this.btnCancel = findViewById(R.id.btnCancel);
        this.edtReader = findViewById(R.id.edtReader);
        this.edtCreatedDate = findViewById(R.id.edtCreatedDate);
        this.edtExpiredDate = findViewById(R.id.edtExpiredDate);
        this.rvBookList = findViewById(R.id.rvBookList);
        this.adapter = new BookListModifyAdapter(form.getState());
    }

    @SuppressLint("DefaultLocale")
    private void setData() {
        // SETUP DATA FROM INTENT.
        tvTitle.setText(String.format("PHIẾU MƯỢN - TRẢ SÁCH #%d", form.getId()));
        ReaderService.setReaderEditText(edtReader, form.getReaderId());
        edtCreatedDate.setText(form.getCreatedDate());
        edtExpiredDate.setText(form.getExpiredDate());
        // ONLY MODIFY WHEN IN PENDING OR PROCESSING STATE.
        btnConfirm.setVisibility(form.getState() == 0 ? View.VISIBLE : View.GONE);
        // SETUP ADAPTER.
        BorrowFormDetailService.loadDataAdapter(form, adapter);
        // SETUP RECYCLERVIEW.
        rvBookList.addItemDecoration(new SpaceItemDecoration(30));
        rvBookList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBookList.setAdapter(adapter);
    }

    private void setEvent() {
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BorrowFormDetail> details = adapter.getDetails();

                BorrowFormService.updateBorrowForm(form, details);
                finish();
            }
        });
    }
}