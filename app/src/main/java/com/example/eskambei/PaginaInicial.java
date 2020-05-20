package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PaginaInicial extends AppCompatActivity {

    private EditText editLogin, editPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);

        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            Intent menuPrincipal = new Intent(PaginaInicial.this,MenuPrincipal.class);
            startActivity(menuPrincipal);
            finish();
        }
    }

    public void logarUsuario(View view){
        String login = editLogin.getText().toString();
        String password = editPassword.getText().toString();

        if(login.isEmpty() || password.isEmpty()){
            Toast.makeText(PaginaInicial.this,"Falha ao Autenticar!", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(login,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            }else{
                                Toast.makeText(PaginaInicial.this,"Falha ao Autenticar!", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    public void registrarUsuario(View view) {
        Intent registrarUsuario = new Intent(PaginaInicial.this,RegistrarUsuario.class);
        startActivity(registrarUsuario);
        finish();
    }
}
