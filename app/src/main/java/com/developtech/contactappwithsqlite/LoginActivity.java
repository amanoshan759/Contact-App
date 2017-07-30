package com.developtech.contactappwithsqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.developtech.contactappwithsqlite.db.MySqliteServices;

public class LoginActivity extends AppCompatActivity implements Button.OnClickListener {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnReset, btnSignUp;

    private void initComponenets() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponenets();
        // registerForContextMenu(btnLogin);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_login, menu);
    }

    /*
        @Override
        public boolean onContextItemSelected(MenuItem item) {
            int id=item.getItemId();
            if(id==R.id.action_settings)
            {
                Toast.makeText(LoginActivity.this,"settings",Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(id==R.id.ada)
            {
                Toast.makeText(LoginActivity.this,"ada",Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onContextItemSelected(item);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
           // return super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.menu_login, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id=item.getItemId();

            if(id==R.id.action_settings)
            {
                Toast.makeText(LoginActivity.this,"settings",Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(id==R.id.ada)
            {
                Toast.makeText(LoginActivity.this,"ada",Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin: {
                // openContextMenu(view);
                if (etUsername.getText().toString().trim().isEmpty()) {
                    Snackbar.make(view, "Plzz Enter Username", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (etPassword.getText().toString().trim().isEmpty()) {
                    Snackbar.make(view, "Plzz Enter Password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    MySqliteServices my = new MySqliteServices(LoginActivity.this);
                    boolean f = my.authenticateUser(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
                    SharedPreferences sp = getSharedPreferences("spdata", MODE_PRIVATE);
                    SharedPreferences.Editor e = sp.edit();
//                    e.putString("name",etUsername.getText().toString().trim());
//                    e.putString("name1",etPassword.getText().toString().trim());
//                    e.commit();
                    if (f) {
                        Snackbar.make(view, "Login Sucess", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        e.putBoolean("Login", true);
                        e.commit();
                        Intent i = new Intent(LoginActivity.this, ContactActivity.class);
                        startActivity(i);
                        LoginActivity.this.finish();
                    } else {
                        Snackbar.make(view, "Login not seuccess", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                break;
            }
            case R.id.btnReset: {
                etUsername.setText("");
                etPassword.setText("");
                etUsername.requestFocus();
                break;
            }
            case R.id.btnSignUp: {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
                break;
            }
        }
    }
}
