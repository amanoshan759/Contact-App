package com.developtech.contactappwithsqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.developtech.contactappwithsqlite.bean.ContactBean;

import java.util.ArrayList;

public class MySqliteServices {
    public SQLiteDatabase db;
    private Context c;
    private MySqliteOpenHelper helper;

    public MySqliteServices(Context c) {
        this.c = c;
        helper = new MySqliteOpenHelper(c);
        db = helper.getWritableDatabase();
    }

    public static boolean checkEmail(String s) {
        int a = s.indexOf("@");
        int b = s.lastIndexOf("@");
        if (!(s.endsWith(".com"))) {
            return false;
        } else if (s.startsWith("@") || s.endsWith("@") || s.endsWith("@.com") || s.startsWith(".")) {
            return false;
        } else if (s.startsWith("0") || s.startsWith("1") || s.startsWith("2") || s.startsWith("3") || s.startsWith("4") || s.startsWith("5") || s.startsWith("6") || s.startsWith("7") || s.startsWith("8") || s.startsWith("9")) {
            return false;
        } else if (s.contains(".@") || (!s.contains("@")) || (a != b) || (s.contains("!")) || (s.contains("#")) || (s.contains("$")) || (s.contains("%")) || (s.contains("^") || (s.contains("&"))) || (s.contains("*")) || (s.contains("-")) || (s.contains("+")) || (s.contains(">")) || (s.contains("<")) || (s.contains("?")) || (s.contains("/")) || (s.contains(":")) || (s.contains(";"))) {
            return false;
        }
        return true;
    }

