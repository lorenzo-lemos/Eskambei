package com.example.eskambei;

public class Item {

    private String nome;
    private String categoria;
    private String estado;

    public Item(String nome, String categoria, String estado) {
        this.nome = nome;
        this.categoria = categoria;
        this.estado = estado;
    }

    public Item() {
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
