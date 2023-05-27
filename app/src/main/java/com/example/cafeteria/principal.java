package com.example.cafeteria;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cafeteria.databinding.ActivityPrincipalBinding;

public class principal extends AppCompatActivity {

    ActivityPrincipalBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        refracment(new FoodFragment());

        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
            int id= item.getItemId();
            if(id==R.id.list){
                refracment(new FoodFragment());
            }
            if(id==R.id.food){
                refracment(new homeFragment());
            }
            return true;
        });
    }

    private void refracment(Fragment fragment){
        FragmentManager manaller = getSupportFragmentManager();
        FragmentTransaction transaction = manaller.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

}