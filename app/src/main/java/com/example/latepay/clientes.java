package com.example.latepay;

import static com.example.latepay.utilidades.utilidades.FIELD_CUSTOMER_ID;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clientes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clientes extends Fragment {
    ArrayList<Cliente> listClientes;
    List<ListElement> elements;
    private ActivityMainBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public clientes() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static clientes newInstance(String param1, String param2) {
        clientes fragment = new clientes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        ListAdapter listAdapter = new ListAdapter(elements, getActivity(), item -> {
            messageDeleteDialog(view,item);
        });
        RecyclerView recyclerView = view.findViewById(R.id.list_clientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        elements = new ArrayList<>();
        getCustomers();
        ListAdapter listAdapter = new ListAdapter(elements, getActivity(), item -> {
            messageDeleteDialog(view,item);
        });
        RecyclerView recyclerView = view.findViewById(R.id.list_clientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);
    }
    public void messageDeleteDialog(View view, ListElement item){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirmacion_hacer)
                .setTitle(R.string.informacion);
        builder.setPositiveButton(R.string.editar_cliente, (dialogInterface, i) -> {
            moveToActivity(item);
        });
        builder.setNegativeButton(R.string.ver_registros, (dialogInterface, i) -> {
            Snackbar.make(view, "Se muestra la lista de productos", Snackbar.LENGTH_LONG).setAction(R.string.action, null).show();
        });
        builder.setNeutralButton(R.string.cancelar, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void moveToActivity(ListElement item){
        Intent intent = new Intent(getActivity(), actualizar_cliente.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }

    public void getCustomers(){
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getActivity(),"late_pay_bd",null,1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CUSTOMER, null);
        listClientes = new ArrayList<>();
        while(cursor.moveToNext()){
            double deuda = 0.0;
            Cursor cursor2 = db.rawQuery("SELECT " + FIELD_PRICE + " FROM " + TABLE_DEBT + " WHERE "+FIELD_CUSTOMER_ID+" = "+cursor.getInt(0),null);
            while (cursor2.moveToNext()){
                deuda+=cursor2.getDouble(0);
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


    public void setListView(ArrayList<Cliente> listClientes){
        elements.clear();
        for (Cliente c: listClientes) {
            elements.add(new ListElement(
                    c.getCustomer_id(),
                    c.getFirst_name(),
                    c.getLast_name(),
                    (c.getPhone().equalsIgnoreCase(""))?"Sin teléfono":c.getPhone(),
                    c.getEmail(),
                    c.getAddress(),
                    c.getCreated_date(),
                    "MXN $"+c.getTotal_debt(),
                    "#000000"
            ));
        }

    }
}