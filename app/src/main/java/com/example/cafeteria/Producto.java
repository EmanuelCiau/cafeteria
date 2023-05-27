package com.example.cafeteria;

import com.google.gson.Gson;

public class Producto {
    Integer id;
    String nombre;
    String descripcion;
    Double precio_unidad;
    Integer cantidad;
    String categoria;
    String imagen;
    String creacion;
    String actualizado;

    public static Producto fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Producto.class);
    }

    public Producto(Integer id, String nombre, String descripcion, Double precio_unidad, Integer cantidad, String categoria, String imagen, String creacion, String actualizado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio_unidad = precio_unidad;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.imagen = imagen;
        this.creacion = creacion;
        this.actualizado = actualizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio_unidad() {
        return precio_unidad;
    }

    public void setPrecio_unidad(Double precio_unidad) {
        this.precio_unidad = precio_unidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }

    public String getActualizado() {
        return actualizado;
    }

    public void setActualizado(String actualizado) {
        this.actualizado = actualizado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio_unidad=" + precio_unidad +
                ", cantidad=" + cantidad +
                ", categoria='" + categoria + '\'' +
                ", imagen='" + imagen + '\'' +
                ", creacion='" + creacion + '\'' +
                ", actualizado='" + actualizado + '\'' +
                '}';
    }
}
