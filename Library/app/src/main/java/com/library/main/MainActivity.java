package com.library.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.auth0.android.jwt.JWT;
import com.google.android.material.navigation.NavigationView;
import com.library.R;
import com.library.intent.auth.IntentLogin;
import com.library.main.fragment.FragmentAdmin;
import com.library.main.fragment.FragmentProfile;
import com.library.main.fragment.FragmentReader;
import com.library.main.fragment.FragmentStaff;
import com.library.service.api.interceptor.TokenStorage;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigation;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.drawer_menu);
        }
        setControl();
        setEvent();
        // OPEN HOME FRAGMENT AS FIRST.
        Fragment userPage = getUserPage();
        if (userPage != null)
            getSupportFragmentManager().beginTransaction().add(R.id.FrameLayout, userPage).commit();
        else {
            Toast.makeText(this, "Tài khoản của bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
            navigation.setCheckedItem(R.id.mLogout);
        }
    }

    private void setControl() {
        this.drawer = findViewById(R.id.DrawerLayout);
        this.navigation = findViewById(R.id.NavigationView);
        this.drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name);
        setHeaderView(navigation.getHeaderView(0));
    }

    private void setEvent() {
        this.drawer.addDrawerListener(drawerToggle);
        this.navigation.setNavigationItemSelectedListener(this);
    }

    private void setHeaderView(View headerView) {
        TextView tvName = headerView.findViewById(R.id.tvName);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);
        ImageView ivAvatar = headerView.findViewById(R.id.ivAvatar);

        try {
            JWT userData = Objects.requireNonNull(TokenStorage.getTokenData());
            String name = String.format("%s - %s",
                    Objects.requireNonNull(userData.getClaim("name").asString()).toUpperCase(),
                    userData.getClaim("user_id").asString());
            tvName.setText(name);
            tvEmail.setText(userData.getClaim("email").asString());

            String profileImagePath = Objects.requireNonNull(userData.getClaim("profile_image_path").asString());
            if (!profileImagePath.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(profileImagePath);
                ivAvatar.setImageBitmap(bitmap);
            }
        } catch (Exception ignored) {
        }
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        this.drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            if (drawer.isOpen()) drawer.close();
            else drawer.open();
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.mHome) {
            refreshFragment(getUserPage());
        } else if (itemId == R.id.mProfile) {
            setActionBarTitle("THÔNG TIN CÁ NHÂN");
            refreshFragment(new FragmentProfile());
        } else if (itemId == R.id.mLogout) {
            logout();
        }
        return true;
    }

    private void refreshFragment(Fragment fragment) {
        // REPLACE FRAGMENT.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment);
        transaction.commit();
        // CLOSE DRAWER.
        drawer.close();
    }

    private Fragment getUserPage() {
        try {
            JWT userData = Objects.requireNonNull(TokenStorage.getTokenData());
            boolean isSuperuser = Boolean.TRUE.equals(userData.getClaim("is_superuser").asBoolean());
            boolean isStaff = Boolean.TRUE.equals(userData.getClaim("is_staff").asBoolean());

            if (isSuperuser) {
                setActionBarTitle("QUẢN TRỊ VIÊN");
                return new FragmentAdmin();
            } else if (isStaff) {
                setActionBarTitle("QUẢN LÝ THƯ VIỆN");
                return new FragmentStaff();
            } else {
                setActionBarTitle("TRA CỨU SÁCH");
                return new FragmentReader();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private void logout() {
        // CLEAR ALL USER INFORMATION.
        SharedPreferences sharedPreferences = this.getSharedPreferences("Token", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        // REDIRECT TO LOGIN PAGE.
        Intent loginIntent = new Intent(MainActivity.this, IntentLogin.class);
        startActivity(loginIntent);
        finish();
    }
}