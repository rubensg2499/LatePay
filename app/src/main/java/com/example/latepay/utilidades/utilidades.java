package com.example.latepay.utilidades;

import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utilidades {
    public static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE customer(" +
            "customer_id INTEGER NOT NULL, " +
            "first_name TEXT NOT NULL, " +
            "last_name TEXT NOT NULL, " +
            "phone TEXT, " +
            "email TEXT, " +
            "address TEXT, " +
            "created_date TEXT, " +
            "PRIMARY KEY(customer_id))";

    public static final String CREATE_TABLE_DEBT = "CREATE TABLE debt(" +
            "debt_id INTEGER NOT NULL, " +
            "product TEXT NOT NULL, " +
            "price REAL NOT NULL, " +
            "paid INTEGER NOT NULL, " +
            "buy_date TEXT NOT NULL, " +
            "pay_date TEXT," +
            "customer_id INTEGER NOT NULL," +
            "PRIMARY KEY(debt_id)," +
            "FOREIGN KEY(customer_id) REFERENCES customer(customer_id))";

    public static final String DELETE_TABLE_CUSTOMER = "DROP TABLE IF EXISTS customer";
    public static final String DELETE_TABLE_DEBT = "DROP TABLE IF EXISTS debt";

    //Campos de la tablas customer
    public static final String TABLE_CUSTOMER = "customer";
    public static final String FIELD_CUSTOMER_ID = "customer_id";
    public static final String FIELD_FIRST_NAME = "first_name";
    public static final String FIELD_LAST_NAME = "last_name";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_CREATED_DATE = "created_date";

    //Campos de la tabla debt
    public static final String TABLE_DEBT = "debt";
    public static final String FIELD_DEBT_ID = "debt_id";
    public static final String FIELD_PRODUCT = "product";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_PAID = "paid";
    public static final String FIELD_BUY_DATE = "buy_date";
    public static final String FIELD_PAY_DATE = "pay_date";

    // Expresiones regulares

    public static final String ER_FULL_NAME = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\ ]*$";
    public static final String ER_PHONE = "^\\d{3}[\\s-.]?\\d{3}[\\s-.]?\\d{4}$";
    public static final String ER_EMAIL = "^[A-Za-z0-9._ñ%+-+-']+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";
    public static final String ER_PRICE = "^[0-9]{1,5}\\.[0-9]{1,2}$";

    public static boolean isCorrectPattern(String chain, String pattern) {
        Pattern _pattern = Pattern.compile(pattern);
        Matcher matcher = _pattern.matcher(chain);
        return matcher.find();
    }

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().equalsIgnoreCase("");
    }

    public static void showSnack(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }
}
