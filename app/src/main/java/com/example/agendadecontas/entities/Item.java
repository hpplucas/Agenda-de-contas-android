package com.example.agendadecontas.entities;

import java.io.Serializable;

public class Item implements Serializable {

    private int id;
    private int id_cliente;
    private String nome;
    private double preco;
    private int quantidade;
    private double valor_total;

    public Item(){}

    public Item(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        calcularValor_total();
    }

    public Item(int id_cliente, String nome, double preco) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = 1;
        calcularValor_total();
    }

    public Item(int id_cliente, String nome, double preco, int quantidade) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.calcularValor_total();
    }

    public int getId() {
        return id;
    }


    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double calcularValor_total() {
        valor_total = preco * quantidade;
        return valor_total;
    }

    public double getValor_total(){
        return valor_total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }


    @Override
    public String toString() {
        return quantidade + "     "+nome+ "    R$"+String.format("%.2f",preco) + "   Total: R$"+String.format("%.2f",valor_total);
    }
}
