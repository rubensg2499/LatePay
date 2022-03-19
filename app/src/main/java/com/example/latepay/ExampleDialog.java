package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.ER_PRICE;
import static com.example.latepay.utilidades.utilidades.isCorrectPattern;
import static com.example.latepay.utilidades.utilidades.isEmpty;

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

import java.util.ArrayList;


public class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextProducto;
    private EditText editTextPrecio;
    private final long customer_id;
    private ArrayList<String> errores;
    private ExampleDialogListener listener;

    public ExampleDialog(ListElementCustomer element) {
        customer_id = element.getCustomer_id();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        errores = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_producto, null);
        builder.setView(view)
                .setTitle("Agregar producto")
                .setIcon(R.drawable.ic_paid_purple_24dp);

        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Agregar", (dialogInterface, i) -> {
            if (isEmpty(editTextProducto) || isEmpty(editTextPrecio)) {
                errores.add("Error, no puede dejar campos vacíos.");
                listener.applyParams(errores);
            } else if (!isCorrectPattern(editTextPrecio.getText().toString().trim(), ER_PRICE)) {
                errores.add("Error, el precio ingresado es incorrecto.");
                listener.applyParams(errores);
            } else {
                //Aplicar mejor un patron
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
            throw new ClassCastException(context + " Debe implementar ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyParams(long customer_id, String producto, double precio, View view);

        void applyParams(ArrayList<String> errores);
    }
}