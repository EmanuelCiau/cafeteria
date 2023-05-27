package com.example.cafeteria;

import com.google.gson.Gson;

import java.util.List;

public class Pedido {

    int id;
    int id_cliente;
    String nombre_cliente;
    String telefono_cliente;
    String tipo_pago;
    Double total;
    String hora_compra;
    String hora_recogida;
    String estatus;
    List<element_producto> compra;

    public static Pedido fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Pedido.class);
    }

    public Pedido(int id, int id_cliente, String nombre_cliente, String telefono_cliente, String tipo_pago, Double total, String hora_compra, String hora_recogida, String estatus//, List<element_producto> compra
    ) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.telefono_cliente = telefono_cliente;
        this.tipo_pago = tipo_pago;
        this.total = total;
        this.hora_compra = hora_compra;
        this.hora_recogida = hora_recogida;
        this.estatus = estatus;
        //this.compra = compra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(String telefono_cliente) {
        this.telefono_cliente = telefono_cliente;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getHora_compra() {
        return hora_compra;
    }

    public void setHora_compra(String hora_compra) {
        this.hora_compra = hora_compra;
    }

    public String getHora_recogida() {
        return hora_recogida;
    }

    public void setHora_recogida(String hora_recogida) {
        this.hora_recogida = hora_recogida;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<element_producto> getCompra() {
        return compra;
    }

    public void setCompra(List<element_producto> compra) {
        this.compra = compra;
    }
}
