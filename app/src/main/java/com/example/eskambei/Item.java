package com.example.eskambei;

public class Item {

    private String idUsuario;
    private String nome;
    private String categoria;
    private String estado;

    public Item(String idUsuario, String nome, String categoria, String estado) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.categoria = categoria;
        this.estado = estado;
    }

    public Item() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
