package com.example.alcoolgasolina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private EditText edtTxtAlcool, edtTxtGas;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTxtAlcool = findViewById(R.id.edtTxtAlcool);
        edtTxtGas = findViewById(R.id.edtTxtGas);
        resultado = findViewById(R.id.txtResultado);
    }

    //Estamos simplesmente considerando 70% do preço
    public void calcula(View v){
        String strAlcool = edtTxtAlcool.getText().toString();
        String strGas = edtTxtGas.getText().toString();
        if(this.verificaCamposPreenchidos(strGas, strAlcool)){ //verifica se ambos os campos estão preenchidos
            double precoAlcool = Double.valueOf(strAlcool);
            double precoGas = Double.valueOf(strGas);
            double razaoPrecos = precoAlcool/precoGas;
            if(razaoPrecos < 0.7)
                resultado.setText("Vá de Álcool!");
            else if(razaoPrecos > 0.7)
                resultado.setText("Vá de Gasolina!");
            else
                resultado.setText("Ambos os preços são equivalentes!");
        }
        else
            resultado.setText("Preencha os campos primeiro!");

    }

    public boolean verificaCamposPreenchidos (String s, String t){//verifica se ambos os campos estão preenchidos
        if (s.equals("") || t.equals("") || s == null || t == null)
            return false;
        else
            return true;
    }

}
