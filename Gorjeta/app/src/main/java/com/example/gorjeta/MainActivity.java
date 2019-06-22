package com.example.gorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private TextView gorjeta, total, porcentagem;
    private SeekBar skbar;
    private NumberFormat nf;//usado para formatar double pra mostrar 2 casas decimais

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.edTxtInput);
        skbar = findViewById(R.id.seekbar);
        gorjeta = findViewById(R.id.txtGorjeta);
        total = findViewById(R.id.txtTotal);
        porcentagem = findViewById(R.id.txtPorcentagem);

        skbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(inputVazio()){
                    Toast.makeText(getApplicationContext(), "Por favor, digite um número antes de calcular o valor", Toast.LENGTH_SHORT).show();

                }
                else {
                    int skbarValue = skbar.getProgress(); //lendo porcentagem na seekbar
                    double valor = Double.parseDouble(input.getText().toString()); //lendo input do valor do usuario\
                    double gorjetaVal = calculaGorjeta(valor, skbarValue);
                    nf = NumberFormat.getInstance(); //usado para formatar double pra mostrar 2 casas decimais
                    nf.setMaximumFractionDigits(2);
                    nf.setMinimumFractionDigits(2);
                    nf.setRoundingMode(RoundingMode.HALF_UP);

                    porcentagem.setText(skbarValue + "%");
                    gorjeta.setText("R$ " + nf.format(gorjetaVal));
                    total.setText("R$ " + nf.format(calculaTotal(valor, gorjetaVal)));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    //calcula o valor da gorjeta
    public double calculaGorjeta(double valor, int skbarVal){
        return valor*skbarVal/100;
    }

    public double calculaTotal(double valor, double gorjeta){
        return valor + gorjeta;
    }

    //verifica se o usuario deixou a entrada em branco antes de rolar a seekbar
    public boolean inputVazio(){
        String input = this.input.getText().toString();
        if(input == null || input.equals(""))
            return true;
        else
            return false;
    }
}
