package com.example.postagensredesocial.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 234; //requestCode

    private RecyclerView rv;
    private LinearLayout linearLayout;
    private TextInputEditText txtInputPost;
    private Stack<Postagem> listaPostagens = new Stack<Postagem>();
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper();

        rv = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.myFabButton);
        progressBar = findViewById(R.id.progressBar);
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
                //se o fab foi clicado pela primeira vez
                if (linearLayoutVisibility == View.GONE) {
                    //torna input field visível e troca ícone do FAB
                    linearLayout.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.ic_send_post);
                    //se é clicado sem ter digitado nada, fecha o InputTxt
                } else if (linearLayoutVisibility == View.VISIBLE && mensagem.equals("")) {
                    linearLayout.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.ic_fab);
                }//se clicou no fab para enviar e tem algo digitado
                else if (linearLayoutVisibility == View.VISIBLE) {
                    //abre galeria de fotos para upload
                    selecionaFotos();
                    linearLayout.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.ic_fab);

                }
            }
        });

        //lista as postagens
        this.prepararPostagens();
    }

    public void limpaTxtInput() {
        txtInputPost.setText("");
    }

    //abre a galeria de fotos para o usuario selecionar
    public void selecionaFotos() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //requisitando ao android para "escolher uma imagem"
        startActivityForResult(Intent.createChooser(intent, "Selecione a Imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            switch (requestCode) { //o código de PICK_IMAGE_REQUEST definimos
                case 234:
                    String nome = "Quik19"; //TROCAR NOME USUARIO LOGIN
                    String descricao = txtInputPost.getText().toString();
                    Postagem p = montaPostSemFoto(nome, descricao);
                    //progressBar.setVisibility(View.VISIBLE);
                    db.escrevePostagem(p, filePath);
                    limpaTxtInput();
                    //progressBar.setVisibility(View.GONE);
                    break;
            }
        }

    }

    public Postagem montaPostSemFoto(String nome, String descricao) { //pega as entradas da interface
        Postagem p = new Postagem();
        p.setNome(nome);
        p.setDescricao(descricao);
        return p;
    }

    //requisita dados do FirebaseDB
    public void prepararPostagens() {
        db.getPostagensDBRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPostagens.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Postagem post = d.getValue(Postagem.class);
                    listaPostagens.push(post);
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


    public void mostraProgresso() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void ocultaProgresso() {
        progressBar.setVisibility(View.GONE);
    }
}