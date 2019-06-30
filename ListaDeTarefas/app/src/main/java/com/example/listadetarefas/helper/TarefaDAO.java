package com.example.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements InterfaceTarefaDAO {

    private SQLiteDatabase dbEscreve;//usado para escrever nas tabelas
    private SQLiteDatabase dbLe; //usado apra a leitura das tabelas
    private Context context;

    public TarefaDAO(Context c){
        context = c;
        DBHelper db = new DBHelper(c);
        dbEscreve = db.getWritableDatabase();
        dbLe = db.getReadableDatabase();

    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        try{
            ContentValues cv = new ContentValues();
            cv.put("descricao", tarefa.getDescricao());
            dbEscreve.insert(DBHelper.TABELA_TAREFAS, null, cv);

            Log.i("INFO", "Tarefa Salva com Sucesso!");
        }catch(Exception e){
            Log.e("INFO", "Erro ao salvar tarefa: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("descricao", tarefa.getDescricao()); // vamos atualizar a coluna "descricao" com o valor que vem de tarefa

            String[] args = {tarefa.getId().toString()};
            dbEscreve.update(DBHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO", "Tarefa Atualizada com Sucesso!");
            return true;
        }catch(Exception e){
            Log.e("INFO", "Erro ao atualizar tarefa: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        try{
            String[] args = {tarefa.getId().toString()};
            dbEscreve.delete(DBHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "Sucesso ao deletar");
            return true;
        }catch(Exception e){
            Log.e("INFO", "Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefaList = new ArrayList<Tarefa>();
        String sqlListar = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + ";";

        Cursor cursor =  dbLe.rawQuery(sqlListar, null);
        cursor.moveToFirst();

        while(cursor.moveToNext()){
            Tarefa t = new Tarefa();
            t.setId(cursor.getLong(cursor.getColumnIndex("id")));
            t.setDescricao(cursor.getString(cursor.getColumnIndex("descricao"))); //recuperamos uma String da coluna com o nome "descricao"

            tarefaList.add(t);
        }
        return tarefaList;
    }
}
