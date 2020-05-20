package com.example.eskambei;

public class Usuario {
    private String id;
    private String nome;
    private String sobrenome;
    private String endereço;
    private int telefone;

    public Usuario(String id, String nome, String sobrenome, String endereço, int telefone) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereço = endereço;
        this.telefone = telefone;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}
