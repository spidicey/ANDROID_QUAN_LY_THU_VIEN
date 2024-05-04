package com.library.intent.adapter.reader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.library.R;
import com.library.model.Reader;

import java.util.List;

public class ReaderAdapter extends ArrayAdapter<Reader> {
    private final Context context;
    private final int resource;
    private final List<Reader> listReader;

    public ReaderAdapter(@NonNull Context context, int resource, List<Reader> listReader) {
        super(context, resource, listReader);
        this.context = context;
        this.listReader = listReader;
        this.resource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        ImageView ivHinh = convertView.findViewById(R.id.ivUserImage);
        TextView tvTenDocGia = convertView.findViewById(R.id.tvUserName);
        TextView tvMaDocGia = convertView.findViewById(R.id.tvUserCode);
        TextView tvTrangThai = convertView.findViewById(R.id.tvTrangThai);

        Reader docGia = listReader.get(position);

        tvTenDocGia.setText(docGia.getFullName().toUpperCase());
        tvMaDocGia.setText(String.valueOf(docGia.getId()));

        if (docGia.getState()) {
            tvTrangThai.setBackground(ContextCompat.getDrawable(context, R.drawable.bgs_processing));
            tvTrangThai.setText("HOẠT ĐỘNG");
        } else {
            tvTrangThai.setBackground(ContextCompat.getDrawable(context, R.drawable.bgs_canceled));
            tvTrangThai.setText("BỊ CẤM");
        }

        if (docGia.getGender() == null) {
            ivHinh.setImageResource(R.drawable.default_user);
        } else {
            ivHinh.setImageResource(docGia.getGender() ?
                    R.drawable.default_reader_male : R.drawable.default_reader_female);
        }

        return convertView;
    }
}
