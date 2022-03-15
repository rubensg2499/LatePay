package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.FIELD_ADDRESS;
import static com.example.latepay.utilidades.utilidades.FIELD_CREATED_DATE;
import static com.example.latepay.utilidades.utilidades.FIELD_CUSTOMER_ID;
import static com.example.latepay.utilidades.utilidades.FIELD_EMAIL;
import static com.example.latepay.utilidades.utilidades.FIELD_FIRST_NAME;
import static com.example.latepay.utilidades.utilidades.FIELD_LAST_NAME;
import static com.example.latepay.utilidades.utilidades.FIELD_PHONE;
import static com.example.latepay.utilidades.utilidades.TABLE_CUSTOMER;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        textViewID.setText("ID: " + element.getCustomer_id());
        textViewCreacion.setText("Fecha de creación: " + element.getCreated_date());
        editNombre.setText(element.getFirst_name());
        editApellidos.setText(element.getLast_name());
        editTelefono.setText(element.getPhone());
        editCorreo.setText(element.getEmail());
        editDireccion.setText(element.getAddress());

        buttonActualizar.setOnClickListener(view -> updateCustomer(view,element.getCustomer_id()));
        buttonEliminar.setOnClickListener(view -> messageDeleteDialog(view));
        buttonCancelar.setOnClickListener(view -> this.finish());
    }

    private void updateCustomer(View view, long id) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_FIRST_NAME, editNombre.getText().toString());
        cv.put(FIELD_LAST_NAME, editApellidos.getText().toString());
        cv.put(FIELD_PHONE, editTelefono.getText().toString());
        cv.put(FIELD_EMAIL, editCorreo.getText().toString());
        cv.put(FIELD_ADDRESS, editDireccion.getText().toString());
        long result = db.update(TABLE_CUSTOMER, cv, "customer_id="+id,null);
        Snackbar.make(view, R.string.registro_actualizado+""+result, Snackbar.LENGTH_LONG).setAction(R.string.action, null).show();
        this.finish();
    }

    public void messageDeleteDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacion_elimnar)
                .setTitle(R.string.informacion);
        builder.setPositiveButton(R.string.si, (dialogInterface, i) -> {
            Snackbar.make(view, R.string.registro_eliminado, Snackbar.LENGTH_LONG).setAction(R.string.action, null).show();
        });
        builder.setNeutralButton(R.string.cancelar, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}