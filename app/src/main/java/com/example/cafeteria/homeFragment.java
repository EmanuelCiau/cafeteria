package com.example.cafeteria;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment implements ListAdapter.OnItemClickListener{
    private RecyclerView vistaProductos;
    private List<Producto> productos;
    private ListAdapter listAdapter;
    private String URL_ProductosDia = "https://afflated-sentries.000webhostapp.com/recuperarProductosDia.php";
    private String URL_ProductosEmpaquetado = "https://afflated-sentries.000webhostapp.com/recuperarProductosEmpaquetado.php";
    private String URL_ProductosEmbotellado = "https://afflated-sentries.000webhostapp.com/recuperarProductosEmbotellado.php";
    private String URL_ProductosDulce = "https://afflated-sentries.000webhostapp.com/recuperarProductosDulce.php";
    private String URL_ProductosPostre = "https://afflated-sentries.000webhostapp.com/recuperarProductosPostre.php";
    private String URL_TodosProductos = "https://afflated-sentries.000webhostapp.com/recuperarTodosProductos.php";

    MaterialButton botonAgregar;
    AutoCompleteTextView autoCompleteTextView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @Override
    public void modificar(int id){
        agregarProductos agregar = new agregarProductos();

        Bundle bundle = new Bundle();
        bundle.putInt("id_producto", id);
        agregar.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, agregar);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClick(int id) {
        Toast.makeText(getContext(),"botonEliminar: ",Toast.LENGTH_SHORT).show();
    }

    public homeFragment() {
        // Required empty public constructor
    }

    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
        productos = new ArrayList<>();
        recuperarProductos(URL_TodosProductos);

        List<String> listaOpciones = new ArrayList<>();
        listaOpciones.add("Todos los productos");
        listaOpciones.add("Productos del día");
        listaOpciones.add("Empaquetados");
        listaOpciones.add("Embotellados");
        listaOpciones.add("Dulces y golosinas");
        listaOpciones.add("Postres");

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.stile_text, listaOpciones);

        //-------------Configuraciones del RecyclerView de los productos---------------------------------
        listAdapter = new ListAdapter(productos, getContext());
        //Hay que instanciar este metodo para que los botones funcionen.
        listAdapter.setOnItemClickListener(this);
        vistaProductos = view.findViewById(R.id.listRecyclerView2);
        vistaProductos.setHasFixedSize(true);
        vistaProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        vistaProductos.setAdapter(listAdapter);

        //--------------------------------------------------------------------------
        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setText(listaOpciones.get(0),false);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                refrescarLista(item);
            }

        });

        //---------------funcionalidad del boton----------------------------------------
        botonAgregar = (MaterialButton) view.findViewById(R.id.btnAgregar);

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarProductos agregar = new agregarProductos();
                int id = -1;
                Bundle bundle = new Bundle();
                bundle.putInt("id_producto", id);
                agregar.setArguments(bundle);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, agregar);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void refrescarLista(String categoriaProducto){
        switch(categoriaProducto){
            case "Todos los productos": recuperarProductos(URL_TodosProductos); break;
            case "Productos del día": recuperarProductos(URL_ProductosDia); break;
            case "Empaquetados": recuperarProductos(URL_ProductosEmpaquetado); break;
            case "Embotellados": recuperarProductos(URL_ProductosEmbotellado); break;
            case "Postres": recuperarProductos(URL_ProductosPostre); break;
            case "Dulces y golosinas": recuperarProductos(URL_ProductosDulce); break;
        }
    }

    private void recuperarProductos(String URL){
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
            productos.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nombre = jsonObject.getString("nombre");
                int id = jsonObject.getInt("id_producto");
                String descripcion = jsonObject.getString("descripcion");
                double precio = jsonObject.getDouble("precio_unidad");
                int cantidad = jsonObject.getInt("cantidad");
                String categoria = jsonObject.getString("categoria");
                String imagen = jsonObject.getString("imagen");
                String creacion = jsonObject.getString("creacion");
                String actualizado = jsonObject.getString("actualizado");

                Producto producto = new Producto(id, nombre,descripcion,precio,cantidad,categoria,imagen,creacion,actualizado);
                productos.add(producto);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}