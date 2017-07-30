package com.developtech.contactappwithsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.developtech.contactappwithsqlite.bean.ContactBean;
import com.developtech.contactappwithsqlite.db.MySqliteServices;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements TextWatcher {

    public static boolean f = false;
    private RecyclerView recycle;
    private LayoutInflater inflater;
    private ArrayList<ContactBean> al;
    private Contact_Adapter ca;
    private ContactBean objbean;
    private EditText etSearch;
    private Bundle b;

    private void init() {
        recycle = (RecyclerView) findViewById(R.id.recycle);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(this);
        MySqliteServices my = new MySqliteServices(ContactActivity.this);
        al = my.view();
        Log.e("aman", "" + al.size());
        ca = new Contact_Adapter(al, ContactActivity.this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(ContactActivity.this);
        recycle.setLayoutManager(lm);
        recycle.setAdapter(ca);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactActivity.this, AddContactActivity.class);
                b = new Bundle();
                b.putBoolean("flag", true);
                i.putExtras(b);
                startActivity(i);
            }
        });
        init();
        b = getIntent().getExtras();
        if (b != null) {
            f = b.getBoolean("flag");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Logout) {
            //Toast.makeText(ContactActivity.this,"Logout",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ContactActivity.this, LoginActivity.class);
            startActivity(i);
            ContactActivity.this.finish();
            return true;
        } else if (id == R.id.AboutUs) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String s = charSequence.toString();
        //ArrayList<ContactBean> al=new MySqliteServices(ContactActivity.this).searchByName(s);
        ca = new Contact_Adapter(al, ContactActivity.this);
        recycle.setAdapter(ca);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (f) {
            MySqliteServices my = new MySqliteServices(ContactActivity.this);
            al = my.view();
            ca = new Contact_Adapter(al, ContactActivity.this);
            recycle.setAdapter(ca);
            f = false;
        }
    }

}
