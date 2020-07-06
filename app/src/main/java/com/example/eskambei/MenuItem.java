package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eskambei.model.Item;
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

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        items = carregarDados(null);

        setContentView(R.layout.activity_menu_items);


    }

    @Override
    protected void onStart(){
        super.onStart();
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

    public List<Item> carregarDados(View view){
        final List<Item> listItem = new ArrayList<>();
        CollectionReference items = db.collection("items");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        items.whereEqualTo("idUsuario",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot document : task.getResult()){
                                listItem.add(document.toObject(Item.class));
                            }
                        }
                    }
                });
        return listItem;
    }
}
