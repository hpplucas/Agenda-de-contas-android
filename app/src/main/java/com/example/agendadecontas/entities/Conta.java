package com.example.agendadecontas.entities;

import java.io.Serializable;
import java.util.List;

public class Conta implements Serializable {



    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private double totalConta = 0;


    public Conta(){}

    public Conta(Cliente cliente, List<Item> itens) {
        this.cliente = cliente;
        this.itens = itens;
    }

    public Conta(int id, Cliente cliente, List<Item> itens, double totalConta) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.totalConta = totalConta;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }



    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public void setTotalConta(double total) {
        this.totalConta = total;
    }

    public Double calcTotalConta() {

        if (itens != null) {

            for (Item i : itens) {
                totalConta += i.getValor_total();
            }
            return totalConta;

        }
        return null;
    }

    public double getTotalConta() {
        return totalConta;
    }

 @Override
   public String toString() {
    return "Cliente: "+cliente.getNome()+"\nTotal: R$"+String.format("%.2f",totalConta);
  }


}
