package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.*;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class agregar_cliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    Button buttonGuardar;
    EditText editNombre;
    EditText editApellidos;
    EditText editTelefono;
    EditText editCorreo;
    EditText editDireccion;

    public agregar_cliente() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_cliente, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        buttonGuardar = view.findViewById(R.id.buttonGuardar);
        editNombre = view.findViewById(R.id.editTextNombre);
        editApellidos = view.findViewById(R.id.editTextApellidos);
        editTelefono = view.findViewById(R.id.editTextTelefono);
        editCorreo = view.findViewById(R.id.editTextCorreo);
        editDireccion = view.findViewById(R.id.editTextDireccion);

        buttonGuardar.setOnClickListener(view1 -> {
            if (!checkFields()){
                registerCustomer(view1);
            }
        });

    }
    private boolean checkFields(){
        boolean res = false;
        if(editNombre.getText().toString().trim().equals("")){
            res = true;
            editNombre.setError("Debe ingresar un nombre.");
        }
        if(editApellidos.getText().toString().trim().equals("")){
            res = true;
            editApellidos.setError("Debe ingresar un apellido.");
        }
        return res;
    }
    private void registerCustomer(View view) {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getActivity(),"late_pay_bd",null,1);
        SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ContentValues cv = new ContentValues();
        cv.put(FIELD_FIRST_NAME, editNombre.getText().toString());
        cv.put(FIELD_LAST_NAME, editApellidos.getText().toString());
        cv.put(FIELD_PHONE, editTelefono.getText().toString());
        cv.put(FIELD_EMAIL, editCorreo.getText().toString());
        cv.put(FIELD_ADDRESS, editDireccion.getText().toString());
        cv.put(FIELD_CREATED_DATE, formatter.format(LocalDateTime.now()));
        long result = db.insert(TABLE_CUSTOMER,FIELD_CUSTOMER_ID, cv);
        Snackbar.make(view, "Cliente registrado exitosamente con el id " + result, Snackbar.LENGTH_LONG).setAction("Action",null).show();
        cleanFields();
    }

    private void cleanFields() {
        editNombre.setText("");
        editApellidos.setText("");
        editTelefono.setText("");
        editCorreo.setText("");
        editDireccion.setText("");
        editNombre.requestFocus();
    }
}