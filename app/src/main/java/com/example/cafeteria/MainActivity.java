package com.example.cafeteria;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    MaterialButton btniniciar;
    EditText usuario,contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btniniciar = (MaterialButton) findViewById(R.id.btnIniciarSesion);
        usuario = (EditText) findViewById(R.id.txtCorreo);
        contrasena = (EditText) findViewById(R.id.txtContrasena);

        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUSuario("https://afflated-sentries.000webhostapp.com/recuperarAdmin.php");
            }
        });
    }

    private void validarUSuario(String url){
        StringRequest requerido = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);
                if(!response.isEmpty()){
                    Intent inte = new Intent(getApplicationContext(), principal.class);
                    startActivity(inte);
                }else {
                    Toast.makeText(MainActivity.this,"Usuario o contraseña incorectas",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre",usuario.getText().toString());
                parametros.put("contrasena",contrasena.getText().toString());
                return parametros;
            }
        };
        RequestQueue respuesta = Volley.newRequestQueue(this);
        respuesta.add(requerido);
    }
}