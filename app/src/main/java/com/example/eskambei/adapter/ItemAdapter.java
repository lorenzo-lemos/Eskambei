package com.example.eskambei.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eskambei.model.Item;
import com.example.eskambei.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>  {

    private List<Item> postagens;

    public ItemAdapter(List<Item> item) {
        this.postagens = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_menu_postagens, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item item = postagens.get(position);
        holder.nome.setText(item.getNome());
        holder.postagem.setText(item.getCategoria());
        holder.postagem.setText(item.getEstado());
        //holder.imagem.setImageResource(item.getUrlImagem());
    }

    @Override
    public int getItemCount() {
        return postagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nome;
        private TextView postagem;
        private ImageView imagem;
        private TextView categoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textTitle);
            postagem = itemView.findViewById(R.id.textEstado);
            categoria = itemView.findViewById(R.id.textCategoria);
            imagem = itemView.findViewById(R.id.imagePostagem);
        }
    }

}
