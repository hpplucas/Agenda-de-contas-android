package com.example.agendadecontas;

import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agendadecontas.entities.Cliente;
import com.example.agendadecontas.entities.Conta;
import com.example.agendadecontas.entities.Item;
import com.example.agendadecontas.entities.dao.ClienteDAO;
import com.example.agendadecontas.entities.dao.ContaDAO;
import com.example.agendadecontas.entities.dao.ItemDAO;

import java.util.ArrayList;
import java.util.List;

public class Act_cadastrar_conta extends AppCompatActivity {

    private EditText edtNomeCli;
    private EditText edtTelCli;
    private EditText edtNomeProd;
    private EditText edtValorProd;
    private EditText edtQuantProd;
    private ListView lvProdutos;
    private List<Item> itens = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastrar_conta);

        edtNomeCli = (EditText) findViewById(R.id.edtNomeCli);
        edtTelCli = (EditText) findViewById(R.id.edtTelCli);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_sav_conta, menu);
        return true;
    }

    public void cadastrarConta(MenuItem item) {
        //Insere cliente
        String nomeCli = edtNomeCli.getText().toString();
        String telCli = edtTelCli.getText().toString();
        Cliente cliente = new Cliente(nomeCli, telCli);
        ClienteDAO cliDao = new ClienteDAO(this);
        cliDao.inserir(cliente);

        //Insere produto
        int cliId = cliDao.getClienteId(nomeCli); //Serve para vincular um item a um cliente
        ItemDAO itemDAO = new ItemDAO(this);

        for (Item i : itens) {
            i.setId_cliente(cliId);
            itemDAO.inserir(i);
        }

        //Insere conta
        cliente.setId(cliId);
        Conta conta = new Conta(cliente, itens);
        ContaDAO contaDAO = new ContaDAO(this);
        long rawc = contaDAO.inserir(conta);



        Toast.makeText(this, "Conta de:"+conta.getCliente().getNome()+" cadastrada com sucesso!!",Toast.LENGTH_LONG).show();
        this.finish();
    }


    public void addItem(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Act_cadastrar_conta.this);
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

                itens.add(new Item(nomeProd, valorProd, quant));
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

    private void atualizaListaProdutos() {
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this,android.R.layout.simple_list_item_1, itens);
        lvProdutos = (ListView) findViewById(R.id.lista_prod);
        lvProdutos.setAdapter(adapter);
    }

    public void fecharActCadastro(MenuItem item) {
        this.finish();
    }

    private void fechaDialog(AlertDialog a) {
        a.hide();
        a.dismiss();
        a.cancel();
    }


}
