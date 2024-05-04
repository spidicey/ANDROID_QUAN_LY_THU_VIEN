package com.library.intent.form.book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.library.R;
import com.library.model.Book;
import com.library.service.BookService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FormBookCreate extends AppCompatActivity {
    private static final int REQUEST_CODE = 200;
    private EditText eTitle, eBookshelf, eBookend;
    private AutoCompleteTextView eAuthor, eType;
    private Button btnThem, eImg, btnHuy;
    private ImageView imageView;
    private byte[] chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_book_create);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setControl();
        setData();
        setEvent();
    }

    private void setControl() {
        this.imageView = findViewById(R.id.imageView);
        this.eImg = findViewById(R.id.eImg);
        this.eTitle = findViewById(R.id.eTitle);
        this.eAuthor = findViewById(R.id.eAuthor);
        this.eType = findViewById(R.id.eType);
        this.eBookshelf = findViewById(R.id.eBookshelf);
        this.eBookend = findViewById(R.id.eBookend);
        this.btnThem = findViewById(R.id.btnThem);
        this.btnHuy = findViewById(R.id.btnHuy);
    }

    private void setData() {
        // SET ADAPTER.
        ArrayAdapter<String> authorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        eAuthor.setAdapter(authorAdapter);
        eType.setAdapter(categoryAdapter);

        BookService.listBookInformation(authorAdapter, categoryAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        eImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromDevice();
            }
        });

        eAuthor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                eAuthor.showDropDown();
                return false;
            }
        });

        eType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                eType.showDropDown();
                return false;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenImage == null) {
                    Toast.makeText(FormBookCreate.this, "Ảnh sách không thể trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eTitle.getText().toString().trim().isEmpty()) {
                    Toast.makeText(FormBookCreate.this, "Tên sách không thể trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eAuthor.getText().toString().trim().isEmpty()) {
                    Toast.makeText(FormBookCreate.this, "Tác giả không thể trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eType.getText().toString().trim().isEmpty()) {
                    Toast.makeText(FormBookCreate.this, "Thể loại không thể trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Book book = new Book();
                book.setImage(Base64.encodeToString(chosenImage, Base64.DEFAULT));
                book.setTitle(eTitle.getText().toString());
                book.setAuthor(eAuthor.getText().toString());
                book.setCategory(eType.getText().toString());
                if (!eBookshelf.getText().toString().isEmpty()) {
                    book.setBookshelf(Integer.parseInt(eBookshelf.getText().toString()));
                }
                if (!eBookend.getText().toString().isEmpty()) {
                    book.setShelf(Integer.parseInt(eBookend.getText().toString()));
                }
                BookService.createBook(book);
                finish();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void loadImageFromDevice() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    // CONVERT URI => BYTE.
                    chosenImage = getBytesFromUri(uri);
                    // CONVERT BYTE => BITMAP.
                    Bitmap bitmap = BitmapFactory.decodeByteArray(chosenImage, 0, chosenImage.length);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public byte[] getBytesFromUri(Uri uri) {
        ByteArrayOutputStream outputStream;
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;
            if (inputStream != null) {
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return outputStream.toByteArray();
    }
}