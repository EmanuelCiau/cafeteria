package com.example.cafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.zip.Inflater;

public class ListPedidoAdapter extends RecyclerView.Adapter<ListPedidoAdapter.ViewHolder>{

    private OnItemClickListener cambio; // Sirve para ir a la pantalla de información de un producto
    private List<Pedido> pedidos;
    private LayoutInflater inflater;
    private Context context;

    public ListPedidoAdapter(List <Pedido> pedidoList, Context cont){

        this.pedidos = pedidoList;
        this.inflater = LayoutInflater.from(cont);
        this.context = cont;

    }


    @NonNull
    @Override
    public ListPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.targetas_pedidos, null);
        return new ListPedidoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPedidoAdapter.ViewHolder holder, int position) {

        holder.binData(pedidos.get(position));

        holder.eliminiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                if (cambio != null) {
                    cambio.onItemClick(id);
                }
            }
        });

        holder.guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                if (cambio != null) {
                    cambio.guardar(id);
                }
            }
        });

    }

    //problema
    @Override
    public int getItemCount() { return pedidos.size();}

    public void setItems(List<Pedido> items){pedidos= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Context context;
        TextView ped, hora, total, telefono, recoger;
        MaterialButton eliminiar, guardar;
        private RecyclerView listaPedidos;
        ListAdapterPedido listAdapter;

        ViewHolder(View itemView){
            super(itemView);
            //context = itemView.getContext();
            ped= itemView.findViewById(R.id.pedidoDe);
            hora = itemView.findViewById(R.id.Hora);
            total = itemView.findViewById(R.id.txtTotalCompra);
            telefono = itemView.findViewById(R.id.txtTelefonoCliente);
            recoger = itemView.findViewById(R.id.txtHoraRecogida);
            guardar = itemView.findViewById(R.id.btnHoraGuardar);
            eliminiar = itemView.findViewById(R.id.btnPedidoEliminar);
            listaPedidos = itemView.findViewById(R.id.recyclerView);
        }

        void binData(final Pedido item){
            String[] partes = item.hora_compra.split(" ");
            String[] tiemp_creado = partes[1].split(":");
            if(item.hora_recogida.equals("null")){
                recoger.setText("Sin especificar");
            }else {
                String[] obtener = item.hora_recogida.split(" ");
                String[] tiemp_recogida = obtener[1].split(":");
                recoger.setText("Recoger a: "+tiemp_recogida[0]+":"+tiemp_recogida[1]+" hrs");
            }
            ped.setText("De: "+item.nombre_cliente);
            hora.setText(tiemp_creado[0]+":"+tiemp_creado[1]+" hrs");
            total.setText("Total: $"+item.total);
            telefono.setText("Telefono: "+item.getTelefono_cliente());

            guardar.setTag(item.id);
            eliminiar.setTag(item.id);
            listAdapter = new ListAdapterPedido(item.getCompra(), itemView.getContext());
            listaPedidos.setAdapter(listAdapter);
            listaPedidos.setHasFixedSize(true);
            listaPedidos.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }

    }

    // Sirve para ir a la pantalla de información de un producto
    public interface OnItemClickListener {
        void onItemClick(int id);
        void guardar(int id);
    }

    // Sirve para ir a la pantalla de información de un producto
    public void setOnItemClickListener(ListPedidoAdapter.OnItemClickListener listener) {
        this.cambio = listener;
    }
}
