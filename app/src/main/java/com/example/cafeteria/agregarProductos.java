package com.example.cafeteria;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link agregarProductos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class agregarProductos extends Fragment {

    MaterialButton agregarProducto;
    EditText nombre, descripcion, precio_unidad, cantidad;
    String categoria;
    Producto producto;
    String URLagregarProdcudto = "https://afflated-sentries.000webhostapp.com/registrarProducto.php";
    String URLRecuperarProducto = "https://afflated-sentries.000webhostapp.com/recuperarProducto.php";
    String URLModificarProduct = "https://afflated-sentries.000webhostapp.com/modificarProducto.php";

    AutoCompleteTextView autoCompleteTextView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public agregarProductos() {
        // Required empty public constructor
    }

    public static agregarProductos newInstance(String param1, String param2) {
        agregarProductos fragment = new agregarProductos();
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

        //----------Declaracion de las variables del sistema_________________________________________
        View view = inflater.inflate(R.layout.fragment_agregar_productos,container,false);

        List<String> listaOpciones = new ArrayList<>();
        listaOpciones.add("Dia");
        listaOpciones.add("Empaquetado");
        listaOpciones.add("Embotellado");
        listaOpciones.add("Dulce");
        listaOpciones.add("Postre");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.stile_text, listaOpciones);

        nombre = view.findViewById(R.id.txtnombre);
        descripcion = view.findViewById(R.id.txtDescripcion);
        precio_unidad = (EditText) view.findViewById(R.id.txtPrecio);
        cantidad = (EditText) view.findViewById(R.id.txtCantidad);
        int id_producto = getArguments().getInt("id_producto");

        if(id_producto!=-1){
            recuperarProducto(URLRecuperarProducto,String.valueOf(id_producto));
        }

        //-------------------------selecion de caregoria-----------------------------------------
        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoria = parent.getItemAtPosition(position).toString();
            }

        });

        //---------------------------boton para agregar productos------------------
        agregarProducto = (MaterialButton) view.findViewById(R.id.guardar);

        agregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nombre.getText().toString().isEmpty() && !descripcion.getText().toString().isEmpty() &&
                        !precio_unidad.getText().toString().isEmpty() && !cantidad.getText().toString().isEmpty() && !categoria.isEmpty()){

                    if(id_producto!=-1){
                        modificarProducto(URLModificarProduct);
                    }else {
                        nuevoProducto(URLagregarProdcudto);
                    }

                }else {
                    Toast.makeText(getContext(), "no agregado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    //-------------------Metodod que se encarga de subir los productos a la db------------------------------
    private void nuevoProducto(String url){
        StringRequest requerido = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);
                if(!response.isEmpty()){
                    Toast.makeText(getContext(), "agregado", Toast.LENGTH_SHORT).show();
                    homeFragment home = new homeFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, home);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    Toast.makeText(getContext(),"Error en la coneccion",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("descripcion",descripcion.getText().toString());
                parametros.put("precio_unidad",precio_unidad.getText().toString());
                parametros.put("cantidad",cantidad.getText().toString());
                parametros.put("categoria",categoria);
                parametros.put("imagen", "null");
                return parametros;
            }
        };
        RequestQueue respuesta = Volley.newRequestQueue(getContext());
        respuesta.add(requerido);
    }

    private void recuperarProducto(String URL, String id_producto){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    producto = Producto.fromJson(response);
                    nombre.setText(producto.getNombre());
                    descripcion.setText(producto.descripcion);
                    cantidad.setText(String.valueOf(producto.getCantidad()));
                    categoria = producto.getCategoria();


                    DecimalFormat formato = new DecimalFormat("0.00");
                    String precio = formato.format(producto.getPrecio_unidad());
                    precio_unidad.setText(precio);

                }else{
                    Toast.makeText(getContext(),"Producto no encontrado", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_producto", id_producto);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private  void  modificarProducto(String url){
        StringRequest requerido = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);
                if(response.isEmpty()){
                    //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "Modificado", Toast.LENGTH_SHORT).show();
                    homeFragment home = new homeFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, home);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    Toast.makeText(getContext(),"Error en la modificación",Toast.LENGTH_SHORT).show();
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
                parametros.put("id_producto",String.valueOf(producto.getId()));
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("descripcion",descripcion.getText().toString());
                parametros.put("precio_unidad",precio_unidad.getText().toString());
                parametros.put("cantidad",cantidad.getText().toString());
                parametros.put("categoria",categoria);
                parametros.put("imagen", null);
                return parametros;
            }
        };
        RequestQueue respuesta = Volley.newRequestQueue(getContext());
        respuesta.add(requerido);
    }
}