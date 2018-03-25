package com.example.anafl.comilex.Objetos;

/**
 * Created by batut on 19/03/2018.
 */

public class Comprador {

    public String nomeC;
    public String emailC;
    public String cepC;
    public String contatoC;
    public String senhaC;

    public Comprador(){

    }

    public Comprador(String nome, String email, String cep, String contato, String senha){
        this.nomeC = nome;
        this.emailC = email;
        this.cepC = cep;
        this.contatoC = contato;
        this.senhaC = senha;
    }
}
