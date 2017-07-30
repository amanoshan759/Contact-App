package com.developtech.contactappwithsqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developtech.contactappwithsqlite.bean.ContactBean;
import com.developtech.contactappwithsqlite.db.MySqliteServices;

public class ViewContactActivity extends AppCompatActivity implements ImageButton.OnClickListener {

    private Bundle b;
    private TextView txtMobile, txtName;
    private ImageButton btnCall, btnMsg;
    private ContactBean objbean;
    private RecyclerView recycle;
    private LayoutInflater inflater;
    private ImageView image1;

    private void init() {
        recycle = (RecyclerView) findViewById(R.id.recycle);
        txtMobile = (TextView) findViewById(R.id.txtMobile);
        txtName = (TextView) findViewById(R.id.txtName);
        btnCall = (ImageButton) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);
        btnMsg = (ImageButton) findViewById(R.id.btnMsg);
        btnMsg.setOnClickListener(this);
        image1 = (ImageView) findViewById(R.id.image1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();
        b = getIntent().getExtras();
        if (b != null) {
            int id = b.getInt("id");
            objbean = new MySqliteServices(ViewContactActivity.this).viewById(id);
            Log.e("1234", "" + objbean.getName());
            Log.e("1234", "" + objbean.getMobile());
            txtName.setText(objbean.getName());
            txtMobile.setText(objbean.getMobile());
            String s = objbean.getImage();
            Log.e("q1234567", "" + s);
            Bitmap bm = DecodeEncode.StringToBitMap(s);
            image1.setImageBitmap(bm);
            // objbean=new MySqliteServices(ViewContactActivity.this).viewById(id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewContactActivity.this);
            builder.setMessage("Do You Want To Delete");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (new MySqliteServices(ViewContactActivity.this).delete(objbean.getContactid())) {
                        ContactActivity.f = true;
                        ViewContactActivity.this.finish();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return true;
        } else if (id == R.id.Share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "" + objbean.getMobile();
            String shareSub = "Your subject here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
            return true;
        } else if (id == R.id.Edit) {
            Intent i = new Intent(ViewContactActivity.this, AddContactActivity.class);
            b = new Bundle();
            b.putBoolean("flag", false);
            b.putInt("id", objbean.getContactid());
            i.putExtras(b);
            startActivity(i);
            ViewContactActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_view, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Delete) {
            Toast.makeText(ViewContactActivity.this, "Del", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.Share) {
            Toast.makeText(ViewContactActivity.this, "Share", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.Edit) {
            Toast.makeText(ViewContactActivity.this, "Share", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCall: {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + objbean.getMobile()));
                if (ActivityCompat.checkSelfPermission(ViewContactActivity.this, "android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ViewContactActivity.this, new String[]{"android.permission.CALL_PHONE"}, 1);
                }
                startActivity(i);
                break;
            }
            case R.id.btnMsg: {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("smsto:" + objbean.getMobile()));
                i.putExtra("sms_body", "hello");
                startActivity(i);
                break;
            }
        }
    }

}
