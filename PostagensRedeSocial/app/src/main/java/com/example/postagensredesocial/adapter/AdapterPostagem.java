package com.example.postagensredesocial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postagensredesocial.R;
import com.example.postagensredesocial.model.Postagem;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.Stack;

/*Dentro de adapter criamos uma classe do tipo ViewHolder, que recebe em seu construtor uma View
    Essa View é criada a partir de um inflate do layout que criamos em xml
    Esse layout é correspondente a cada elemento da Lista (cardView ou RecyclerView) que será mostrado na tela

    Pegamos o layout > Criamos uma view com esse layout no método onCreateViewHolder, retornando um objeto da classe ViewHolder que criamos > Colocamos o conteúdo a ser mostrado dentro do ViewHolder no método (onBindViewHolder)

*/
public class AdapterPostagem extends RecyclerView.Adapter<AdapterPostagem.HolderPostagem> {

    private Stack<Postagem> listaPostagem;

    public AdapterPostagem(Stack<Postagem> lp){
        this.listaPostagem = lp;
    }

    @NonNull
    @Override
    public HolderPostagem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //criar o layout para o CardView (será cada elemento do CardView)

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.postagem_layout, parent, false);
        return new HolderPostagem(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPostagem holder, int position) {
            Postagem p = listaPostagem.get(listaPostagem.size() - position - 1);
            //Recebe holder com a view do item do recyclerView
            holder.nome.setText(p.getNome());
            holder.descricao.setText(p.getDescricao());
            holder.data.setText("19/07/2019"); //data estática

            UrlImageViewHelper.setUrlDrawable(holder.foto, p.getFotourl());
    }

    @Override
    public int getItemCount() {
        return listaPostagem.size();
    }

    public class HolderPostagem extends RecyclerView.ViewHolder{
        private TextView nome, data, descricao;
        private ImageView foto;

        public HolderPostagem(View view){
            super(view);
            this.nome = view.findViewById(R.id.txtNome);
            this.data = view.findViewById(R.id.txtData);
            this.descricao = view.findViewById(R.id.txtDescricao);
            this.foto = view.findViewById(R.id.imgPostagem);

        }

    }

}
