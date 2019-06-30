package com.example.listadetarefas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listadetarefas.R;
import com.example.listadetarefas.adapter.TarefaAdapter;
import com.example.listadetarefas.helper.DBHelper;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;
import com.example.listadetarefas.helper.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerLista;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<Tarefa>(); //lista das tarefas recuperadas do DB para serem mostradas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerLista = findViewById(R.id.recyclerLista);

        //criando Mouse Event Listener (eventos de clique)
        recyclerLista.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerLista, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) { //Toque curto para atualizar
                //recuperando tarefa selecionada
                Tarefa tarefaSelecionada = listaTarefas.get(position);

                //enviando os dados da tarefa para a activity AddTarefaActivity
                Intent intent = new Intent(getApplicationContext(), AddTarefaActivity.class);
                intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) { //Toque longo para deletar
                //Recuperando tarefa que sera deletada
                final Tarefa tarefaDeletar = listaTarefas.get(position);


                //Mensagem de confirmação de exclusão da Tarefa:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Excluir");
                dialog.setMessage("Deseja mesmo excluir a tarefa '" + tarefaDeletar.getDescricao() + "'?");
                dialog.setNegativeButton("Não", null);
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                        if(tarefaDAO.deletar(tarefaDeletar)){
                            Toast.makeText(MainActivity.this, "Tarefa Excluida!", Toast.LENGTH_SHORT).show();
                            carregarListaTarefas();
                        }else{
                            Toast.makeText(MainActivity.this, "Erro ao Excluir tarefa!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.create();
                dialog.show();

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaTarefas(){
        //Listando as tarefas estaticamente

        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar(); //recuperando a lista de tarefas do DB

        /*Configurando Adapter
            Criamos uma nova classe Adapter
            Dentro dela criamos uma inner Class, que será nosso ViewHolder
         */
        tarefaAdapter = new TarefaAdapter(listaTarefas); //instanciando adapter

        //Configurando o recyclerView
        //cria LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //atribui o layoutManager ao nosso recyclerView
        recyclerLista.setLayoutManager(layoutManager);
        recyclerLista.setHasFixedSize(true);
        recyclerLista.addItemDecoration(new DividerItemDecoration(getApplicationContext(), RecyclerView.VERTICAL));
        //atribuindo adapter
        recyclerLista.setAdapter(tarefaAdapter);
    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }

}
