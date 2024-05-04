package com.library.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.library.R;
import com.library.intent.statistic.GraphBookBorrow;
import com.library.intent.statistic.GraphBookSell;
import com.library.intent.statistic.GraphBookType;

public class IntentStatistic extends AppCompatActivity {
    private LinearLayout graphBookType, graphBookSell, graphBookBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_statistic);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("BÁO CÁO - THỐNG KÊ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setControl();
        setEvent();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        this.graphBookType = findViewById(R.id.graphBookType);
        this.graphBookSell = findViewById(R.id.graphBookSell);
        this.graphBookBorrow = findViewById(R.id.graphBookBorrow);
    }

    private void setEvent() {
        this.graphBookType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), GraphBookType.class));
            }
        });

        this.graphBookSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), GraphBookSell.class));
            }
        });

        this.graphBookBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), GraphBookBorrow.class));
            }
        });
    }
}