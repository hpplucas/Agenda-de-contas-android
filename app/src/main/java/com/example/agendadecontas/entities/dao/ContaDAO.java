package com.example.agendadecontas.entities.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendadecontas.db.Conexao;
import com.example.agendadecontas.entities.Cliente;
import com.example.agendadecontas.entities.Conta;
import com.example.agendadecontas.entities.Item;
import com.example.agendadecontas.utils.OrderClientByName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Context context;

    public ContaDAO(Context c) {
        conexao = new Conexao(c);
        this.context = c;
    }

    public long inserir(Conta c) {
       // c.setTotalConta(200.00);
        ContentValues values = new ContentValues();
        values.put("totalConta", c.calcTotalConta());
        values.put("id_cliente", c.getCliente().getId());

        banco = conexao.getWritableDatabase();
        long i = banco.insert("contas", null, values);
        banco.close();
        return  i;
    }

    public List<Conta> findAll() {
        banco = conexao.getReadableDatabase();
        List<Conta> contas = new ArrayList<>();

        String query = "SELECT * FROM contas ";

        Cursor cursor = banco.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Conta c = new Conta();
            c.setId(cursor.getInt(0));
            c.setTotalConta(cursor.getDouble(1));
            int id_cliente = cursor.getInt(2);
            ClienteDAO cd = new ClienteDAO(context);
            Cliente cliente = cd.findById(id_cliente);
            c.setCliente(cliente);
            ItemDAO i = new ItemDAO(context);
            List<Item> itens = i.findByCliId(cliente.getId());
            c.setItens(itens);
            contas.add(c);
        }

        banco.close();
        //Collections.sort(contas,new OrderClientByName());
        return contas;
    }

    public Conta findById(int id) {
        banco = conexao.getReadableDatabase();

        String query = "SELECT * FROM contas WHERE _id = " + id;
        Cursor cursor = banco.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Conta conta = new Conta();
            conta.setId(cursor.getInt(0));
            conta.setTotalConta(cursor.getDouble(1));
            int idCli = cursor.getInt(2);
            ClienteDAO dao = new ClienteDAO(context);
            Cliente cliente = dao.findById(id);
            conta.setCliente(cliente);
            conta.setItens(null);

            return conta;
        }

        return null;
    }

    public void excluir(Conta conta) {
        int id = conta.getId();
        banco = conexao.getWritableDatabase();
        banco.delete("contas","_id = ?", new String[]{String.valueOf(id)});

        banco.close();
    }

}
