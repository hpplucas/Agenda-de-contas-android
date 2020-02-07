package com.example.agendadecontas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "dbagenda";
    private static final int version = 1;

    public Conexao(Context context) {

        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE clientes (" +
                "    _id   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "    nome VARCHAR(100)," +
                "    telefone VARCHAR(11)" +
                ");");

        db.execSQL("CREATE TABLE itens (" +
                "    _id   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "    nome VARCHAR(100)," +
                "    preco DOUBLE," +
                "    quantidade INTEGER,"+
                "    valorTotal,"+
                "    id_cliente INTEGER REFERENCES clientes (_id) NOT NULL"+
                ");");
        db.execSQL("CREATE TABLE contas (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " totalConta DOUBLE NOT NULL," +
                "   id_cliente INTEGER REFERENCES clientes (_id) NOT NULL"+
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
