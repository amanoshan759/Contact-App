package com.developtech.contactappwithsqlite;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.developtech.contactappwithsqlite.bean.ContactBean;
import com.developtech.contactappwithsqlite.db.MySqliteServices;

import java.io.IOException;
import java.util.Calendar;

public class AddContactActivity extends AppCompatActivity implements Button.OnClickListener {

    private static boolean f = false;
    private EditText etFirstName, etLastName, etMobile, etEmail, etId;
    private Button btnSave, btnClear, btnDate;
    private Calendar cal;
    private int day, month, year;
    private TextView txtDob, txtUpload;
    private Bundle b;
    private boolean flag;
    private ContactBean objbean;
    private Spinner spinner;
    private ImageView image;
    private String s, s3;
    private CheckBox chkReminder;
    private DatePickerDialog.OnDateSetListener on = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

            txtDob.setText(String.valueOf(dayOfMonth + "/" + (month + 1) + "/" + year));

        }
    };

    private void initComponenets() {
        chkReminder = (CheckBox) findViewById(R.id.chkReminder);
        image = (ImageView) findViewById(R.id.image);
        spinner = (Spinner) findViewById(R.id.spinner);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etEmail = (EditText) findViewById(R.id.etEmail);
        txtDob = (TextView) findViewById(R.id.txtDob);
        txtUpload = (TextView) findViewById(R.id.txtUpload);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        cal = Calendar.getInstance();
        day = (cal.get(Calendar.DAY_OF_MONTH));
        month = (cal.get(Calendar.MONTH));
        year = (cal.get(Calendar.YEAR));
        txtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddContactActivity.this);
                alertBuilder.setMessage("Choose from where to upload");
                alertBuilder.setPositiveButton("Galary", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent i2 = new Intent(Intent.ACTION_PICK);
                        i2.setType("image/*");
                        startActivityForResult(i2, 102);
                        f = true;
                    }
                });
                alertBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent i2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i2, 1001);
                        f = true;
                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertBuilder.show();
            }
        });
    }
  /*  public void open(View view)
   {
       AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
       alertBuilder.setMessage("Choose from where to upload");
       alertBuilder.setPositiveButton("Galary", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

           }
       });
       alertBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
           }
       });
       AlertDialog alertDialog=alertBuilder.create();
       alertBuilder.show();
}*/
  /*  public void addItemsOnSpinner2() {

        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bm);
            s = DecodeEncode.getEncoded64ImageStringFromBitmap(bm);
            Log.e("add1", "" + s);
        } else if (requestCode == 102 && resultCode == RESULT_OK) {
            Uri u = data.getData();
            // image.setImageURI(u);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);
                image.setImageBitmap(bitmap);
                s = DecodeEncode.getEncoded64ImageStringFromBitmap(bitmap);
                Log.e("add", "" + s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initComponenets();
        b = getIntent().getExtras();
        if (b != null) {
            flag = b.getBoolean("flag");
            Log.e("flag", "" + flag);
            if (!flag) {
                Log.e("flagunder", "" + flag);
                int id = b.getInt("id");
                objbean = new MySqliteServices(AddContactActivity.this).viewById(id);
                String s = objbean.getName().toString().trim();
                String s1[] = s.split(" ");
                etFirstName.setText(s1[0]);
                etLastName.setText(s1[1]);
                etMobile.setText(objbean.getMobile());
                txtDob.setText(objbean.getDob());
                etEmail.setText(objbean.getEmail());
                s3 = objbean.getImage();
                Log.e("add123", "" + objbean.getImage());
                Bitmap bm1 = DecodeEncode.StringToBitMap(s3);
                image.setImageBitmap(bm1);
                f = false;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave: {
                if (etFirstName.getText().toString().trim().isEmpty()) {
                    Snackbar.make(view, "Plzz Enter First Name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    etFirstName.requestFocus();
                } else if (etLastName.getText().toString().trim().isEmpty()) {
                    Snackbar.make(view, "Plzz Enter Last Name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    etLastName.requestFocus();
                } else if (etMobile.getText().toString().trim().isEmpty()) {
                    Snackbar.make(view, "Plzz Enter Mobile No.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    etMobile.requestFocus();
                } else if (etEmail.getText().toString().trim().isEmpty()) {
                    Snackbar.make(view, "Plzz Enter Email Id", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    etEmail.requestFocus();
                } else if (txtDob.getText().toString().trim().isEmpty()) {
                    Snackbar.make(view, "Plzz Enter Dob", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (etEmail.getText().toString().trim() != "") {
                    MySqliteServices my = new MySqliteServices(AddContactActivity.this);
                    boolean b1 = MySqliteServices.checkEmail(etEmail.getText().toString().trim());
                    if (b1 == false) {
                        Snackbar.make(view, "Plzz Enter Valid Email", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        etEmail.requestFocus();
                    } else {
                        if (flag) {
                            ContactActivity.f = true;
                            ContactBean objbean = new ContactBean();
                            objbean.setName(etFirstName.getText().toString().trim() + " " + etLastName.getText().toString().trim());
                            objbean.setDob(txtDob.getText().toString().trim());
                            objbean.setEmail(etEmail.getText().toString().trim());
                            objbean.setMobile(etMobile.getText().toString().trim());
                            objbean.setMember(spinner.getSelectedItem().toString().trim());
                            objbean.setImage(s);
                            if (chkReminder.isChecked()) {
                                objbean.setBirthdayReminder(true);
                            } else {
                                objbean.setBirthdayReminder(false);
                            }
                            Log.e("save", "" + s);
                            if (new MySqliteServices(AddContactActivity.this).addRecord(objbean)) {
                                Snackbar.make(view, "Contact Added Successfully", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                Log.e("saveunder", "" + s);
                                AddContactActivity.this.finish();
                            } else {
                                Snackbar.make(view, "Already Exists", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            objbean.setName(etFirstName.getText().toString().trim() + " " + etLastName.getText().toString().trim());
                            objbean.setDob(txtDob.getText().toString().trim());
                            objbean.setEmail(etEmail.getText().toString().trim());
                            objbean.setMobile(etMobile.getText().toString().trim());
                            if (f) {
                                objbean.setImage(s);
                            } else {
                                objbean.setImage(s3);
                            }

                            Log.e("under", "" + objbean.getImage());
                            Log.e("9999", "" + s3);
                            if (chkReminder.isChecked()) {
                                objbean.setBirthdayReminder(true);
                            } else {
                                objbean.setBirthdayReminder(false);
                            }
                            boolean f = new MySqliteServices(AddContactActivity.this).update(objbean);
                            if (f) {
                                Snackbar.make(view, "updated", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(view, "not updated", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            ContactActivity.f = true;
                            AddContactActivity.this.finish();
                        }
                    }
                }
                break;
            }
            case R.id.btnDate: {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                DatePickerDialog dpd = new DatePickerDialog(AddContactActivity.this, on, year, month, day);
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpd.show();
                break;
            }
            case R.id.btnClear: {
                etFirstName.setText("");
                etLastName.setText("");
                etEmail.setText("");
                etMobile.setText("");
                txtDob.setText("");
                etFirstName.requestFocus();
                break;
            }
        }
    }
}

