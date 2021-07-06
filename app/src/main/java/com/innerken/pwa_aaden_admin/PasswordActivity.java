package com.innerken.pwa_aaden_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.innerken.pwa_aaden_admin.databinding.ActivityMainBinding;
import com.innerken.pwa_aaden_admin.databinding.ActivityPasswordBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PasswordActivity extends AppCompatActivity {

    @Inject
    GlobalSettingManager globalSettingManager;


    ActivityPasswordBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.button2.setOnClickListener(v -> {
            if (binding.editTextTextPassword.getText().toString().equals(globalSettingManager.getPassword())) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("isOk", true);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Password false", Toast.LENGTH_LONG).show();
            }
        });


    }
}