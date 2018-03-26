package com.example.anafl.comilex.Objetos;

/**
 * Created by batut on 19/03/2018.
 */

public class Vendedor {

    public String nomeV;
    public String emailV;
    public String cepV;
    public String contatoV;
    public String senhaV;

    public Vendedor(){

    }

    public Vendedor(String nome, String email, String cep, String contato, String senha){
        this.nomeV = nome;
        this.emailV = email;
        this.cepV = cep;
        this.contatoV = contato;
        this.senhaV = senha;
    }
}
