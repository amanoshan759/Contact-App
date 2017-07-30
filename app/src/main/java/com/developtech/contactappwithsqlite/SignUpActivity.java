package com.developtech.contactappwithsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developtech.contactappwithsqlite.db.MySqliteServices;

public class SignUpActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnSubmit;

    private void init() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUsername.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else {
                    MySqliteServices my = new MySqliteServices(SignUpActivity.this);
                    if (my.signUp(etUsername.getText().toString().trim(), etPassword.getText().toString().trim())) {
                        Toast.makeText(SignUpActivity.this, "Signup Sucess", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                        SignUpActivity.this.finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Signup not Sucess", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }
}
