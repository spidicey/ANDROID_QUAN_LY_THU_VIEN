package com.library.main.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.auth0.android.jwt.JWT;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.library.R;
import com.library.service.api.interceptor.TokenStorage;

import java.util.Objects;

public class FragmentProfile extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText edtFullName = view.findViewById(R.id.edtFullName);
        EditText edtPhone = view.findViewById(R.id.edtPhone);
        EditText edtEmail = view.findViewById(R.id.edtEmail);
        ImageView ivQRCode = view.findViewById(R.id.ivQRCode);

        try {
            JWT userData = Objects.requireNonNull(TokenStorage.getTokenData());
            String personal = userData.getClaim("personal").asString();
            String name = userData.getClaim("name").asString();

            edtFullName.setText(name);
            edtPhone.setText(userData.getClaim("phone").asString());
            edtEmail.setText(userData.getClaim("email").asString());

            boolean isSuperuser = Boolean.TRUE.equals(userData.getClaim("is_superuser").asBoolean());
            boolean isStaff = Boolean.TRUE.equals(userData.getClaim("is_staff").asBoolean());
            if (isSuperuser || isStaff) ivQRCode.setVisibility(View.GONE);
            else {
                MultiFormatWriter formatWriter = new MultiFormatWriter();
                try {
                    String data = String.format("%s ~ %s", name, personal);
                    float density = getResources().getDisplayMetrics().density;
                    int size = Math.round(ivQRCode.getWidth() * density);
                    BitMatrix bitMatrix = formatWriter.encode(data, BarcodeFormat.QR_CODE, size, size);

                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    ivQRCode.setImageBitmap(bitmap);
                } catch (WriterException ignored) {
                }
            }
        } catch (Exception ignored) {
        }
    }
}
