package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.FIELD_BUY_DATE;
import static com.example.latepay.utilidades.utilidades.FIELD_CUSTOMER_ID;
import static com.example.latepay.utilidades.utilidades.FIELD_DEBT_ID;
import static com.example.latepay.utilidades.utilidades.FIELD_PAID;
import static com.example.latepay.utilidades.utilidades.FIELD_PAY_DATE;
import static com.example.latepay.utilidades.utilidades.FIELD_PRICE;
import static com.example.latepay.utilidades.utilidades.FIELD_PRODUCT;
import static com.example.latepay.utilidades.utilidades.TABLE_CUSTOMER;
import static com.example.latepay.utilidades.utilidades.TABLE_DEBT;
import static com.example.latepay.utilidades.utilidades.showSnack;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class deudas extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    List<ListElementDebt> elements;
    FloatingActionButton buttonAgregar;
    Button buttonPagarTodo;
    ListElementCustomer element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deudas);
        buttonAgregar = findViewById(R.id.buttonAgregar);
        buttonPagarTodo = findViewById(R.id.buttonPagarTodo);
        element = (ListElementCustomer) getIntent().getSerializableExtra("ListElementCustomer");
        this.setTitle(element.getFirst_name() + " " + element.getLast_name());
        elements = new ArrayList<>();
        getProducts(element.getCustomer_id());
        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> messageOptionDialog(item));
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);

        buttonAgregar.setOnClickListener(view -> openDialog(element));
        buttonPagarTodo.setOnClickListener(view -> {
            if (elements.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.mensaje_pagar_todo)
                        .setTitle(R.string.informacion)
                        .setIcon(R.drawable.ic_paid_purple_24dp);
                builder.setPositiveButton(R.string.pagar_producto, (dialogInterface, i) -> payAll(element.getCustomer_id()));
                builder.setNeutralButton(R.string.cancelar, (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.mensaje_no_productos)
                        .setTitle(R.string.informacion)
                        .setIcon(R.drawable.ic_paid_purple_24dp);
                builder.setPositiveButton(R.string.aceptar, (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void openDialog(ListElementCustomer element) {
        ExampleDialog exampleDialog = new ExampleDialog(element);
        exampleDialog.show(getSupportFragmentManager(), "Agregar cliente");
    }

    public void messageOptionDialog(ListElementDebt item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacion_hacer)
                .setTitle(R.string.informacion)
                .setIcon(R.drawable.ic_paid_purple_24dp);
        builder.setPositiveButton(R.string.pagar_producto, (dialogInterface, i) -> {
            AlertDialog.Builder pagar = new AlertDialog.Builder(this);
            pagar.setMessage("Se descontará este producto de la cuenta actual. ¿Desea continuar con esta operación?")
                    .setTitle(R.string.informacion)
                    .setIcon(R.drawable.ic_paid_purple_24dp);
            pagar.setPositiveButton(R.string.pagar_producto, (dialogInterface1, i1) -> payProduct(item.getDebt_id()));
            pagar.setNeutralButton(R.string.cancelar, (dialogInterface2, i2) -> dialogInterface2.dismiss());
            AlertDialog dialogPagar = pagar.create();
            dialogPagar.show();
        });

        builder.setNegativeButton(R.string.eliminar, (dialogInterface, i) -> {
            AlertDialog.Builder eliminar = new AlertDialog.Builder(this);
            eliminar.setMessage("Se eliminará este producto de la cuenta actual, no se contemplará en los reportes. ¿Desea continuar con esta operación?")
                    .setTitle(R.string.informacion)
                    .setIcon(R.drawable.ic_paid_purple_24dp);
            eliminar.setPositiveButton(R.string.eliminar, (dialogInterface1, i1) -> deleteProduct(item.getDebt_id()));
            eliminar.setNeutralButton(R.string.cancelar, (dialogInterface2, i2) -> dialogInterface2.dismiss());
            AlertDialog dialogEliminar = eliminar.create();
            dialogEliminar.show();
        });
        builder.setNeutralButton(R.string.cancelar, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void applyParams(long customer_id, String producto, double precio, View view) {
        registerProduct(customer_id, producto, precio, view);
        getProducts(customer_id);
        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> messageOptionDialog(item));
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);
    }

    @Override
    public void applyParams(ArrayList<String> errores) {
        for (String error : errores) {
            showSnack(findViewById(R.id.list_deudas), error);
        }
    }

    private void registerProduct(long customer_id, String producto, double precio, View view) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ContentValues cv = new ContentValues();
        cv.put(FIELD_PRODUCT, producto);
        cv.put(FIELD_PRICE, precio);
        cv.put(FIELD_PAID, "false");
        cv.put(FIELD_BUY_DATE, formatter.format(LocalDateTime.now()));
        cv.put(FIELD_PAY_DATE, "NOT_PAID");
        cv.put(FIELD_CUSTOMER_ID, customer_id);
        long result = db.insert(TABLE_DEBT, FIELD_DEBT_ID, cv);
        db.close();
    }

    public void getProducts(long customer_id) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEBT + " WHERE customer_id=" + customer_id + " AND paid='false'", null);
        elements = new ArrayList<>();
        while (cursor.moveToNext()) {
            elements.add(
                    new ListElementDebt(
                            cursor.getLong(0),
                            cursor.getString(1),
                            "MXN $" + cursor.getDouble(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getLong(6),
                            "#674FA3"
                    )
            );
        }
        cursor.close();
        db.close();
    }

    public void payProduct(long debt_id) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ContentValues cv = new ContentValues();
        cv.put(FIELD_PAID, "true");
        cv.put(FIELD_PAY_DATE, formatter.format(LocalDateTime.now()));
        db.update(TABLE_DEBT, cv, "debt_id = " + debt_id, null);
        db.close();
        elements = new ArrayList<>();
        getProducts(element.getCustomer_id());

        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> messageOptionDialog(item));
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);
    }

    public void deleteProduct(long debt_id) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        db.delete(TABLE_DEBT, "debt_id = " + debt_id, null);
        db.close();
        elements = new ArrayList<>();
        getProducts(element.getCustomer_id());
        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> messageOptionDialog(item));
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);
    }

    public void payAll(long customer_id) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ContentValues cv = new ContentValues();
        cv.put(FIELD_PAID, "true");
        cv.put(FIELD_PAY_DATE, formatter.format(LocalDateTime.now()));
        db.update(TABLE_DEBT, cv, "customer_id = " + customer_id, null);
        db.close();
        elements = new ArrayList<>();
        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> messageOptionDialog(item));
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);
    }
}