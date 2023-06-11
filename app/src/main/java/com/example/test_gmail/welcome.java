package com.example.test_gmail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class welcome extends AppCompatActivity {

    boolean isMale;
    TextView termsText, registerText, termsError;
    MaterialCardView maleButton, femaleButton;
    TextInputEditText nameText,mailText;
    TextInputLayout nameBox, mailBox;
    AppCompatCheckBox termsBox;
    LinearLayout background;
    Window window;
    final int [][] states = {{android.R.attr.state_checked}, {}};
    int darkBlue, darkPink, blue, pink, black, white, gray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        blue = ContextCompat.getColor(this, R.color.blue);
        pink = ContextCompat.getColor(this, R.color.pink);
        darkBlue = ContextCompat.getColor(this, R.color.dark_blue);
        darkPink = ContextCompat.getColor(this, R.color.dark_pink);
        black = ContextCompat.getColor(this, R.color.black);
        white = ContextCompat.getColor(this, R.color.white);
        gray = ContextCompat.getColor(this, R.color.gray);

        isMale = true;
        window = getWindow();
        maleButton = findViewById(R.id.male_button);
        femaleButton = findViewById(R.id.female_button);
        nameText = findViewById(R.id.name_edit_text);
        mailText = findViewById(R.id.mail_edit_text);
        nameBox = findViewById(R.id.name_text_layout);
        mailBox = findViewById(R.id.mail_text_layout);
        termsBox = findViewById(R.id.terms);
        termsText = findViewById(R.id.terms_text);
        termsError = findViewById(R.id.terms_error);
        registerText = findViewById(R.id.register);
        background = findViewById(R.id.background);

        femaleButton.setOnClickListener(onClickCardView(maleButton));
        maleButton.setOnClickListener(onClickCardView(femaleButton));
        maleButton.setEnabled(false);
        maleButton.setStrokeColor(blue);

        int []colors = {blue, gray};
        CompoundButtonCompat.setButtonTintList(termsBox, new ColorStateList(states, colors));

        nameText.addTextChangedListener(onTextChanged(nameText, nameBox,
                "[a-zA-Z]+", "name should contains letters only"));
        mailText.addTextChangedListener(onTextChanged(mailText, mailBox,
                "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", "please enter a valid email"));


    }

    public void register(View view){
        boolean valid = true;
        if(nameBox.getError()!=null) valid = false;
        else if (nameText.getText().toString().length()==0) {
            valid = false;
            nameBox.setError("please enter your name");
        }
        if(mailBox.getError()!=null) valid = false;
        else if (mailText.getText().toString().length()==0) {
            valid = false;
            mailBox.setError("please enter supervisor email");
        }
        termsError.setVisibility(!termsBox.isChecked() ? View.VISIBLE : View.INVISIBLE);
        if(!termsBox.isChecked()){
            valid = false;
        }
        if(valid){
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putString("name", nameText.getText().toString())
                    .putString("mail", mailText.getText().toString())
                    .putBoolean("isMale", isMale).commit();
            Intent i = new Intent(welcome.this, MainActivity.class);
            startActivity(i);
        }
    }
    public void openTerms(View view){
        Intent i = new Intent(welcome.this, TermsActivity.class);
        if(isMale){
        i.putExtra("color", blue);
        i.putExtra("dark", darkBlue);
        }else{
            i.putExtra("color", pink);
            i.putExtra("dark", darkPink);
        }
        startActivity(i);
    }

    protected View.OnClickListener onClickCardView(MaterialCardView offView){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCardView onView = (MaterialCardView) v;
                int defaultColor = isMale ? pink : blue;
                int []colors = {defaultColor, gray};

                CompoundButtonCompat.setButtonTintList(termsBox, new ColorStateList(states, colors));
                background.setBackgroundColor(defaultColor);
                window.setStatusBarColor(defaultColor); // dark
                termsText.setTextColor(defaultColor);
                registerText.setBackgroundColor(defaultColor);

                offView.setEnabled(true);
                offView.setStrokeColor(white);

                onView.setEnabled(false);
                onView.setStrokeColor(defaultColor);

                isMale = !isMale;
            }
        };
    }

    protected TextWatcher onTextChanged(TextInputEditText text, TextInputLayout layout, String pattern, String error){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String input = text.getText().toString().trim();
                if(input.length()==0 || input.matches(pattern)) layout.setError(null);
                else layout.setError(error);
            }
        };
    }

}