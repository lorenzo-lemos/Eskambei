package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NovoItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editNome;
    private Spinner spCategoria;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        editNome = findViewById(R.id.editNome);
        spCategoria = findViewById(R.id.spCategoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categorias,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);
        spCategoria.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = FirebaseFirestore.getInstance();
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void menuItems(View view) {
        Intent menuItems = new Intent(NovoItem.this, MenuItem.class);
        startActivity(menuItems);
        finish();
    }
    public void adicionarItem(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        RadioGroup rgEstado = (RadioGroup)findViewById(R.id.rgEstado);
        String id = user.getUid();
        String nome = editNome.getText().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        String estado = rgEstado.getCheckedRadioButtonId() == R.id.rbNovo ? "Novo" : "Usado";

        Item item = new Item(id,nome,categoria,estado);

        db.collection("items").add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String message = "Item Cadastrado com Sucesso!";
                        Toast.makeText(NovoItem.this,message,Toast.LENGTH_SHORT).show();
                        menuItems(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Falha ao Cadastrar Item!";
                        Toast.makeText(NovoItem.this,message,Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
