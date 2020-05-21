package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DadosCadastrais extends AppCompatActivity {

    private EditText editNome, editSobrenome, editEndereço, editTelefone;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_cadastrais);

        editNome = findViewById(R.id.editNome);
        editSobrenome = findViewById(R.id.editSobrenome);
        editEndereço = findViewById(R.id.editEndereço);
        editTelefone = findViewById(R.id.editTelefone);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = FirebaseFirestore.getInstance();
    }

    public void menuPrincipal(View view) {
        Intent menuPrincipal = new Intent(DadosCadastrais.this,MenuPrincipal.class);
        startActivity(menuPrincipal);
        finish();
    }

    public void salvarPerfil(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        String nome = editNome.getText().toString();
        String sobrenome = editSobrenome.getText().toString();
        String endereço = editEndereço.getText().toString();
        String telefone = editTelefone.getText().toString();

        Map<String,Object> dadosUsuario = new HashMap<>();

        dadosUsuario.put("id",id);
        dadosUsuario.put("nome",nome);
        dadosUsuario.put("sobrenome",sobrenome);
        dadosUsuario.put("endereço",endereço);
        dadosUsuario.put("telefone",telefone);

        db.collection("usuarios").document(user.getUid())
                .set(dadosUsuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String message = "Dados Cadastrados com Sucesso!";
                        Toast.makeText(DadosCadastrais.this,message,Toast.LENGTH_SHORT).show();
                        menuPrincipal(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Falha ao Cadastrar Dados!";
                        Toast.makeText(DadosCadastrais.this,message,Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
