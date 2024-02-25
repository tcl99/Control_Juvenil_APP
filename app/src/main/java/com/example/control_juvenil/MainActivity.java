package com.example.control_juvenil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView user;
    private TextView pass;
    private boolean hasUserChanged;
    private boolean hasPassChanged;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.editUsername);
        pass = findViewById(R.id.editPassword);
        hasUserChanged = false;
        hasPassChanged = false;

        user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hasUserChanged = true;
                user.setOnTouchListener(null);
                return false;
            }
        });
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hasPassChanged = true;
                pass.setOnTouchListener(null);
                return false;
            }
        });

    }

    public void sendMessage (View view) {
        if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
            Toast.makeText(this, "No puedes dejar campos vacios", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, HomeActivity.class);
            EditText editText = (EditText) findViewById(R.id.editUsername);
            String msg = editText.getText().toString();
            intent.putExtra("LOGIN", msg);
            startActivity(intent);
        }
    }
}