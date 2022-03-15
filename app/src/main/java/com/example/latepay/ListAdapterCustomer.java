package com.example.latepay;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapterCustomer extends RecyclerView.Adapter<ListAdapterCustomer.ViewHolder> {
    private List<ListElementCustomer> mdata;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterCustomer.onItemClickListener listener;

    public interface onItemClickListener{
        void onItemClick(ListElementCustomer item);
    }
    public ListAdapterCustomer(List<ListElementCustomer> itemList, Context context, ListAdapterCustomer.onItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mdata = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount(){ return mdata.size(); }

    @Override
    public ListAdapterCustomer.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapterCustomer.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapterCustomer.ViewHolder holder, final int position){
        holder.bindData(mdata.get(position));
    }
    public void setItems(List<ListElementCustomer> items){ mdata=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nombre, telefono, deuda;
        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nombre_cliente);
            telefono = itemView.findViewById(R.id.telefono_cliente);
            deuda = itemView.findViewById(R.id.deuda_cliente);
        }

        void bindData(final ListElementCustomer item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getFirst_name() + " " + item.getLast_name());
            telefono.setText(item.getPhone());
            deuda.setText(item.getTotal_debt());
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }
    }
}
