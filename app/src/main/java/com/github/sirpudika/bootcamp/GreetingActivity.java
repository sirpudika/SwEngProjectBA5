package com.github.sirpudika.bootcamp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class GreetingActivity extends AppCompatActivity {
    public static final String EXTRA_USER_NAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        Intent intent = getIntent();
        String name = intent.getStringExtra(EXTRA_USER_NAME);

        TextView textView = findViewById(R.id.greetingMessage);
        textView.setText(getString(R.string.greeting_message, name));
    }
}