package com.library.intent.adapter.librarian;

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
import com.library.model.Librarian;

import java.util.List;

public class LibrarianAdapter extends ArrayAdapter<Librarian> {
    private final Context context;
    private final int resource;
    private final List<Librarian> listLibrarian;

    public LibrarianAdapter(@NonNull Context context, int resource, List<Librarian> listLibrarian) {
        super(context, resource, listLibrarian);
        this.context = context;
        this.listLibrarian = listLibrarian;
        this.resource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        ImageView ivHinh = convertView.findViewById(R.id.ivUserImage);
        TextView tvTenThuThu = convertView.findViewById(R.id.tvUserName);
        TextView tvMaThuThu = convertView.findViewById(R.id.tvUserCode);
        TextView tvTrangThai = convertView.findViewById(R.id.tvTrangThai);

        Librarian thuThu = listLibrarian.get(position);

        tvTenThuThu.setText(thuThu.getFullName().toUpperCase());
        tvMaThuThu.setText(String.valueOf(thuThu.getId()));

        if (thuThu.getState()) {
            tvTrangThai.setBackground(ContextCompat.getDrawable(context, R.drawable.bgs_processing));
            tvTrangThai.setText("HOẠT ĐỘNG");
        } else {
            tvTrangThai.setBackground(ContextCompat.getDrawable(context, R.drawable.bgs_canceled));
            tvTrangThai.setText("BỊ CẤM");
        }

        if (thuThu.getGender() == null) {
            ivHinh.setImageResource(R.drawable.default_user);
        } else {
            ivHinh.setImageResource(thuThu.getGender() ?
                    R.drawable.default_librarian_male : R.drawable.default_librarian_female);
        }

        return convertView;
    }
}
