package com.example.culturetracker;

import android.os.Bundle;

import com.example.culturetracker.ui.add_book.AddBookFragment;
import com.example.culturetracker.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.culturetracker.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeFragment homeFragment = new HomeFragment();
    private AddBookFragment addBookFragment = new AddBookFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //list of books to read
        List<ToReadBooks> toReadBooksList = new ArrayList<>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit(); //define the first displayed fragment
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        /*
        In ths method, we will listen to the user touch on the bottom navigation view and define a behavior based on the user selection. On each button, the app will display the corresponding fragment.
         */
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;

                case R.id.navigation_add_book:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,addBookFragment).commit();
                    return true;


            }

            return false;
        });

    }

}