package com.library.main.fragment.staff.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.library.main.fragment.staff.fragment.FragmentInformation;
import com.library.main.fragment.staff.fragment.FragmentManagement;

public class PagerHomeAdapter extends FragmentStateAdapter {
    private static final int PAGE_COUNT = 2;

    public PagerHomeAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new FragmentInformation() : new FragmentManagement();
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}