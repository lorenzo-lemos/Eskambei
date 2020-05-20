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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditarPerfil extends AppCompatActivity {

    private EditText editNome, editSobrenome, editEndereço, editTelefone;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        editNome = findViewById(R.id.editNome);
        editSobrenome = findViewById(R.id.editSobrenome);
        editEndereço = findViewById(R.id.editEndereço);
        editTelefone = findViewById(R.id.editTelefone);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    public void menuPrincipal(View view) {
        Intent menuPrincipal = new Intent(EditarPerfil.this,MenuPrincipal.class);
        startActivity(menuPrincipal);
        finish();
    }

    public void editarPerfil(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        String nome = editNome.getText().toString();
        String sobrenome = editSobrenome.getText().toString();
        String endereço = editEndereço.getText().toString();
        int telefone = Integer.parseInt(editTelefone.getText().toString());

        Usuario usuario = new Usuario(id,nome,sobrenome,endereço,telefone);

        db.collection("usuarios").add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String message = "Perfil Editado!";
                        Toast.makeText(EditarPerfil.this, message, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Falha ao Editar Perfil!";
                        Toast.makeText(EditarPerfil.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
