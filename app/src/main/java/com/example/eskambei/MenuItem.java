package com.example.eskambei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_items);
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
}
