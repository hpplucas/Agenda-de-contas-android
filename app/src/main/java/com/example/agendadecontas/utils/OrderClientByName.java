package com.example.agendadecontas.utils;

import com.example.agendadecontas.entities.Conta;
import com.example.agendadecontas.entities.Item;

import java.util.Comparator;

public class OrderClientByName implements Comparator<Conta> {

    @Override
    public int compare(Conta c1, Conta c2) {
        return c1.getCliente().getNome().compareToIgnoreCase(c2.getCliente().getNome());
    }
}
