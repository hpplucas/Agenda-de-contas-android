package com.example.agendadecontas.entities.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendadecontas.db.Conexao;
import com.example.agendadecontas.entities.Cliente;
import com.example.agendadecontas.entities.Item;
import com.example.agendadecontas.utils.OrderItemByName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ItemDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ItemDAO(Context c) {
        conexao = new Conexao(c);
    }

    public long inserir(Item item) {
        ContentValues values = new ContentValues();
        values.put("nome", item.getNome());
        values.put("preco", item.getPreco());
        values.put("quantidade", item.getQuantidade());
        values.put("valorTotal", item.getValor_total());
        values.put("id_cliente", item.getId_cliente());

        banco = conexao.getWritableDatabase();
        long i = banco.insert("itens", null, values);
        conexao.close();

        return i;
    }

    public List<Item> findAll() {
        List<Item> itens = new ArrayList<>();
        banco = conexao.getReadableDatabase();

        String query = "SELECT * FROM itens ";

        Cursor cursor = banco.rawQuery(query, null);

        while (cursor.moveToNext()) {
             Item i = new Item();
             i.setId(cursor.getInt(0));
             i.setNome(cursor.getString(1));
             i.setPreco(cursor.getDouble(2));
             i.setQuantidade(cursor.getInt(3));
             i.setValor_total(cursor.getDouble(4));
             i.setId_cliente(cursor.getInt(5));

             itens.add(i);
        }

        banco.close();
        return itens;
    }


    public List<Item> findByCliId(int id_cliente) {
        List<Item> itenscliente = new ArrayList<>();

        List<Item> aux = findAll();
        for (Item i : aux) {
            if (i.getId_cliente() == id_cliente) {
                itenscliente.add(i);
            }
        }

        Collections.sort(itenscliente, new OrderItemByName());
        return itenscliente;
    }

    public void deleteByCliId(int id) {
        banco = conexao.getWritableDatabase();

        banco.delete("itens", "id_cliente = ?", new String[]{String.valueOf(id)});

        banco.close();
    }

}
