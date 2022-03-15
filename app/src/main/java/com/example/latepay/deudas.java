package com.example.latepay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class deudas extends AppCompatActivity {
    List<ListElementDebt> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deudas);
        ListElementCustomer element = (ListElementCustomer) getIntent().getSerializableExtra("ListElementCustomer");
        this.setTitle(element.getFirst_name()+" "+element.getLast_name());
        elements = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            elements.add(new ListElementDebt(
                    1,"string","MXN $4.5","false","buy","pay",1,"#674FA3"
            ));
        }

        ListAdapterDebt listAdapterDebt = new ListAdapterDebt(elements, this, item -> {
            messageOptionDialog(item);
        });
        RecyclerView recyclerView = findViewById(R.id.list_deudas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterDebt);
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
}