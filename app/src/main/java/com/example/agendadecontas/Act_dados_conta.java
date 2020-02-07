package com.example.agendadecontas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendadecontas.entities.Conta;
import com.example.agendadecontas.entities.Item;
import com.example.agendadecontas.entities.dao.ItemDAO;

import java.util.List;

public class Act_dados_conta extends AppCompatActivity {

    private EditText edtNomeCli;
    private EditText edtTelCli;
    private EditText edtValorPag;
    private EditText edtNomeProd;
    private EditText edtValorProd;
    private EditText edtQuantProd;
    private ListView lvProdutos;
    private EditText valorPagamento;
    private List<Item> itens;
    private Conta conta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dados_conta);

        edtNomeCli = (EditText) findViewById(R.id.edtNomeCli);
        edtTelCli = (EditText) findViewById(R.id.edtTelCli);

        Intent it = getIntent();
        conta = (Conta) it.getSerializableExtra("conta");

        edtNomeCli.setText(conta.getCliente().getNome());
        edtTelCli.setText(conta.getCliente().getTelefone());
        itens = conta.getItens();

        atualizarTotal();
        atualizaListaProdutos();

        registerForContextMenu(lvProdutos);
    }


    
    public void addItem(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Act_dados_conta.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        edtNomeProd = (EditText) mView.findViewById(R.id.edtNomeProd);
        edtValorProd = (EditText) mView.findViewById(R.id.edtValorProd);
        edtQuantProd = (EditText) mView.findViewById(R.id.edtQuantProd);
        Button btAddItem = (Button) mView.findViewById(R.id.btAdicionar);
        ImageButton btFechar = (ImageButton) mView.findViewById(R.id.btFechar);

        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        btAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeProd = edtNomeProd.getText().toString();
                double valorProd = Double.parseDouble(edtValorProd.getText().toString());
                int quant = Integer.parseInt(edtQuantProd.getText().toString());
                int id_cliente = conta.getCliente().getId();

                Item item = new Item(id_cliente, nomeProd, valorProd, quant);
                ItemDAO itemDAO = new ItemDAO(getApplicationContext());
                itemDAO.inserir(item);

                itens.add(item);
                atualizaListaProdutos();

                alertDialog.hide();
                alertDialog.dismiss();
                alertDialog.cancel();

            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                alertDialog.dismiss();
                alertDialog.cancel();
            }
        });

    }

    public void pagamento(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Act_dados_conta.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_reg_pagamento, null);

        edtValorPag = (EditText) mView.findViewById(R.id.edtValorPag);
        Button btPagamento = (Button) mView.findViewById(R.id.btPagamento);
        ImageButton btFechar = (ImageButton) mView.findViewById(R.id.btFechar);

        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        btPagamento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                double valorPag = Double.parseDouble(edtValorPag.getText().toString());
                if (conta != null) {
                    conta.setTotalConta(conta.getTotalConta() - valorPag);
                    //Falta o update

                    //
                    Act_dados_conta.this.atualizarTotal();
                    Toast.makeText(Act_dados_conta.this, "Registro de pagamento realizado com sucesso!",Toast.LENGTH_LONG).show();
                    alertDialog.hide();
                    alertDialog.dismiss();
                    alertDialog.cancel();
                }

            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                alertDialog.dismiss();
                alertDialog.cancel();
            }
        });
    }

    public void mostrarHistorico(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Act_dados_conta.this);
        final View mView = getLayoutInflater().inflate(R.layout.historico_pagamentos, null);

        ImageButton btFechar = (ImageButton) mView.findViewById(R.id.btFechar);

        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                alertDialog.dismiss();
                alertDialog.cancel();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_dados_conta, menu);
        return true;
    }

    public void atualizarConta(MenuItem item) {

    }

    public void fecharActDados(MenuItem item) {
        this.finish();
    }

    private void atualizaListaProdutos() {
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this,android.R.layout.simple_list_item_1, itens);
        lvProdutos = (ListView) findViewById(R.id.lvProdutos);
        lvProdutos.setAdapter(adapter);
    }

    private void atualizarTotal() {
        TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotal.setText("Total: R$"+ String.format("%.2f",conta.getTotalConta()));
    }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Item itemExcluir = itens.get(menuInfo.position);
    /*
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmete excluir a conta de: "+contaExcluir.getCliente().getNome()+" ?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contaDAO.excluir(contaExcluir);
                        atualizarListaContas();
                    }
                }).create();
        dialog.show();
        */
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_excluir, menu);
    }

}
