package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        db = FirebaseFirestore.getInstance();
        carregarDados(null);
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
                        String message = "Perfil Editado com Sucesso!";
                        Toast.makeText(EditarPerfil.this,message,Toast.LENGTH_SHORT).show();
                        menuPrincipal(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Falha ao Editar Perfil!";
                        Toast.makeText(EditarPerfil.this,message,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void carregarDados(View view){
        CollectionReference usuarios = db.collection("usuarios");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        usuarios.whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Usuario> listUsuario = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listUsuario.add(document.toObject(Usuario.class));
                            }
                            String nome ="";
                            String sobrenome ="";
                            String endereço ="";
                            String telefone ="";
                            for(Usuario u : listUsuario){
                                nome = u.getNome();
                                sobrenome = u.getSobrenome();
                                endereço = u.getEndereço();
                                telefone = u.getTelefone();

                            }
                            editNome.setText(nome);
                            editSobrenome.setText(sobrenome);
                            editEndereço.setText(endereço);
                            editTelefone.setText(telefone);
                        }
                    }
                });
    }
}
