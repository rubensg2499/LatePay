package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.FIELD_CUSTOMER_ID;
import static com.example.latepay.utilidades.utilidades.FIELD_PAID;
import static com.example.latepay.utilidades.utilidades.FIELD_PRICE;
import static com.example.latepay.utilidades.utilidades.TABLE_CUSTOMER;
import static com.example.latepay.utilidades.utilidades.TABLE_DEBT;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latepay.databinding.ActivityMainBinding;
import com.example.latepay.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class clientes extends Fragment {
    ArrayList<Cliente> listClientes;
    List<ListElementCustomer> elements;
    private ActivityMainBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    public clientes() {
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
        return inflater.inflate(R.layout.fragment_clientes, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        elements = new ArrayList<>();
        getCustomers();
        ListAdapterCustomer listAdapterCustomer = new ListAdapterCustomer(elements, getActivity(), item -> messageDeleteDialog(view, item));
        if (view != null) {
            RecyclerView recyclerView = view.findViewById(R.id.list_clientes);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(listAdapterCustomer);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        elements = new ArrayList<>();
        getCustomers();
        ListAdapterCustomer listAdapterCustomer = new ListAdapterCustomer(elements, getActivity(), item -> messageDeleteDialog(view, item));
        RecyclerView recyclerView = view.findViewById(R.id.list_clientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapterCustomer);
    }

    public void messageDeleteDialog(View view, ListElementCustomer item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirmacion_hacer)
                .setTitle(R.string.informacion)
                .setIcon(R.drawable.ic_paid_purple_24dp);
        builder.setPositiveButton(R.string.editar_cliente, (dialogInterface, i) -> moveToActivity(item, actualizar_cliente.class));
        builder.setNegativeButton(R.string.ver_registros, (dialogInterface, i) -> moveToActivity(item, deudas.class));
        builder.setNeutralButton(R.string.cancelar, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void moveToActivity(ListElementCustomer item, Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("ListElementCustomer", item);
        startActivity(intent);
    }

    public void getCustomers() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getActivity(), "late_pay_bd", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER, null);
        listClientes = new ArrayList<>();
        while (cursor.moveToNext()) {
            double deuda = 0.0;
            Cursor cursor2 = db.rawQuery("SELECT " + FIELD_PRICE + " FROM " + TABLE_DEBT + " WHERE " + FIELD_CUSTOMER_ID + " = " + cursor.getInt(0) + " AND " + FIELD_PAID + "='false'", null);
            while (cursor2.moveToNext()) {
                deuda += cursor2.getDouble(0);
            }
            listClientes.add(
                    new Cliente(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            deuda
                    )
            );
            cursor2.close();
        }
        cursor.close();

        setListView(listClientes);
    }

    public void setListView(ArrayList<Cliente> listClientes) {
        elements.clear();
        for (Cliente c : listClientes) {
            elements.add(new ListElementCustomer(
                    c.getCustomer_id(),
                    c.getFirst_name(),
                    c.getLast_name(),
                    (c.getPhone().equalsIgnoreCase("")) ? "Sin tel??fono" : c.getPhone(),
                    c.getEmail(),
                    c.getAddress(),
                    c.getCreated_date(),
                    "MXN $" + c.getTotal_debt(),
                    "#674FA3"
            ));
        }

    }
}