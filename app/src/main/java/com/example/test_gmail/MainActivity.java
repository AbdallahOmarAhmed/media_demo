package com.example.test_gmail;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {

    boolean isMale;
    int color, dark_color;
    SharedPreferences preferences;
    LinearLayout background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        isMale = preferences.getBoolean("isMale",true);
        dark_color = ContextCompat.getColor(this, isMale ? R.color.dark_blue : R.color.dark_pink);
        color = ContextCompat.getColor(this, isMale ? R.color.blue : R.color.pink);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

//        background = findViewById(R.id.background);
//        background.setBackgroundColor(color);



    }
}
