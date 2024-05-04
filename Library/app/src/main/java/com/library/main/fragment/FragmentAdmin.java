package com.library.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.intent.IntentLibrarian;
import com.library.intent.IntentStatistic;
import com.library.intent.IntentValidateForm;
import com.library.main.fragment.admin.RecyclerHomeAdapter;
import com.library.utilities.HomeItemDecoration;

public class FragmentAdmin extends Fragment implements RecyclerHomeAdapter.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvDisplay = view.findViewById(R.id.MainPage);
        rvDisplay.addItemDecoration(new HomeItemDecoration());
        rvDisplay.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvDisplay.setAdapter(new RecyclerHomeAdapter(this));
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getContext(), IntentLibrarian.class));
                break;
            case 1:
                startActivity(new Intent(getContext(), IntentValidateForm.class));
                break;
            case 2:
                startActivity(new Intent(getContext(), IntentStatistic.class));
                break;
        }
    }
}