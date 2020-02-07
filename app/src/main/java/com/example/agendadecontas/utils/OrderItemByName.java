package com.example.agendadecontas.utils;

import com.example.agendadecontas.entities.Item;

import java.util.Comparator;

public class OrderItemByName implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o1.getNome().compareToIgnoreCase(o2.getNome());
    }
}
