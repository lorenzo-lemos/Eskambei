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

public class RegistrarUsuario extends AppCompatActivity {

    private EditText editLogin, editPassword, editPasswordConfirmation;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
        editPasswordConfirmation = findViewById(R.id.editPasswordConfirmation);

        mAuth = FirebaseAuth.getInstance();
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent menuPrincipal = new Intent(RegistrarUsuario.this,DadosCadastrais.class);
            startActivity(menuPrincipal);
            finish();
        }
    }

    public void registrarUsuario(View view) {
        String login = editLogin.getText().toString();
        String password = editPassword.getText().toString();
        String confPassword = editPasswordConfirmation.getText().toString();

        if (login.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
            Toast.makeText(RegistrarUsuario.this, "Preencha os Campos Corretamente!", Toast.LENGTH_SHORT).show();
        }else{
            if(password != confPassword) {
                Toast.makeText(RegistrarUsuario.this, "Senhas incompatíveis!", Toast.LENGTH_SHORT).show();

            }else {
                mAuth.createUserWithEmailAndPassword(login, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrarUsuario.this, "Usuário Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    Toast.makeText(RegistrarUsuario.this, "Falha ao Cadastrar Usuário!", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        }
    }

    public void paginaInicial(View view) {
        Intent paginaInicial = new Intent(RegistrarUsuario.this, PaginaInicial.class);
        startActivity(paginaInicial);
        finish();
    }
}
