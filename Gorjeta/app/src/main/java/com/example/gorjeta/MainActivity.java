package com.example.gorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private TextView gorjeta, total, porcentagem;
    private SeekBar skbar;

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
                int skbarValue = skbar.getProgress(); //lendo porcentagem na seekbar
                double valor = Double.parseDouble(input.getText().toString()); //lendo input do valor do usuario\
                double gorjetaVal = calculaGorjeta(valor, skbarValue);

                porcentagem.setText(skbarValue + "%");
                gorjeta.setText("R$ " + gorjetaVal);
                total.setText("R$ " + calculaTotal(valor, gorjetaVal));

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
}
