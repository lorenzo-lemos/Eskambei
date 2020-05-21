package com.example.eskambei;

public class Usuario {

    private String nome;
    private String sobrenome;
    private String endereço;
    private String telefone;

    public Usuario(String nome, String sobrenome, String endereço, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereço = endereço;
        this.telefone = telefone;
    }

    public Usuario() {
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
