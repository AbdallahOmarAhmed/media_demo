package com.example.test_gmail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.webkit.WebView;

public class TermsActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Bundle b = getIntent().getExtras();
        int color = b.getInt("color");
        int darkColor = b.getInt("dark");
        ActionBar toolBar = getSupportActionBar();
        toolBar.setDisplayHomeAsUpEnabled(true);
        toolBar.setBackgroundDrawable(new ColorDrawable(color));
        getWindow().setStatusBarColor(darkColor);

        webView = findViewById(R.id.web_view);
        webView.loadUrl("file:///android_asset/terms.html");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}