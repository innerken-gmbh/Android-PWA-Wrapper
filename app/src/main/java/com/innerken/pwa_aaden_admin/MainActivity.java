package com.innerken.pwa_aaden_admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.innerken.pwa_aaden_admin.databinding.ActivityMainBinding;
import com.innerken.pwa_aaden_admin.ui.UIManager;
import com.innerken.pwa_aaden_admin.webview.WebViewHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    // Globals
    private UIManager uiManager;
    private WebViewHelper webViewHelper;

    private ActivityMainBinding binding;

    @Inject
    GlobalSettingManager globalSettingManager;

    private boolean intentHandled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup Theme
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        if (!globalSettingManager.getPassword().isEmpty()) {
            if (getIntent() == null || !getIntent().getBooleanExtra("isOk", false)) {
                startActivity(new Intent(this, PasswordActivity.class));
            }
        }


        binding.config.setOnClickListener(l -> startActivity(new Intent(this, SettingsActivity.class)));


        // Setup Helpers
        uiManager = new UIManager(this, globalSettingManager);
        webViewHelper = new WebViewHelper(this, uiManager, globalSettingManager);

        // Setup App
        webViewHelper.setupWebView();
        uiManager.changeRecentAppsIcon();

        // Check for Intents
        try {
            Intent i = getIntent();
            String intentAction = i.getAction();
            // Handle URLs opened in Browser
            if (!intentHandled && intentAction != null && intentAction.equals(Intent.ACTION_VIEW)) {
                Uri intentUri = i.getData();
                String intentText = "";
                if (intentUri != null) {
                    intentText = intentUri.toString();
                }
                // Load up the URL specified in the Intent
                if (!intentText.equals("")) {
                    intentHandled = true;
                    webViewHelper.loadIntentUrl(intentText);
                }
            } else {
                // Load up the Web App
                webViewHelper.loadHome();
            }
        } catch (Exception e) {
            // Load up the Web App
            webViewHelper.loadHome();
        }
    }


    @Override
    protected void onPause() {
        webViewHelper.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webViewHelper.onResume();
        // retrieve content from cache primarily if not connected
        webViewHelper.forceCacheIfOffline();
        super.onResume();
    }

    // Handle back-press in browser
    @Override
    public void onBackPressed() {
        if (!webViewHelper.goBack()) {
            super.onBackPressed();
        }
    }
}
