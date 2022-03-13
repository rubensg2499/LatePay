package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER);
        sqLiteDatabase.execSQL(CREATE_TABLE_DEBT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE_DEBT);
        sqLiteDatabase.execSQL(DELETE_TABLE_CUSTOMER);
        onCreate(sqLiteDatabase);
    }
}