    public boolean addRecord(ContactBean appbean) {
        ContentValues cv = null;
        try {
            cv = new ContentValues();
            //cv.put(MySqliteOpenHelper.VARIABLE_CONTACTID,appbean.getContactid());
            cv.put(MySqliteOpenHelper.VARIABLE_NAME, appbean.getName());
            cv.put(MySqliteOpenHelper.VARIABLE_MOBILE, appbean.getMobile());
            cv.put(MySqliteOpenHelper.VARIABLE_EMAIL, appbean.getEmail());
            cv.put(MySqliteOpenHelper.VARIABLE_DOB, appbean.getDob());
            cv.put(MySqliteOpenHelper.VARIABLE_IMAGE, appbean.getImage());
            Log.e("recordrem", "" + appbean.isBirthdayReminder());
            if (appbean.isBirthdayReminder()) {
                cv.put(MySqliteOpenHelper.VARIABLE_BIRTHDAY, 1);
            } else {
                cv.put(MySqliteOpenHelper.VARIABLE_BIRTHDAY, 0);
            }
            Log.e("record", "" + appbean.getImage());
            long id = db.insert(MySqliteOpenHelper.TABLE_NAME, null, cv);
            if (id > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            db.close();
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            long i = db.delete(MySqliteOpenHelper.TABLE_NAME, MySqliteOpenHelper.VARIABLE_CONTACTID + "=" + id, null);
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            db.close();
        }
        return false;
    }

    public ArrayList<ContactBean> view() {
        ArrayList<ContactBean> l = new ArrayList<ContactBean>();
        try {
            String selectQuery = "SELECT  * FROM " + MySqliteOpenHelper.TABLE_NAME;
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {
                    ContactBean appBean = new ContactBean();
                    appBean.setContactid(c.getInt((c.getColumnIndex(MySqliteOpenHelper.VARIABLE_CONTACTID))));
                    appBean.setName((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_NAME))));
                    appBean.setMobile((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_MOBILE))));
                    appBean.setEmail((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_EMAIL))));
                    appBean.setDob(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_DOB)));
                    appBean.setImage(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_IMAGE)));
                    Log.e("view", "" + appBean.getImage());
                    Log.e("column", "" + c.getColumnIndex(MySqliteOpenHelper.VARIABLE_BIRTHDAY));
                    if ((Integer.parseInt(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_BIRTHDAY)))) == 1) {
                        appBean.setBirthdayReminder(true);
                    } else {
                        appBean.setBirthdayReminder(false);
                    }
                    Log.e("777", "" + appBean.isBirthdayReminder());
                    l.add(appBean);
                } while (c.moveToNext());
            }
            Log.e("aman3", "" + l.size());
            return l;
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            db.close();
        }
        return null;
    }

    public ContactBean viewById(int id) {
        ArrayList<ContactBean> l = new ArrayList<ContactBean>();
        try {
            String selectQuery = "SELECT  * FROM " + MySqliteOpenHelper.TABLE_NAME + " WHERE " + MySqliteOpenHelper.VARIABLE_CONTACTID + "=" + id;
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                ContactBean appBean = new ContactBean();
                appBean.setContactid(c.getInt((c.getColumnIndex(MySqliteOpenHelper.VARIABLE_CONTACTID))));
                appBean.setName((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_NAME))));
                appBean.setMobile((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_MOBILE))));
                appBean.setEmail((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_EMAIL))));
                appBean.setDob(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_DOB)));
                appBean.setImage(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_IMAGE)));
                Log.e("columns123", "" + appBean.getImage());
                Log.e("column", "" + c.getColumnIndex(MySqliteOpenHelper.VARIABLE_BIRTHDAY));
                if ((Integer.parseInt(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_BIRTHDAY)))) == 1) {
                    appBean.setBirthdayReminder(true);
                } else {
                    appBean.setBirthdayReminder(false);
                }
                return appBean;
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            db.close();
        }
        return null;
    }

    public boolean authenticateUser(String username, String password) {
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + MySqliteOpenHelper.TABLE_NAME1 + " WHERE " + MySqliteOpenHelper.VARIABLE_USERNAME + " = ?; ", new String[]{username});
            Cursor cursor1 = db.rawQuery("SELECT * FROM " + MySqliteOpenHelper.TABLE_NAME1 + " WHERE " + MySqliteOpenHelper.VARIABLE_PASSWORD + " = ?; ", new String[]{password});
            if (cursor.moveToNext() & cursor1.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            db.close();
        }
        return false;
    }

    public boolean signUp(String username, String password) {
        ContentValues cv = null;
        try {
            cv = new ContentValues();
            cv.put(MySqliteOpenHelper.VARIABLE_USERNAME, username);
            cv.put(MySqliteOpenHelper.VARIABLE_PASSWORD, password);
            long id = db.insert(MySqliteOpenHelper.TABLE_NAME1, null, cv);
            if (id > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            db.close();
        }
        return false;
    }

    public boolean update(ContactBean appbean) {
        ContentValues cv = null;
        try {
            cv = new ContentValues();
            cv.put(MySqliteOpenHelper.VARIABLE_NAME, appbean.getName());
            cv.put(MySqliteOpenHelper.VARIABLE_MOBILE, appbean.getMobile());
            cv.put(MySqliteOpenHelper.VARIABLE_EMAIL, appbean.getEmail());
            cv.put(MySqliteOpenHelper.VARIABLE_DOB, appbean.getDob());
            cv.put(MySqliteOpenHelper.VARIABLE_IMAGE, appbean.getImage());
            Log.e("update", "" + appbean.getImage());
            if (appbean.isBirthdayReminder()) {
                cv.put(MySqliteOpenHelper.VARIABLE_BIRTHDAY, 1);
            } else {
                cv.put(MySqliteOpenHelper.VARIABLE_BIRTHDAY, 0);
            }
            long i = db.update(MySqliteOpenHelper.TABLE_NAME, cv, MySqliteOpenHelper.VARIABLE_CONTACTID + "=" + appbean.getContactid(), null);
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            db.close();
        }
        return false;
    }

    public ArrayList<ContactBean> searchByName(String s) {
        ArrayList<ContactBean> al2 = new ArrayList<ContactBean>();
        String s1 = s.toLowerCase();
        try {
            Cursor c = db.query(MySqliteOpenHelper.TABLE_NAME, new String[]{MySqliteOpenHelper.VARIABLE_NAME}, MySqliteOpenHelper.VARIABLE_NAME + " LIKE '%" + s1 + "%' COLLATE NOCASE", null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    ContactBean appBean = new ContactBean();
                    appBean.setContactid(c.getInt((c.getColumnIndex(MySqliteOpenHelper.VARIABLE_CONTACTID))));
                    appBean.setName((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_NAME))));
                    appBean.setMobile((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_MOBILE))));
                    appBean.setEmail((c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_EMAIL))));
                    appBean.setDob(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_DOB)));
                    appBean.setImage(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_IMAGE)));
                    Log.e("view", "" + appBean.getImage());
                    Log.e("column", "" + c.getColumnIndex(MySqliteOpenHelper.VARIABLE_BIRTHDAY));
                    if ((Integer.parseInt(c.getString(c.getColumnIndex(MySqliteOpenHelper.VARIABLE_BIRTHDAY)))) == 1) {
                        appBean.setBirthdayReminder(true);
                    } else {
                        appBean.setBirthdayReminder(false);
                    }
                    Log.e("777", "" + appBean.isBirthdayReminder());
                    al2.add(appBean);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }
}
