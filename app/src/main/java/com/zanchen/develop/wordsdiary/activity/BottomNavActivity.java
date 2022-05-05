package com.zanchen.develop.wordsdiary.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.zanchen.develop.wordsdiary.R;
import com.zanchen.develop.wordsdiary.databinding.ActivityBottomNavBinding;

public class BottomNavActivity extends AppCompatActivity {

    private ActivityBottomNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_nav);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);

    }

}