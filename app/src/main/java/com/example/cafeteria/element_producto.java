package com.example.cafeteria;

import com.google.gson.Gson;

public class element_producto {
    int id_pedido;
    int id_producto;
    String nombre_producto;
    int cantidad_pedido;
    double precio_producto;

    public static element_producto fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, element_producto.class);
    }

    public element_producto(int id_pedido, int id_producto, String nombre_producto, int cantidad_pedido, double precio_producto) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.cantidad_pedido = cantidad_pedido;
        this.precio_producto = precio_producto;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public int getCantidad_pedido() {
        return cantidad_pedido;
    }

    public double getPrecio_producto() {
        return precio_producto;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public void setCantidad_pedido(int cantidad_pedido) {
        this.cantidad_pedido = cantidad_pedido;
    }

    public void setPrecio_producto(double precio_producto) {
        this.precio_producto = precio_producto;
    }


}
