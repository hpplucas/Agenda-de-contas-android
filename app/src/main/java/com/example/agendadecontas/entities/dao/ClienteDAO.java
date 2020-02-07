package com.example.agendadecontas.entities.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.example.agendadecontas.db.Conexao;
import com.example.agendadecontas.entities.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ClienteDAO(Context c) {
        conexao = new Conexao(c);
    }

    public int inserir(Cliente c) {
        ContentValues values = new ContentValues();
        values.put("nome", c.getNome());
        values.put("telefone", c.getTelefone());

        banco = conexao.getWritableDatabase();
        long i = banco.insert("clientes", null, values);

        int id = 0;

        if (i >= 0) id = Integer.parseInt(String.valueOf(i));

        banco.close();

        return id;
    }

    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM clientes ");
        banco = conexao.getReadableDatabase();
        Cursor cursor = banco.rawQuery(sb.toString(),null);
        while (cursor.moveToNext()) {
            Cliente c = new Cliente();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setTelefone(cursor.getString(2));

            clientes.add(c);
        }

        banco.close();
        return clientes;
    }

    public Cliente findById(int id) {
        /*
        Cliente cliente = new Cliente();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM clientes WHERE _id = "+id);
        banco = conexao.getReadableDatabase();
        */

        List<Cliente> clientes = findAll();

        for (Cliente c : clientes) {
            if (c.getId() == id) {
                Cliente cliente = new Cliente(c.getId(), c.getNome(), c.getTelefone());
                return cliente;
            }
        }

        return null;
    }

    public Cliente findByName(String nome) {

        List<Cliente> clientes = findAll();

        for (Cliente c : clientes) {
            if (c.getNome().equals(nome)) {
                Cliente cliente = new Cliente(c.getId(), c.getNome(), c.getTelefone());
                return cliente;
            }
        }


        return null;
    }


    public int getClienteId(String nome){

        List<Cliente> clientes = findAll();

        for (Cliente c : clientes) {
            if (c.getNome().equals(nome)) {
                return c.getId();
            }
        }

        return 0;
    }

}
