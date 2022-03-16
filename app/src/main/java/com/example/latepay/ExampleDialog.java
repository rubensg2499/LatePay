package com.example.latepay;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.snackbar.Snackbar;


public class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextProducto;
    private EditText editTextPrecio;
    private long customer_id;
    private ExampleDialogListener listener;
    public ExampleDialog(ListElementCustomer element){
        customer_id = element.getCustomer_id();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_producto, null);
        builder.setView(view)
                .setTitle("Agregar producto");

        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Agregar", (dialogInterface, i) -> {
            if(editTextProducto.getText().toString().trim().equalsIgnoreCase("") ||
                editTextPrecio.getText().toString().trim().equalsIgnoreCase("")){
                Snackbar.make(view, "Error, no puede dejar campos vacíos.", Snackbar.LENGTH_LONG).setAction(R.string.action, null).show();
            }else {
                String textProduct = editTextProducto.getText().toString();
                double textPrice = Double.parseDouble(editTextPrecio.getText().toString());
                listener.applyParams(customer_id, textProduct, textPrice, view);
            }
        });
        editTextProducto = view.findViewById(R.id.txt_producto);
        editTextPrecio = view.findViewById(R.id.txt_precio);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +" Debe implementar ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyParams(long customer_id,String producto, double precio, View view);
    }
}