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
import android.widget.ListView;

import com.example.agendadecontas.entities.Conta;
import com.example.agendadecontas.entities.dao.ContaDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvContas;
    ContaDAO contaDAO = new ContaDAO(this);
    private List<Conta> contasEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Conta> contas = contaDAO.findAll();
        contasEx = contas; //Não mexer pra não dar merda.

        lvContas = (ListView) findViewById(R.id.lista_contas);
        ArrayAdapter<Conta> adapter = new ArrayAdapter<Conta>(this,android.R.layout.simple_list_item_1, contas);
        lvContas.setAdapter(adapter);


        lvContas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it = new Intent(getApplicationContext(), Act_dados_conta.class);
                it.putExtra("conta", contas.get(position));
                startActivity(it);

            }
        });


        registerForContextMenu(lvContas);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        return true;
    }

   public void cadastrar(MenuItem item) {
       Intent it = new Intent(this, Act_cadastrar_conta.class);
       startActivity(it);
   }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Conta contaExcluir = contasEx.get(menuInfo.position);

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

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_excluir, menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarListaContas();
    }

    private void atualizarListaContas() {
        lvContas = (ListView) findViewById(R.id.lista_contas);
        ContaDAO contaDAO = new ContaDAO(this);
        List<Conta> contas = contaDAO.findAll();
        ArrayAdapter<Conta> adapter = new ArrayAdapter<Conta>(this,android.R.layout.simple_list_item_1, contas);
        lvContas.setAdapter(adapter);
    }
}
