package com.example.latepay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        textViewID.setText("ID: "+element.getCustomer_id());
        textViewCreacion.setText("Fecha de creación: "+element.getCreated_date());
        editNombre.setText(element.getFirst_name());
        editApellidos.setText(element.getLast_name());
        editTelefono.setText(element.getPhone());
        editCorreo.setText(element.getEmail());
        editDireccion.setText(element.getAddress());

        buttonActualizar.setOnClickListener(view -> this.finish());
        buttonEliminar.setOnClickListener(view -> messageDeleteDialog(view));
        buttonCancelar.setOnClickListener(view -> this.finish());
    }
    public void messageDeleteDialog(View view){
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