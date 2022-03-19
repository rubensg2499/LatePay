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

public class ListAdapterDebt extends RecyclerView.Adapter<ListAdapterDebt.ViewHolder> {
    final ListAdapterDebt.onItemClickListener listener;
    private List<ListElementDebt> mdata;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapterDebt(List<ListElementDebt> itemList, Context context, ListAdapterDebt.onItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mdata = itemList;
        this.listener = listener;
    }

    @Override
    public ListAdapterDebt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element_debt, null);
        return new ListAdapterDebt.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapterDebt.ViewHolder holder, int position) {
        holder.bindData(mdata.get(position));
    }

    public void setItems(List<ListElementDebt> items) {
        mdata = items;
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public interface onItemClickListener {
        void onItemClick(ListElementDebt item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView producto, precio;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewDebt);
            producto = itemView.findViewById(R.id.nombre_producto);
            precio = itemView.findViewById(R.id.importe_producto);
        }

        void bindData(final ListElementDebt item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            producto.setText(item.getProduct());
            precio.setText(String.valueOf(item.getPrice()));
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }
    }
}
