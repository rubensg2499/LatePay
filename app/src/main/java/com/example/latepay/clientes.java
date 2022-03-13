package com.example.latepay;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.latepay.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clientes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clientes extends Fragment {
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
    public void onViewCreated(View view, Bundle savedInstanceState){
        elements = new ArrayList<>();
        elements.add(
                new ListElement(
                        1,
                        "Pedro",
                        "Pérez",
                        "9712222469",
                        "pedro.perez@gmail.com",
                        "Segunda norte s/n col, linda vista",
                        "01/01/2021",
                        "MXN $23.00",
                        "#000000"
                )
        );
        ListAdapter listAdapter = new ListAdapter(elements, getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.list_clientes);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);
    }
}