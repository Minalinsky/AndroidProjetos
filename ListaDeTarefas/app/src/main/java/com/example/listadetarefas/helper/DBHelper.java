package com.example.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "TAREFAS_DB";
    public static String TABELA_TAREFAS = "tarefas";


    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // O método é chamado apenas uma vez quando o usuária instala e abre o app
        //a chave primária das tuplas da tabela de tarefas é o id com autoincrement
        String sqlCriaTabelaTarefas = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT NOT NULL)";
        try{
            sqLiteDatabase.execSQL(sqlCriaTabelaTarefas);
            Log.i("INFO DB", "Sucesso ao criar a tabela na DB");

        }catch(Exception e){
            Log.i("INFO_DB", "Erro ao criar a tabela: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //O método é chamado quando houver uma atualização no all (VERSION é diferente)

    }
}
