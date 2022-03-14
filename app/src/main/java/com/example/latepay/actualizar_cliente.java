package com.example.latepay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class actualizar_cliente extends AppCompatActivity {
    TextView textViewID;
    Button buttonActualizar;
    Button buttonEliminar;
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
        textViewID = findViewById(R.id.textViewID);
        textViewCreacion = findViewById(R.id.textViewCreacion);
        editNombre = findViewById(R.id.editTextNombreUpdate);
        editApellidos = findViewById(R.id.editTextApellidosUpdate);
        editTelefono = findViewById(R.id.editTextTelefonoUpdate);
        editCorreo = findViewById(R.id.editTextCorreoUpdate);
        editDireccion = findViewById(R.id.editTextDireccionUpdate);

        buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        
    }
}