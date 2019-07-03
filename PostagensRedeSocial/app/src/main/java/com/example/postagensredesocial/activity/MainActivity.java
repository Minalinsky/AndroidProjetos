package com.example.postagensredesocial.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postagensredesocial.R;
import com.example.postagensredesocial.adapter.AdapterPostagem;
import com.example.postagensredesocial.helper.DBHelper;
import com.example.postagensredesocial.model.Postagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 234; //requestCode

    private RecyclerView rv;
    private ArrayList<Postagem> listaPostagens = new ArrayList<Postagem>();
    private FloatingActionButton fab;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper();

        rv = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.myFabButton);

        //Definindo layout para o RecyclerView
        //inner class
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        //inicializando fab (adicionando evento de clique)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionaFotos();
            }
        });

        //lista postagens
        this.prepararPostagens();
    }

    //abre a galeria de fotos para o usuario selecionar
    public void selecionaFotos(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //requisitando ao android para "escolher uma imagem"
        startActivityForResult(Intent.createChooser(intent, "Selecione a Imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 234 && resultCode == RESULT_OK && data != null){ //o código de PICK_IMAGE_REQUEST definimos
            Uri filePath = data.getData();
            Postagem p = montaPostSemFoto();
            db.escrevePostagem(p, filePath);
        }

    }

    public Postagem montaPostSemFoto(){ //deverá pegar as entradas da interface
        Postagem p = new Postagem();
        p.setDescricao("I'm just a memer I meme all day");
        p.setNome("NO WOWLRLD OADONT KNOW");
        return p;
    }

    //requisita dados do FirebaseDB
    public void prepararPostagens(){
        db.getPostagensDBRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPostagens.clear();
                for(DataSnapshot d :  dataSnapshot.getChildren()){
                    Postagem post = d.getValue(Postagem.class);
                    listaPostagens.add(post);
                }
                Log.i("PostAVISO", "tamanho da lista com todas as postagens DENTRO DE DBHelper.leTodasPostagens(): " + listaPostagens.size());
                rv.setAdapter(new AdapterPostagem(listaPostagens));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("AVISO", "Erro ao ler todas as postagens do DB");
            }
        });
    }
}
