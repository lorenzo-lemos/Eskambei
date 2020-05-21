package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NovoItem extends AppCompatActivity {

    private EditText editNome, editCategoria;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        editNome = findViewById(R.id.editNome);
        editCategoria = findViewById(R.id.editCategoria);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = FirebaseFirestore.getInstance();
    }

    public void menuItems(View view) {
        Intent menuItems = new Intent(NovoItem.this, MenuItem.class);
        startActivity(menuItems);
        finish();
    }

    public void adicionarItem(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        RadioGroup rgEstado = (RadioGroup)findViewById(R.id.rgEstado);
        String nome = editNome.getText().toString();
        String categoria = editCategoria.getText().toString();
        String estado = rgEstado.getCheckedRadioButtonId() == R.id.rbNovo ? "Novo" : "Usado";

        Map<String,Object> dadosItem = new HashMap<>();

        dadosItem.put("nome",nome);
        dadosItem.put("categoria",categoria);
        dadosItem.put("estado",estado);

        db.collection("items").document(user.getUid())
                .set(dadosItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String message = "Item Cadastrado com Sucesso!";
                        Toast.makeText(NovoItem.this,message,Toast.LENGTH_SHORT).show();
                        menuItems(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Falha ao Adicionar Item!";
                        Toast.makeText(NovoItem.this,message,Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
