package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.FIELD_BUY_DATE;
import static com.example.latepay.utilidades.utilidades.FIELD_CUSTOMER_ID;
import static com.example.latepay.utilidades.utilidades.FIELD_DEBT_ID;
import static com.example.latepay.utilidades.utilidades.FIELD_PAID;
import static com.example.latepay.utilidades.utilidades.FIELD_PAY_DATE;
import static com.example.latepay.utilidades.utilidades.FIELD_PRICE;
import static com.example.latepay.utilidades.utilidades.FIELD_PRODUCT;
import static com.example.latepay.utilidades.utilidades.TABLE_DEBT;

import android.content.ContentValues;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deudas);
        buttonAgregar = findViewById(R.id.buttonAgregar);
        buttonPagarTodo = findViewById(R.id.buttonPagarTodo);
        ListElementCustomer element = (ListElementCustomer) getIntent().getSerializableExtra("ListElementCustomer");
        this.setTitle(element.getFirst_name()+" "+element.getLast_name());
        elements = new ArrayList<>();
        getProducts(element.getCustomer_id());
        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> messageOptionDialog(item));
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);

        buttonAgregar.setOnClickListener(view -> openDialog(element));
        buttonPagarTodo.setOnClickListener(view -> pagarTodo(element.getCustomer_id()));
    }
    public void openDialog(ListElementCustomer element){
        ExampleDialog exampleDialog = new ExampleDialog(element);
        exampleDialog.show(getSupportFragmentManager(),"Agregar cliente");
    }
    public void messageOptionDialog(ListElementDebt item){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacion_hacer)
                .setTitle(R.string.informacion);
        builder.setPositiveButton(R.string.pagar_producto, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setNegativeButton(R.string.editar_producto, (dialogInterface, i) -> dialogInterface.dismiss());
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
        System.out.println("Producto registrado exitosamente con el id " + result);
        db.close();
    }

    public void getProducts(long customer_id){
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this,"late_pay_bd",null,1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_DEBT+" WHERE customer_id="+customer_id+" AND paid='false'", null);
        elements = new ArrayList<>();
        while(cursor.moveToNext()){
                elements.add(
                        new ListElementDebt(
                                cursor.getLong(0),
                                cursor.getString(1),
                                "MXN $"+ cursor.getDouble(2),
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
    public void pagarProducto(long debt_id){

    }
    public void pagarTodo(long customer_id){
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ContentValues cv = new ContentValues();
        cv.put(FIELD_PAID, "true");
        cv.put(FIELD_PAY_DATE, formatter.format(LocalDateTime.now()));
        db.update(TABLE_DEBT,cv,"customer_id = "+customer_id,null);
        db.close();
        elements = new ArrayList<>();
        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> messageOptionDialog(item));
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);
    }
}