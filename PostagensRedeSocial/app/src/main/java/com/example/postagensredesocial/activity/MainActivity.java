package com.example.postagensredesocial.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.postagensredesocial.R;
import com.example.postagensredesocial.adapter.AdapterPostagem;
import com.example.postagensredesocial.model.Postagem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<Postagem> listaPostagem = new ArrayList<Postagem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.recyclerView);
        //Definindo layout para o RecyclerView
        //inner class
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        this.prepararPostagens();

        //Definindo o Adapter
        rv.setAdapter(new AdapterPostagem(listaPostagem));
    }

    public void prepararPostagens(){ //poderíamos substituir esse método por uma leitura de arquivos, requisição dos dados de um DB, etc
        Postagem postagem = new Postagem("Alyson Maruyama", "Amazing view!", R.drawable.landscape);
        this.listaPostagem.add(postagem);

        postagem = new Postagem("Edward", "My cute dog", R.drawable.dog);
        this.listaPostagem.add(postagem);

        postagem = new Postagem("Carlton White", "Do you like my new car? #LetsGo", R.drawable.car);
        this.listaPostagem.add(postagem);

        postagem = new Postagem("Mariah", "Nothing better than a day of coding...", R.drawable.macbook);
        this.listaPostagem.add(postagem);
    }
}
