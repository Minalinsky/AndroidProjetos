package com.example.postagensredesocial.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postagensredesocial.R;
import com.example.postagensredesocial.adapter.AdapterPostagem;
import com.example.postagensredesocial.helper.DBHelper;
import com.example.postagensredesocial.model.Postagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 234; //requestCode

    private RecyclerView rv;
    private LinearLayout linearLayout;
    private TextInputEditText txtInputPost;
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
        linearLayout = findViewById(R.id.inputLayout);
        txtInputPost = findViewById(R.id.txtInputPost);

        //Definindo layout para o RecyclerView
        //inner class
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        //inicializando fab (adicionando evento de clique)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int linearLayoutVisibility = linearLayout.getVisibility();
                String mensagem = txtInputPost.getText().toString();

                if(linearLayoutVisibility == View.GONE ){
                    //torna input field visível e troca ícone do FAB
                    linearLayout.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.ic_send_post);

                }else if(linearLayoutVisibility == View.VISIBLE && mensagem.equals("")){
                    linearLayout.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.ic_fab);
                }
                else if(linearLayoutVisibility == View.VISIBLE){
                    //abre galeria de fotos para upload
                    selecionaFotos();
                    linearLayout.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.ic_fab);
                }
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
        if(resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            switch (requestCode) { //o código de PICK_IMAGE_REQUEST definimos
                case 234:
                    String nome = "Quik19"; //TROCAR NOME USUARIO LOGIN
                    String descricao = txtInputPost.getText().toString();
                    Postagem p = montaPostSemFoto(nome, descricao);
                    db.escrevePostagem(p, filePath);
                    break;
            }
        }

    }

    public Postagem montaPostSemFoto(String nome, String descricao){ //pega as entradas da interface
        Postagem p = new Postagem();
        p.setNome(nome);
        p.setDescricao(descricao);
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
