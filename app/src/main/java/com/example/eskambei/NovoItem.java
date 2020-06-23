package com.example.eskambei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eskambei.model.Item;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import javax.annotation.Nullable;

public class NovoItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editNome;
    private Spinner spCategoria;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ImageView imageItem;
    private File imageFile;
    private StorageReference storageReference;

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
        imageItem = findViewById(R.id.imageItem);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,requestCode,data);
        if(resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            imageItem.setImageURI(uri);

            imageFile = ImagePicker.Companion.getFile(data);
        }else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this,ImagePicker.Companion.getError(data),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Tarefa Cancelada!",Toast.LENGTH_SHORT).show();
        }
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

    public void selecionarImagem(View view) {
        ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start();
    }

    public void adicionarItem(View view) {
        if(imageFile == null) {
            Toast.makeText(NovoItem.this, "Falha no Upload da Imagem!", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userPath = "images/" + user.getUid() + "/";
            final StorageReference uploadImage = storageReference.child(userPath+"image_"+Util.getTimeStamp()+".png");
            final UploadTask task = uploadImage.putFile(Uri.fromFile(imageFile));

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NovoItem.this, "Falha no Upload da Imagem!", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(NovoItem.this, "Upload Efetuado com Sucesso!", Toast.LENGTH_SHORT).show();
                    String urlImagem = uploadImage.getDownloadUrl().toString();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    RadioGroup rgEstado = (RadioGroup) findViewById(R.id.rgEstado);
                    String id = user.getUid();
                    String nome = editNome.getText().toString();
                    String categoria = spCategoria.getSelectedItem().toString();
                    String estado = rgEstado.getCheckedRadioButtonId() == R.id.rbNovo ? "Novo" : "Usado";

                    Item item = new Item(id, nome, categoria, estado, urlImagem);

                    db.collection("items").add(item)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    String message = "Item Cadastrado com Sucesso!";
                                    Toast.makeText(NovoItem.this, message, Toast.LENGTH_SHORT).show();
                                    menuItems(null);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String message = "Falha ao Cadastrar Item!";
                                    Toast.makeText(NovoItem.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            });
        }
    }

    /*public void adicionarItem(View view) {
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


    }*/
}
