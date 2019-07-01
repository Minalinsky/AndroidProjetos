package com.example.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.R;
import com.example.listadetarefas.helper.DBHelper;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AddTarefaActivity extends AppCompatActivity {

    private TextInputEditText txtAddTarefa;
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);
        txtAddTarefa = findViewById(R.id.txtAddTarefa);

        //Recuperando tarefa passada para a activity (se houver) caso seja edição
        tarefaSelecionada = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Atualizando a caixa de texto para mostrar a tarefa selecionada
        if(tarefaSelecionada != null){ //verificando se alguma tarefa foi selecionada e recebida por essa activity
            txtAddTarefa.setText(tarefaSelecionada.getDescricao());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //ao selecionar um elemento do menu
        //identificando qual elemento do menu foi clicado
        int itemId = item.getItemId();

        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext()); //instanciando DAO que irá gerenciar a DB usando o DBHelper que criamos
        switch(itemId){
            case R.id.menu_salvar:
                if(tarefaSelecionada != null){
                    //Edição de Tarefa
                    if(txtAddTarefa.getText().toString().equals("")) {
                        Toast.makeText(this, "Não deixe a tarefa em branco!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Tarefa novaTarefa = new Tarefa();
                        novaTarefa.setId(tarefaSelecionada.getId());
                        novaTarefa.setDescricao(txtAddTarefa.getText().toString());

                        //atualizando no DB
                        if(tarefaDAO.atualizar(novaTarefa)){
                            finish();
                            Toast.makeText(this, "Tarefa Atualizada!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Erro ao atualizar Tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else{
                    if(!txtAddTarefa.getText().toString().equals("")) { //verificando se o texto é vazio

                        Tarefa tarefa = new Tarefa();
                        tarefa.setDescricao(txtAddTarefa.getText().toString()); //recuperando Descricao da tarefa digitada pelo usuário na interface
                        tarefaDAO.salvar(tarefa);
                        Toast.makeText(this, "Tarefa Salva!", Toast.LENGTH_SHORT).show();
                        finish(); //fecha activity
                    }
                    else{
                        Toast.makeText(this, "Adicione uma descrição para a Tarefa!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
