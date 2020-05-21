package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuItem extends AppCompatActivity {

    private TextView textNomeItem, textCategoriaItem, textEstadoItem;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_items);

        textNomeItem = findViewById(R.id.textNomeItem);
        textCategoriaItem = findViewById(R.id.textCategoriaItem);
        textEstadoItem = findViewById(R.id.textEstadoItem);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = FirebaseFirestore.getInstance();
        carregarDados(null);
    }

    public void menuPrincipal(View view) {
        Intent menuPrincipal = new Intent(MenuItem.this,MenuPrincipal.class);
        startActivity(menuPrincipal);
        finish();
    }

    public void adicionarItem(View view) {
        Intent adicionarItem = new Intent(MenuItem.this,NovoItem.class);
        startActivity(adicionarItem);
        finish();
    }

    public void carregarDados(View view){
        CollectionReference items = db.collection("items");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        items.whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Item> listItem = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listItem.add(document.toObject(Item.class));
                            }
                            String nome ="";
                            String categoria ="";
                            String estado ="";
                            for(Item i : listItem){
                                nome = i.getNome();
                                categoria = i.getCategoria();
                                estado = i.getEstado();

                            }
                            textNomeItem.setText(nome);
                            textCategoriaItem.setText(categoria);
                            textEstadoItem.setText(estado);
                        }
                    }
                });
    }
}
