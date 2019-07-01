package com.example.atmconsultoria.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.atmconsultoria.R;

import mehdi.sakout.aboutpage.AboutPage;


public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //criando uma View com a biblioteca importada https://github.com/medyo/android-about-page
        View sobreView = new AboutPage(this)
                .setImage(R.drawable.logo)
                .addGroup("Fale Conosco")
                .addEmail("consultoria@atm.com")
                .addGroup("Nossas Redes Sociais")
                .addWebsite("https://www.linkedin.com/in/alysonmaruyama/", "Acesse nosso LinkedIn")
                .addGitHub("Minalinsky", "Nosso GitHub")
                .addFacebook("AlysonMatheus1907", "Facebook")
                .create();
        setContentView(sobreView);




        //setContentView(R.layout.activity_sobre);
    }
}
