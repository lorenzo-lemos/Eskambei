package com.example.eskambei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuPrincipal extends AppCompatActivity {

    private TextView textWelcome;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        textWelcome = findViewById(R.id.textWelcome);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        textWelcome.setText("Bem-vindo, "+user.getEmail());
    }

    public void deslogarUsuario(View view){
        mAuth.signOut();
        Intent inicio = new Intent(MenuPrincipal.this, PaginaInicial.class);
        startActivity(inicio);
        finish();
    }

    public void editarPerfil(View view) {
        Intent editarPerfil = new Intent(MenuPrincipal.this,EditarPerfil.class);
        startActivity(editarPerfil);
        finish();
    }
}
