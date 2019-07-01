package com.example.alysonfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth usuarioAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //exemplo de criação de nó no DB
        rootRef.child("usuarios").child("nome").setValue("Maria");
        DatabaseReference usuarioDBRef = rootRef.child("usuarios");

        usuarioDBRef.child("nome").setValue("João");
        //usuarioDBRef.child("nome")

        //exemplo de cadastro de usuario no Firebase
        usuarioAuth.createUserWithEmailAndPassword("alyson1907@gmail.com", "123456789").addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("CREATE USER", "Sucesso");
                }else{
                    Log.i("CREATE USER", "Falha");
                }
            }
        });

        //Verificando se o usuário está logado
        //Se não for nulo, temos um usuário logado
        if(usuarioAuth.getCurrentUser() != null){

        }


    }
}
