package com.library.intent.statistic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.library.R;
import com.library.service.StatisticService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GraphBookBorrow extends AppCompatActivity {
    private ImageView btnDownload;
    private TableLayout tblBookTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_graph_book_borrow);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("TOP ĐẦU SÁCH NỔI BẬT");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setControl();
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        this.btnDownload = findViewById(R.id.btnDownload);
        this.tblBookTop = findViewById(R.id.tblBookTop);
    }

    private void setData() {
        StatisticService.graphBookBorrow(tblBookTop);
        this.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tblBookTop.setDrawingCacheEnabled(true);
                tblBookTop.buildDrawingCache();
                Bitmap bm = tblBookTop.getDrawingCache();
                File file = getApplicationContext().getExternalFilesDir("");
                Document document = new Document();
                try {
                    File yourFile = new File(file + "THONG_KE_TOP_SACH.pdf");
                    yourFile.createNewFile();
                    PdfWriter.getInstance(document, new FileOutputStream(yourFile));
                } catch (DocumentException | IOException e) {
                    throw new RuntimeException(e);
                }
                document.open();
                ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                Image image;
                try {
                    image = Image.getInstance(stream3.toByteArray());
                } catch (BadElementException | IOException e) {
                    throw new RuntimeException(e);
                }
                float scalar = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - 0) / image.getWidth()) * 100;
                image.scalePercent(scalar);
                image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

                try {
                    document.add(image);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(GraphBookBorrow.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                document.close();
            }
        });
    }
}