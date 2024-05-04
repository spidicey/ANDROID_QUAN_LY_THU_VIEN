package com.library.intent.auth;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.library.R;
import com.library.service.AuthenticationService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class IntentRegister extends AppCompatActivity {
    EditText edtLastName, edtFirstName, edtUsername, edtSignUpSDT, edtSignUpPassword, edtSignUpPasswordComfirm;
    Button btnBack;
    RadioButton rdNam, rdNu;
    Button btnRegister;
    CircleImageView imgAvatar;
    int request = 8080;
    String currentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_register);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setControl();
        setEvent();
    }

    private void setControl() {
        imgAvatar = findViewById(R.id.imgAvatar);
        edtLastName = findViewById(R.id.signup_last_name);
        edtFirstName = findViewById(R.id.signup_first_name);
        rdNam = findViewById(R.id.sign_up_nam);
        rdNu = findViewById(R.id.sign_up_nu);
        edtUsername = findViewById(R.id.signup_username);
        edtSignUpSDT = findViewById(R.id.signup_email);
        edtSignUpPassword = findViewById(R.id.signup_password);
        edtSignUpPasswordComfirm = findViewById(R.id.signup_confirm);
        btnBack = findViewById(R.id.back);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void setEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
                String ho, ten, userName, sdt, password, comfirmPassword;
                String gender = "";
                ho = edtLastName.getText().toString().trim();
                ten = edtFirstName.getText().toString().trim();
                userName = edtUsername.getText().toString().trim();
                sdt = edtSignUpSDT.getText().toString().trim();
                password = edtSignUpPassword.getText().toString().trim();
                comfirmPassword = edtSignUpPasswordComfirm.getText().toString().trim();

                if (ho.isEmpty() || ten.isEmpty() || userName.isEmpty() || sdt.isEmpty()
                        || password.isEmpty() || comfirmPassword.isEmpty()) {
                    Toast.makeText(IntentRegister.this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!rdNam.isChecked() && !rdNu.isChecked()) {
                    Toast.makeText(IntentRegister.this, "Vui long chon gioi tinh", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sdt.length() != 10) {
                    Toast.makeText(IntentRegister.this, "So dien thoai khong hop le", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(comfirmPassword)) {
                    Toast.makeText(IntentRegister.this, "Mat khau xac thuc khong khop", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (rdNam.isChecked()) {
                    gender = "Nam";
                } else if (rdNu.isChecked()) {
                    gender = "Nu";
                }

                Map<String, Object> registerData = new HashMap<>();
                registerData.put("phone", edtSignUpSDT.getText().toString().trim());
                registerData.put("ho", ho);
                registerData.put("ten", ten);
                registerData.put("gender", gender);
                registerData.put("username", userName);
                registerData.put("password", password);
                registerData.put("pathImage", currentPhotoPath);

                Map<String, String> data = new HashMap<>();
                data.put("username", edtUsername.getText().toString());
                data.put("password", edtSignUpPassword.getText().toString());

                AuthenticationService.checkAccount(data, IntentRegister.this, registerData);
                Toast.makeText(IntentRegister.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(IntentRegister.this);
                builder.setTitle("Exit");
                builder.setMessage("Are you want to exit");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent register_layout = new Intent(IntentRegister.this, IntentLogin.class);
                        startActivity(register_layout);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request && resultCode == Activity.RESULT_OK) {
//            Bitmap image= (Bitmap) data.getExtras().get("data");
//            imgAvatar.setImageBitmap(image);

            setPic();
            galleryAddPic();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }

        try {
            File photoFile = null;
            photoFile = createImageFile();

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.library.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, request);
            }
        } catch (IOException ignored) {
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgAvatar.getWidth();
        int targetH = imgAvatar.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imgAvatar.setImageBitmap(bitmap);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void writePassword(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("SaveImage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private void showError(EditText edtText, String s) {
        edtText.setError(s);
        edtText.requestFocus();
    }

    private void checkInput() {
        String ho, ten, userName, sdt, password, comfirmPassword;
        String gender = "";
        ho = edtLastName.getText().toString().trim();
        ten = edtFirstName.getText().toString().trim();
        userName = edtUsername.getText().toString().trim();
        sdt = edtSignUpSDT.getText().toString().trim();
        password = edtSignUpPassword.getText().toString().trim();
        comfirmPassword = edtSignUpPasswordComfirm.getText().toString().trim();
        if (ho.isEmpty()) {
            showError(edtLastName, "Vui lòng nhập trường này");
        } else if (ten.isEmpty()) {
            showError(edtFirstName, "Vui lòng nhập trường này");
        } else if (userName.isEmpty()) {
            showError(edtUsername, "Vui lòng nhập trường này");
        } else if (sdt.isEmpty()) {
            showError(edtSignUpSDT, "Vui lòng nhập trường này");
        } else if (password.isEmpty()) {
            showError(edtSignUpPassword, "Vui lòng nhập trường này");
        } else if (comfirmPassword.isEmpty()) {
            showError(edtSignUpPasswordComfirm, "Vui lòng nhập trường này");
        }
    }
}