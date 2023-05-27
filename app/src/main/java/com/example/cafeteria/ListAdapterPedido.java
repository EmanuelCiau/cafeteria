package com.example.cafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteria.R;
import com.example.cafeteria.element_producto;

import java.text.DecimalFormat;
import java.util.List;

public class ListAdapterPedido extends RecyclerView.Adapter<ListAdapterPedido.ViewHolder> {
    private List<element_producto> datos;
    private LayoutInflater mInflater;
    private Context context;


    public ListAdapterPedido(List<element_producto> datos, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.datos = datos;
    }

    @NonNull
    @Override
    public ListAdapterPedido.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_pedido, null);
        return new ListAdapterPedido.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterPedido.ViewHolder holder, int posicion) {
        holder.bindData(datos.get(posicion));

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setItems(List<element_producto> items) {
        //datos.clear();
        datos.addAll(items);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtProducto, txtCantidad, txtPrecio;

        ViewHolder(View itemView) {
            super(itemView);
            txtProducto = itemView.findViewById(R.id.txtProducto);
            txtCantidad = itemView.findViewById(R.id.labelCantidad);
            txtPrecio = itemView.findViewById(R.id.labelPrecio);
        }

        //pone el contenido
        void bindData(final element_producto pedido) {
            txtProducto.setText(pedido.getNombre_producto());
            txtCantidad.setText("Cantidad: "+String.valueOf(pedido.getCantidad_pedido()));

            DecimalFormat formato = new DecimalFormat("0.00");
            String precio = "$" + formato.format(pedido.getPrecio_producto());
            txtPrecio.setText("Precio: "+precio);
        }

    }
}
