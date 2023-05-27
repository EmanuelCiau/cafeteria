package com.example.cafeteria;

import static android.content.ContentValues.TAG;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodFragment extends Fragment implements ListPedidoAdapter.OnItemClickListener{

    int hora;
    int minutos;
    Calendar c;
    private RecyclerView pedidosLista;
    private List<Pedido> compra;
    private ListPedidoAdapter listAdapter;
    String UrlPedidos = "https://afflated-sentries.000webhostapp.com/recuperarTodasCompras.php";
    String UrlModificarPedido = "https://afflated-sentries.000webhostapp.com/modificarCompra.php";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @Override
    public void onItemClick(int id) {

    }

    @Override
    public void guardar(int id) {
        c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String fecha = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+" "+hourOfDay+":"+minute;
                modificarProducto(UrlModificarPedido, id,fecha);
            }
        },hora,minutos,false);
        timePickerDialog.show();

    }

    public FoodFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        compra = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        recuperarVentas(UrlPedidos);

        //-------------Configuraciones del RecyclerView de los productos---------------------------------
        listAdapter = new ListPedidoAdapter(compra, getContext());
        //Hay que instanciar este metodo para que los botones funcionen.
        listAdapter.setOnItemClickListener(this);
        pedidosLista = view.findViewById(R.id.ListaPedidos);
        pedidosLista.setHasFixedSize(true);
        pedidosLista.setLayoutManager(new LinearLayoutManager(getContext()));
        pedidosLista.setAdapter(listAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void recuperarVentas(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    leerJSON(response);
                    listAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "No hay productos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void leerJSON(String response){
        try {
            JSONArray jsonArray = new JSONArray(response);
            compra.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int id_cliente = jsonObject.getInt("id_cliente");
                String nombre = jsonObject.getString("nombre_cliente");
                String telefono = jsonObject.getString("telefono_cliente");
                String tipo_pago = jsonObject.getString("tipo_pago");
                Double total = jsonObject.getDouble("total");
                String hora_compra = jsonObject.getString("hora_compra");
                String hora_recogida = jsonObject.getString("hora_recogida");
                String estatus = jsonObject.getString("estatus");
                String elemnto = jsonObject.getString("pedidos");

                Pedido pedidos = new Pedido(id, id_cliente, nombre, telefono, tipo_pago, total, hora_compra, hora_recogida, estatus);
                pedidos.setCompra(leerJSONPedidos(elemnto));

                compra.add(pedidos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<element_producto> leerJSONPedidos(String pedidosString){
        List<element_producto> pedidos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(pedidosString);
            pedidos.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id_pedido");
                int id_producto = jsonObject.getInt("id_producto");
                String nombre_producto = jsonObject.getString("nombre_producto");
                int cantidad_pedido = jsonObject.getInt("cantidad_pedido");
                Double precio_producto = jsonObject.getDouble("precio_producto");

                element_producto pedido = new element_producto(id, id_producto, nombre_producto, cantidad_pedido, precio_producto);
                pedidos.add(pedido);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    private  void  modificarProducto(String url, int id, String hora){
        StringRequest requerido = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);
                if(response.isEmpty()){
                    recuperarVentas(UrlPedidos);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"si se modifico",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_compra",String.valueOf(id));
                parametros.put("hora_recogida", hora );
                return parametros;
            }
        };
        RequestQueue respuesta = Volley.newRequestQueue(getContext());
        respuesta.add(requerido);
    }

}