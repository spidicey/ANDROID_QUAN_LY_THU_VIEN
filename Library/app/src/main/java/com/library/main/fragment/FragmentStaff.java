package com.library.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.library.R;
import com.library.main.fragment.staff.adapter.PagerHomeAdapter;

public class FragmentStaff extends Fragment {
    private ViewPager2 pager;
    private BottomNavigationView navigation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        this.pager = view.findViewById(R.id.ViewPager);
        this.navigation = view.findViewById(R.id.BottomNavigation);
        PagerHomeAdapter adapter = new PagerHomeAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());
        pager.setAdapter(adapter);
    }

    private void setEvent() {
        this.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                int itemId = navigation.getMenu().getItem(position).getItemId();
                navigation.setSelectedItemId(itemId);
            }
        });

        this.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                for (int i = 0; i < navigation.getMaxItemCount(); i++) {
                    if (navigation.getMenu().getItem(i).equals(menuItem)) {
                        pager.setCurrentItem(i);
                        break;
                    }
                }
                return true;
            }
        });
    }
}
