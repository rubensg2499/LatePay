package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.ER_EMAIL;
import static com.example.latepay.utilidades.utilidades.ER_FULL_NAME;
import static com.example.latepay.utilidades.utilidades.ER_PHONE;
import static com.example.latepay.utilidades.utilidades.FIELD_ADDRESS;
import static com.example.latepay.utilidades.utilidades.FIELD_EMAIL;
import static com.example.latepay.utilidades.utilidades.FIELD_FIRST_NAME;
import static com.example.latepay.utilidades.utilidades.FIELD_LAST_NAME;
import static com.example.latepay.utilidades.utilidades.FIELD_PHONE;
import static com.example.latepay.utilidades.utilidades.TABLE_CUSTOMER;
import static com.example.latepay.utilidades.utilidades.TABLE_DEBT;
import static com.example.latepay.utilidades.utilidades.isCorrectPattern;
import static com.example.latepay.utilidades.utilidades.isEmpty;
import static com.example.latepay.utilidades.utilidades.showSnack;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class actualizar_cliente extends AppCompatActivity {
    TextView textViewID;
    Button buttonActualizar;
    Button buttonEliminar;
    Button buttonCancelar;
    EditText editNombre;
    EditText editApellidos;
    EditText editTelefono;
    EditText editCorreo;
    EditText editDireccion;
    TextView textViewCreacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_actualizar_cliente);

        this.setTitle("Editar cliente");
        textViewID = findViewById(R.id.textViewID);
        textViewCreacion = findViewById(R.id.textViewCreacion);
        editNombre = findViewById(R.id.editTextNombreUpdate);
        editApellidos = findViewById(R.id.editTextApellidosUpdate);
        editTelefono = findViewById(R.id.editTextTelefonoUpdate);
        editCorreo = findViewById(R.id.editTextCorreoUpdate);
        editDireccion = findViewById(R.id.editTextDireccionUpdate);

        buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        ListElementCustomer element = (ListElementCustomer) getIntent().getSerializableExtra("ListElementCustomer");
        textViewID.setText("ID: " + element.getCustomer_id());
        textViewCreacion.setText("Fecha de creaci??n: " + element.getCreated_date());
        editNombre.setText(element.getFirst_name());
        editApellidos.setText(element.getLast_name());
        editTelefono.setText(element.getPhone().equalsIgnoreCase("Sin tel??fono") ? "" : element.getPhone());
        editCorreo.setText(element.getEmail());
        editDireccion.setText(element.getAddress());

        buttonActualizar.setOnClickListener(view -> {
            if (!checkFields()) updateCustomer(view, element);
        });
        buttonEliminar.setOnClickListener(view -> messageDeleteDialog(view, element));
        buttonCancelar.setOnClickListener(view -> this.finish());
    }

    private void updateCustomer(View view, ListElementCustomer element) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_FIRST_NAME, editNombre.getText().toString());
        cv.put(FIELD_LAST_NAME, editApellidos.getText().toString());
        cv.put(FIELD_PHONE, editTelefono.getText().toString());
        cv.put(FIELD_EMAIL, editCorreo.getText().toString());
        cv.put(FIELD_ADDRESS, editDireccion.getText().toString());
        long result = db.update(TABLE_CUSTOMER, cv, "customer_id=" + element.getCustomer_id(), null);
        Snackbar.make(view, R.string.registro_actualizado + "" + result, Snackbar.LENGTH_LONG).setAction(R.string.action, null).show();
        db.close();
        this.finish();
    }

    private void deleteCustomer(ListElementCustomer element) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        db.delete(TABLE_DEBT, "customer_id = " + element.getCustomer_id(), null);
        db.delete(TABLE_CUSTOMER, "customer_id = " + element.getCustomer_id(), null);
        db.close();
        this.finish();
    }

    private boolean checkFields() {
        boolean res = false;
        //Nombre
        if (isEmpty(editNombre)) {
            res = true;
            editNombre.setError("Debe ingresar un nombre.");
        } else {
            if (!isCorrectPattern(editNombre.getText().toString().trim(), ER_FULL_NAME)) {
                res = true;
                editNombre.setError("Debe ingresar un nombre v??lido.");
            }
        }
        //Apellidos
        if (isEmpty(editApellidos)) {
            res = true;
            editApellidos.setError("Debe ingresar un apellido.");
        } else {
            if (!isCorrectPattern(editApellidos.getText().toString().trim(), ER_FULL_NAME)) {
                res = true;
                editApellidos.setError("Debe ingresar un apellido v??lido.");
            }
        }
        //Tel??fono
        if (!isEmpty(editTelefono)) {
            if (!isCorrectPattern(editTelefono.getText().toString().trim(), ER_PHONE)) {
                res = true;
                editTelefono.setError("Ingrese un tel??fono v??lido.");
            }
        }
        //Correo electr??nico
        if (!isEmpty(editCorreo)) {
            if (!isCorrectPattern(editCorreo.getText().toString().trim(), ER_EMAIL)) {
                res = true;
                editCorreo.setError("Ingrese un correo v??lido.");
            }
        }
        return res;
    }

    public void messageDeleteDialog(View view, ListElementCustomer element) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacion_eliminar)
                .setTitle(R.string.informacion);
        builder.setPositiveButton(R.string.eliminar, (dialogInterface, i) -> {
            deleteCustomer(element);
            showSnack(view, "" + R.string.registro_eliminado);

        });
        builder.setNeutralButton(R.string.cancelar, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}