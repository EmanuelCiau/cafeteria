package com.example.cafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private OnItemClickListener listener; // Sirve para ir a la pantalla de información de un producto
    private List<Producto> producto;
    private LayoutInflater inflater;
    private Context context;

    public ListAdapter(List<Producto> producto, Context context) {
        this.producto = producto;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.element_producto, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {

        holder.binData(producto.get(position));

        holder.eliminiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                if (listener != null) {
                    listener.onItemClick(id);
                }
            }
        });

        holder.modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                if (listener != null) {
                    listener.modificar(id);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return producto.size();
    }

    public void setItems(List<Producto> items){producto= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        Context context;
        TextView prod, cant, prec, des;
        MaterialButton eliminiar, modificar;

        ViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            prod = itemView.findViewById(R.id.texProducto);
            cant = itemView.findViewById(R.id.txtCantidad);
            prec = itemView.findViewById(R.id.txtPrecio);
            des = itemView.findViewById(R.id.txtDescripcion);
            modificar = itemView.findViewById(R.id.btnCambiar);
            eliminiar = itemView.findViewById(R.id.btnEliminar);

        }

        void binData(final Producto item){
            prod.setText(item.getNombre());
            cant.setText("Cantidad: "+item.getCantidad());
            prec.setText("Precio: $"+item.getPrecio_unidad());
            des.setText("Descripcion: "+item.getDescripcion());
            modificar.setTag(item.id);
            eliminiar.setTag(item.id);
        }

    }

    // Sirve para ir a la pantalla de información de un producto
    public interface OnItemClickListener {
        void onItemClick(int id);
        void modificar(int id);
    }

    // Sirve para ir a la pantalla de información de un producto
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
