package com.example.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ResultadoActivity extends AppCompatActivity {
    private ImageView imgResultado;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        //Referenciando os elementos da view
        imgResultado = findViewById(R.id.imgResultado);
        btnVoltar = findViewById(R.id.btnVoltar);

        Bundle bundle = getIntent().getExtras();
        int vencedor = bundle.getInt("vencedor");

        if(vencedor == 0){ //cara
            imgResultado.setImageResource(R.drawable.moeda_cara);
        }
        else if(vencedor == 1){
            imgResultado.setImageResource(R.drawable.moeda_coroa);
        }

        //Evento de cli que para o botao Voltar
        btnVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish(); //finaliza a activity
            }
        });

    }
}
